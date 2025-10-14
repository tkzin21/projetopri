package com.agendai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaginasController {

    @GetMapping("/sobre")
    public String sobre() {
        return "sobre";
    }

    @GetMapping("/configuracoes")
    public String configuracoes() {
        return "configuracoes";
    }
}
