package com.github.vitormbgoncalves.ifood;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.github.vitormbgoncalves.ifood.dto.PratoDTO;
import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("restaurantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "pratos")
public class RestauranteResource {

  @Inject
  io.vertx.mutiny.pgclient.PgPool client;

  @GET
  @Path("{idRestaurante}/pratos")
  public Multi<PratoDTO> buscarPratos(@PathParam("idRestaurante") Long idRestaurante) {
    return Prato.findAll(client, idRestaurante);
  }
}