package com.forumhub.controller;

import com.forumhub.model.Topico;
import com.forumhub.service.TopicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Topico criarTopico(@RequestBody Topico topico) {
        return topicoService.criarTopico(topico);
    }

    @GetMapping
    public List<Topico> listarTopicos() {
        return topicoService.listarTopicos();
    }

    @GetMapping("/{id}")
    public Topico buscarTopico(@PathVariable Long id) {
        return topicoService.buscarTopicoPorId(id);
    }

    @PutMapping("/{id}")
    public Topico atualizarTopico(@PathVariable Long id, @RequestBody Topico topico) {
        return topicoService.atualizarTopico(id, topico);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarTopico(@PathVariable Long id) {
        topicoService.deletarTopico(id);
    }
}
