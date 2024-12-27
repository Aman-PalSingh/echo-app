package com.aps.echo.service;


import com.datastax.astra.client.Database;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AstraService {

    private final Database database;

    public AstraService(Database database) {
        this.database = database;
    }

    public List<String> listCollections() {
        return database.listCollectionNames().collect(Collectors.toList());
    }
}

