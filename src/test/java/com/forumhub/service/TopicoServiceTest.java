package com.forumhub.service;

import com.forumhub.model.Topico;
import com.forumhub.repository.TopicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TopicoServiceTest {

    @Autowired
    private TopicoService topicoService;

    @Autowired
    private TopicoRepository topicoRepository;

    @BeforeEach
    void setUp() {
        // Limpa o banco de dados antes de cada teste
        topicoRepository.deleteAll();
    }

    @Test
    @Transactional
    void testCriarTopicoComTransacao() {
        // Criação de um tópico
        Topico topico = new Topico();
        topico.setTitulo("Teste de Tópico");
        topico.setMensagem("Mensagem do tópico");
        topico.setEstadoTopico("Ativo");
        topico.setAutor("Usuário");
        topico.setCurso("Java");

        topicoService.criarTopico(topico);

        // Verifica se o tópico foi salvo no banco de dados
        assertNotNull(topico.getId());
        assertEquals(1, topicoRepository.count());

        // Verifica se as informações do tópico estão corretas
        Topico encontrado = topicoRepository.findById(topico.getId()).orElseThrow();
        assertEquals("Teste de Tópico", encontrado.getTitulo());
        assertEquals("Mensagem do tópico", encontrado.getMensagem());
        assertEquals("Ativo", encontrado.getEstadoTopico());
        assertEquals("Usuário", encontrado.getAutor());
        assertEquals("Java", encontrado.getCurso());
    }
}