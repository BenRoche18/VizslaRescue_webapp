package org.vizslarescue.service.ping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.vizslarescue.model.ping.Ping;

@RestController
@RequestMapping(path = "/api")
public class PingController {

    @GetMapping("/ping")
    public @ResponseBody Ping ping() {
        return Ping.create("OKAY");
    }
}
