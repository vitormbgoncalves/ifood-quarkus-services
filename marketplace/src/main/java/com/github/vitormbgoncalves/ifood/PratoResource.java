package com.github.vitormbgoncalves.ifood;

import com.github.vitormbgoncalves.ifood.dto.PratoDTO;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/pratos")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Tag(name = "pratos")
public class PratoResource {

  @Inject
  PgPool pgPool;

  @GET
  public Multi<PratoDTO> buscarPratos() {
    return Prato.findAll(pgPool);
  }

  @GET
  @Path("{id}")
  public Uni<PratoDTO> buscarPratoPorId(@PathParam("id") Long id) {
    return Prato.findById(pgPool, id);
  }
}
