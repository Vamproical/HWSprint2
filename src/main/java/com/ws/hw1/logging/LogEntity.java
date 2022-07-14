package com.ws.hw1.logging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LogEntity {
    String ipAddress;
    String operation;
    String endPoint;
    String method;
    String params;
    LocalDateTime requestTime;

    @Override
    public String toString() {
        return "ipAddress= " + ipAddress +
               ", operation= " + operation +
               ", endPoint= " + endPoint +
               ", method= " + method +
               ", params= " + params +
               ", requestTime= " + requestTime;
    }
}
