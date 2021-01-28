package com.github.vitormbgoncalves.ifood.dto;

import java.math.BigDecimal;

public class PratoDTO {

  public Long id;

  public String nome;

  public String descricao;

  public RestauranteDTO restaurante;

  public BigDecimal preco;
}
