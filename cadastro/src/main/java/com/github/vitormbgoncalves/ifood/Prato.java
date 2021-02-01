package com.github.vitormbgoncalves.ifood;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.eclipse.microprofile.opentracing.Traced;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "prato")
@Traced
public class Prato extends PanacheEntityBase {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;

  public String nome;

  public String descricao;

  @ManyToOne
  public  Restaurante restaurante;

  public BigDecimal preco;
}
