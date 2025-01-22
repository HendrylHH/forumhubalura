package com.forumhub.controller;

import com.forumhub.model.Topico;
import com.forumhub.repository.TopicoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TopicoControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Limpa o banco de dados antes de cada teste
        topicoRepository.deleteAll();
    }

    @Test
    void testCriarTopico() throws Exception {
        // Criação do objeto Topico
        Topico topico = new Topico();
        topico.setTitulo("Tópico de Teste");
        topico.setMensagem("Mensagem de teste");
        topico.setEstadoTopico("Ativo");
        topico.setAutor("Autor de Teste");
        topico.setCurso("Curso de Teste");

        // Envia a requisição POST para criar o tópico
        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/topicos", topico, String.class);

        // Verifica se a resposta foi bem-sucedida
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Verifica se o tópico foi salvo no banco de dados
        assertEquals(1, topicoRepository.count());

        // Recupera o tópico do banco de dados
        Topico topicoSalvo = topicoRepository.findAll().get(0);
        assertNotNull(topicoSalvo.getId());
        assertEquals("Tópico de Teste", topicoSalvo.getTitulo());
        assertEquals("Mensagem de teste", topicoSalvo.getMensagem());
    }

    @Test
    void testBuscarTopicoPorId() {
        // Cria e salva um tópico no banco de dados
        Topico topico = new Topico();
        topico.setTitulo("Tópico de Teste");
        topico.setMensagem("Mensagem de teste");
        topico.setEstadoTopico("Ativo");
        topico.setAutor("Autor de Teste");
        topico.setCurso("Curso de Teste");
        topico = topicoRepository.save(topico);

        // Faz uma requisição GET para buscar o tópico pelo id
        ResponseEntity<Topico> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/topicos/" + topico.getId(), Topico.class);

        // Verifica se a resposta foi bem-sucedida
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verifica se os dados estão corretos
        Topico topicoEncontrado = response.getBody();
        assertNotNull(topicoEncontrado);
        assertEquals("Tópico de Teste", topicoEncontrado.getTitulo());
        assertEquals("Mensagem de teste", topicoEncontrado.getMensagem());
    }

    @Test
    void testAtualizarTopico() throws Exception {
        // Cria e salva um tópico no banco de dados
        Topico topico = new Topico();
        topico.setTitulo("Tópico de Teste");
        topico.setMensagem("Mensagem de teste");
        topico.setEstadoTopico("Ativo");
        topico.setAutor("Autor de Teste");
        topico.setCurso("Curso de Teste");
        topico = topicoRepository.save(topico);

        // Atualiza o título do tópico
        topico.setTitulo("Tópico Atualizado");

        // Envia a requisição PUT para atualizar o tópico
        restTemplate.put("http://localhost:" + port + "/topicos/" + topico.getId(), topico);

        // Recupera o tópico do banco de dados
        Topico topicoAtualizado = topicoRepository.findById(topico.getId()).orElseThrow();

        // Verifica se o tópico foi atualizado corretamente
        assertEquals("Tópico Atualizado", topicoAtualizado.getTitulo());
    }

    @Test
    void testDeletarTopico() {
        // Cria e salva um tópico no banco de dados
        Topico topico = new Topico();
        topico.setTitulo("Tópico de Teste");
        topico.setMensagem("Mensagem de teste");
        topico.setEstadoTopico("Ativo");
        topico.setAutor("Autor de Teste");
        topico.setCurso("Curso de Teste");
        topico = topicoRepository.save(topico);

        // Verifica se o tópico foi salvo
        assertEquals(1, topicoRepository.count());

        // Envia a requisição DELETE para remover o tópico
        restTemplate.delete("http://localhost:" + port + "/topicos/" + topico.getId());

        // Verifica se o tópico foi deletado
        assertEquals(0, topicoRepository.count());
    }
}
