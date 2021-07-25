package org.vizslarescue.service.ping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @GetMapping("/api/ping")
    public Ping ping() {
        return new Ping("OKAY");
    }
}
