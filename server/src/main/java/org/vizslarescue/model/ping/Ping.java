package org.vizslarescue.model.ping;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Ping {
    private final String status;

    public static Ping create(String status)
    {
        return new Ping(status);
    }
}
