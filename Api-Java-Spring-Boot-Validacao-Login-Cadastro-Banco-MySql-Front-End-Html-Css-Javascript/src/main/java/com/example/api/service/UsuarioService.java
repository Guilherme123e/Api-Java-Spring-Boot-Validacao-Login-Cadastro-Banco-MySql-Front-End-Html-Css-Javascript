package com.example.api.service;

import com.example.api.model.Usuario;
import com.example.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public Optional<String> cadastrarUsuario(Usuario usuario, String confEmail, String confSenha) {
    if (!usuario.getEmail().equalsIgnoreCase(confEmail)) {
      return Optional.of("Emails não conferem.");
    }
    if (!usuario.getSenhaHash().equals(confSenha)) {
      return Optional.of("Senhas não conferem.");
    }
    if (!isCPFValido(usuario.getCpf())) {
      return Optional.of("CPF inválido.");
    }
    if (!isNomeValido(usuario.getNome())) {
      return Optional.of("Nome inválido. Use apenas letras e espaços.");
    }
    if (!isRGValido(usuario.getRg())) {
      return Optional.of("RG inválido.");
    }
    if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
      return Optional.of("Email já cadastrado.");
    }
    if (usuarioRepository.findByCpf(usuario.getCpf()).isPresent()) {
      return Optional.of("CPF já cadastrado.");
    }

    usuario.setSenhaHash(passwordEncoder.encode(usuario.getSenhaHash()));
    usuarioRepository.save(usuario);
    return Optional.empty();
  }

  public Optional<Usuario> login(String email, String senha) {
    Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
    if (usuarioOpt.isPresent()) {
      Usuario usuario = usuarioOpt.get();
      if (passwordEncoder.matches(senha, usuario.getSenhaHash())) {
        return Optional.of(usuario);
      }
    }
    return Optional.empty();
  }

  private boolean isCPFValido(String cpf) {
    return cpf != null && cpf.length() == 11 && cpf.matches("\\d+");
  }

  private boolean isNomeValido(String nome) {
    return nome != null && nome.matches("[A-Za-zÀ-ú ]+");
  }

  private boolean isRGValido(String rg) {
    return rg != null && rg.length() >= 5 && rg.matches("[\\d\\w]+");
  }
}
