package com.github.vitormbgoncalves.quarkus;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class PessoaService {

  public void salvar() {
    Pessoa p = new Pessoa();
    p.nome = "Teste 1";
    p.idade = 10;
    p.persist();
  }
}
