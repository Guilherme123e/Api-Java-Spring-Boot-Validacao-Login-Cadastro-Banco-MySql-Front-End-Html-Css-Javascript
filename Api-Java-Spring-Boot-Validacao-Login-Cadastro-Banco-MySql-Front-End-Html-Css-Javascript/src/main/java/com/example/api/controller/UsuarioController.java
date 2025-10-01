package com.example.api.controller;

import com.example.api.model.Usuario;
import com.example.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UsuarioController {

  @Autowired
  private UsuarioService usuarioService;

  @PostMapping("/cadastro")
  public ResponseEntity<?> cadastrar(@RequestBody Map<String, String> body) {
    Usuario usuario = new Usuario();
    usuario.setNome(body.get("nome"));
    usuario.setCpf(body.get("cpf"));
    usuario.setRg(body.get("rg"));
    usuario.setDataNascimento(java.time.LocalDate.parse(body.get("data_nascimento")));
    usuario.setEndereco(body.get("endereco"));
    usuario.setTelefone(body.get("telefone"));
    usuario.setEmail(body.get("email"));
    usuario.setSenhaHash(body.get("senha"));
    usuario.setGenero(body.get("genero"));

    String confEmail = body.get("email_confirmation");
    String confSenha = body.get("senha_confirmation");

    var erro = usuarioService.cadastrarUsuario(usuario, confEmail, confSenha);
    if (erro.isPresent()) {
      return ResponseEntity.badRequest().body(Map.of("erro", erro.get()));
    }
    return ResponseEntity.ok(Map.of("msg", "Cadastro realizado com sucesso!"));
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
    String email = body.get("email");
    String senha = body.get("senha");

    var usuario = usuarioService.login(email, senha);
    if (usuario.isPresent()) {
      return ResponseEntity.ok(Map.of("msg", "Login efetuado com sucesso!"));
    } else {
      return ResponseEntity.status(401).body(Map.of("erro", "Email ou senha incorretos."));
    }
  }

  @Controller
  public class RedirectController {

    @GetMapping("/login")
    public String login() {
      return "redirect:/login.html";
    }

    @GetMapping("/register")
    public String register() {
      return "redirect:/register.html";
    }
  }
}
