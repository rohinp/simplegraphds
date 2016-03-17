package com.rohin.main;

import static spark.Spark.*;

public class ServiceLoader {
    public static void main(String[] args) {
        get("/hello", (req, res) -> "<h1>Self Deployed Application<h1>");
    }
}
