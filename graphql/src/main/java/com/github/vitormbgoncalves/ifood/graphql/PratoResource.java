package com.github.vitormbgoncalves.ifood.graphql;

import org.eclipse.microprofile.graphql.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@GraphQLApi
public class PratoResource {

  @Query("buscarPratos")
  @Description("Esta query retorna todos os pratos")
  public List<Prato> buscarTodosPratos() {
    Prato prato = new Prato();
    prato.setNome("Estrogonof");
    prato.setDescricao("Muito Cremosso");
    prato.setValor(BigDecimal.TEN);
    List<Prato> lista = Collections.singletonList(prato);
    return lista;
  }

  @Query("buscarPorId")
  @Description("Esta query retorna todos os pratos")
  public Prato buscarPrato(@Name("id") Integer id) {
    System.out.println("ID: " + id);
    Prato prato = new Prato();
    prato.setNome("Lentilha");
    prato.setDescricao("Com bastante caldo");
    prato.setValor(BigDecimal.TEN);
    return prato;
  }

  @Name("restaurante")
  public Restaurante buscarResturante(@Source Prato prato) {
    Restaurante restaurante = new Restaurante();
    restaurante.setDono("Pedro");
    restaurante.setNome("Prato Nobre");
    return restaurante;
  }

  @Mutation
  @Description("Alterar o restaurante")
  public Restaurante alterarRestaurante(Restaurante restaurante) {
    System.out.println(restaurante.getNome());
    return restaurante;
  }
}
