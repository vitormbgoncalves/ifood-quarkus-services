package com.github.vitormbgoncalves.ifood;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class PedidoDeserializer extends ObjectMapperDeserializer<PedidoRealizadoDTO> {

  public PedidoDeserializer() {
    super(PedidoRealizadoDTO.class);
  }
}