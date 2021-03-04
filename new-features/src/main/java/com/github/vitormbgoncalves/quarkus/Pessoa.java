package com.github.vitormbgoncalves.quarkus;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class Pessoa extends PanacheEntity {

  public String nome;

  public Integer idade;
}
