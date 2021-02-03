package com.github.vitormbgoncalves.ifood;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.SqlClientHelper;
import io.vertx.mutiny.sqlclient.Tuple;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

@ApplicationScoped
public class RestauranteCadastrado {

  @Inject
  PgPool pgPool;

  @Incoming("restaurantes")
  public Uni<Void> receberRetaurante(String json) {
    Jsonb create = JsonbBuilder.create();
    Restaurante restaurante = create.fromJson(json, Restaurante.class);
    return SqlClientHelper.inTransactionUni(pgPool, tx -> {
      Uni<RowSet<Row>> insertOne = tx.preparedQuery("insert into localizacao (id, latitude, longitude) values ($1, $2, $3)")
          .execute(Tuple.of(restaurante.localizacao.id, restaurante.localizacao.latitude, restaurante.localizacao.longitude));
      Uni<RowSet<Row>> insertTwo = tx.preparedQuery("insert into restaurante (id, nome, localizacao_id) values ($1, $2, $3)")
          .execute(Tuple.of(restaurante.id, restaurante.nome, restaurante.localizacao.id));

      return insertOne.and(insertTwo)
          // Ignore the results (the two ids)
          .onItem().ignore().andContinueWithNull();
    });
  }
}