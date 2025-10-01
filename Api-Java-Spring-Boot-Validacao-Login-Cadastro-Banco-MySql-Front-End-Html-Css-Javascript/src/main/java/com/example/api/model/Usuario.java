package com.example.api.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "usuarios", uniqueConstraints = {
    @UniqueConstraint(columnNames = "email"),
    @UniqueConstraint(columnNames = "cpf")

})

public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nome;

  @Column(nullable = false, length = 11, unique = true)
  private String cpf;

  @Column(nullable = false)
  private String rg;

  @Column(nullable = false)
  private LocalDate dataNascimento;

  @Column(nullable = false)
  private String endereco;

  @Column(nullable = false)
  private String telefone;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String senhaHash;

  @Column(nullable = false)
  private String genero;

  @Column(nullable = false)
  private java.time.LocalDateTime criadoEm = java.time.LocalDateTime.now();

}
