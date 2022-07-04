package com.ws.hw1.parser;

import java.io.File;
import java.util.List;

public interface Parser {
    List<EmployeeFromFile> parse(File file);
}
