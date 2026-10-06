package com.aplicaciones.mundialmongo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    // Redirige la raíz al explorador interactivo de endpoints de Swagger
    @GetMapping("/")
    public String index() {
        return "redirect:/swagger-ui.html";
    }
}
