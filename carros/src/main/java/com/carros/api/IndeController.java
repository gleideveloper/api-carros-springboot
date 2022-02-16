package com.carros.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class IndeController {
    @GetMapping
    public String get(){
        return "API dos carros";
    }
}

