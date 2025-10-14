package com.agendai.controller;

import com.agendai.model.Profissional;
import com.agendai.repository.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profissionais")
public class ProfissionalController {

    @Autowired
    private ProfissionalRepository repository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("profissionais", repository.findAll());
        return "profissionais";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Profissional profissional) {
        repository.save(profissional);
        return "redirect:/profissionais";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Profissional profissional = repository.findById(id).orElseThrow();
        model.addAttribute("profissional", profissional);
        model.addAttribute("profissionais", repository.findAll());
        return "profissionais";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/profissionais";
    }
}
