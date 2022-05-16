package com.ws.hw1;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    private static final Map<UUID, String> posts = new HashMap<>();

    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("File must be the argument of the program");
        }
        String path = args[0];

        fill();

        List<String> parsed = read(new File(path));
        List<Employee> employees = new ArrayList<>();
        for (String employee : parsed) {
            Employee empl = parse(employee);
            Collections.sort(empl.getCharacteristics());
            employees.add(empl);
        }

        employees.sort(Comparator.comparing(Employee::getLastName).thenComparing(Employee::getFirstName));

        print(employees);

    }

    public static void fill() {
        posts.put(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "Engineer");
        posts.put(UUID.fromString("762d15a5-3bc9-43ef-ae96-02a680a557d0"), "Tech Writer");
    }

    public static List<String> read(File file) {
        List<String> parsed = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            scanner.useDelimiter("((\\n\\r)|(\\r\\n)){2}|(\\r){2}|(\\n){2}");
            while (scanner.hasNext()) {
                parsed.add(scanner.next());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return parsed;
    }

    public static Employee parse(String employee) {
        String[] part = employee.split("\n");
        String firstName = "";
        String lastName = "";
        String description = "";
        String post = "";
        List<String> characteristics = new ArrayList<>();
        for (String tmp : part) {
            if (tmp.startsWith("firstName:")) {
                firstName = tmp.replaceFirst("firstName:", "").trim();
            }
            if (tmp.startsWith("lastName:")) {
                lastName = tmp.replaceFirst("lastName:", "").trim();
            }
            if (tmp.startsWith("description:")) {
                description = tmp.replaceFirst("description:", "").trim();
            }
            if (tmp.startsWith("characteristics:")) {
                characteristics.addAll(List.of(tmp.replaceFirst("characteristics:", "").trim().split(",")));
            }
            if (tmp.startsWith("postId:")) {
                post = posts.get(UUID.fromString(tmp.replaceFirst("postId:", "").trim()));
            }
        }
        if (firstName.isBlank() || lastName.isBlank() || characteristics.isEmpty() || post.isBlank()) {
            throw new NullPointerException("All the fields except description must be not blank");
        }
        Collections.sort(characteristics);
        return new Employee(firstName, lastName, description, characteristics, post);
    }

    private static void print(List<Employee> employees) {
        for (Employee employee : employees) {
            System.out.println(employee);
            System.out.println();
        }
    }
}
