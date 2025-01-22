package com.forumhub.service;

import com.forumhub.model.Topico;
import com.forumhub.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    // Criar tópico
    @Transactional
    public Topico criarTopico(Topico topico) {
        // Não é necessário atribuir a data de criação, pois ela é configurada no construtor da classe Topico
        return topicoRepository.save(topico);
    }

    // Listar todos os tópicos
    public List<Topico> listarTopicos() {
        return topicoRepository.findAll();
    }

    // Buscar um tópico por id
    public Topico buscarTopicoPorId(Long id) {
        return topicoRepository.findById(id).orElseThrow(() -> new RuntimeException("Tópico não encontrado"));
    }

    // Atualizar um tópico
    @Transactional
    public Topico atualizarTopico(Long id, Topico topicoAtualizado) {
        Topico topicoExistente = buscarTopicoPorId(id);
        topicoExistente.setTitulo(topicoAtualizado.getTitulo());
        topicoExistente.setMensagem(topicoAtualizado.getMensagem());
        topicoExistente.setEstadoTopico(topicoAtualizado.getEstadoTopico());
        topicoExistente.setAutor(topicoAtualizado.getAutor());
        topicoExistente.setCurso(topicoAtualizado.getCurso());
        return topicoRepository.save(topicoExistente);
    }

    // Deletar um tópico
    @Transactional
    public void deletarTopico(Long id) {
        Topico topico = buscarTopicoPorId(id);
        topicoRepository.delete(topico);
    }
}
