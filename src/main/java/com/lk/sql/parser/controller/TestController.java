package com.lk.sql.parser.controller;

import org.springframework.web.bind.annotation.*;

/**
 * @author lukai
 * @creare 2021-12-10 15:54
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("sql")
    public String queryTrack(@RequestParam String sql) {
        System.out.println(sql);
        return sql;
    }
}
