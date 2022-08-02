package org.vizslarescue.service.workbench;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/api/workbench")
@RestController
public class WorkbenchController {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostMapping("")
    public List<Map<String, Object>> executeSql(
        @RequestBody String query
    )
    {
        return jdbcTemplate.queryForList(query);
    }
}
