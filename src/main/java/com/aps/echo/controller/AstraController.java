package com.aps.echo.controller;


import com.aps.echo.service.AstraService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/collections")
public class AstraController {

    private final AstraService astraService;

    public AstraController(AstraService astraService) {
        this.astraService = astraService;
    }

    @RequestMapping (method = RequestMethod.GET)
    public List<String> getCollections() {
        return astraService.listCollections();
    }
}

