package com.github.vitormbgoncalves.ifood;

import javax.persistence.*;

@Entity
@Table(name = "localizacao")
public class Localizacao {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;

  public double latitude;
  public double longitude;
}
