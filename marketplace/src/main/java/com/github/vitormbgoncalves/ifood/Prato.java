package com.github.vitormbgoncalves.ifood;

import com.github.vitormbgoncalves.ifood.dto.PratoDTO;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

import java.math.BigDecimal;
import java.util.stream.StreamSupport;

public class Prato {

  public Long id;

  public String nome;

  public String descricao;

  public Restaurante restaurante;

  public BigDecimal preco;

  public static Multi<PratoDTO> findAll(PgPool pgPool) {
    return pgPool.query("select * from prato").execute()
        .onItem().transformToMulti(set -> Multi.createFrom().items(() ->
        StreamSupport.stream(set.spliterator(), false)))
        .onItem().transform(PratoDTO::from);
  }

  public static Multi<PratoDTO> findAll(PgPool client, Long idRestaurante) {
    return client.preparedQuery("SELECT * FROM prato where prato.restaurante_id = $1 ORDER BY nome ASC").execute(Tuple.of(idRestaurante))
        .onItem().transformToMulti(set -> Multi.createFrom().items(() ->
            StreamSupport.stream(set.spliterator(), false)))
        .onItem().transform(PratoDTO::from);
  }

  public static Uni<PratoDTO> findById(PgPool client, Long id) {
    return client.preparedQuery("SELECT * FROM prato WHERE id = $1").execute(Tuple.of(id))
        .map(RowSet::iterator)
        .map(iterator -> iterator.hasNext() ? PratoDTO.from(iterator.next()) : null);
  }
}
