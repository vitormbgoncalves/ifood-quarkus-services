package com.github.vitormbgoncalves.ifood;

import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;
import java.util.Optional;

@Path("/restaurantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "restaurante")
public class RestauranteResource {

  @GET
  public List<Restaurante> buscar(){
    return Restaurante.listAll();
  }

  @POST
  @Transactional
  @APIResponse(responseCode = "201", description = "Caso restaurante seja cadastrado com sucesso")
  public Response adicionar(Restaurante dto) {
    dto.persist();
    return Response.status(Status.CREATED).build();
  }

  @PUT
  @Path("{id}")
  @Transactional
  public void atualizar(@PathParam("id") Long id, Restaurante dto) {
    Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(id);
    if(restauranteOp.isEmpty()) {
      throw new NotFoundException();
    }
    Restaurante restaurante = restauranteOp.get();
    restaurante.nome = dto.nome;
    restaurante.persist();
  }

  @DELETE
  @Path("{id}")
  @Transactional
  public void delete(@PathParam("id") Long id) {
    Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(id);

    restauranteOp.ifPresentOrElse(Restaurante::delete, () -> {
      throw new NotFoundException();
    });
  }

  //Pratos

  @GET
  @Path("{idRestaurante}/pratos")
  @Tag(name = "prato")
  public List<Restaurante> buscarPratos(@PathParam("idRestaurante") Long idRestaurante) {
    Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
    if (restauranteOp.isEmpty()) {
      throw new NotFoundException("Restaurante não existe");
    }
    return Prato.list("resturante", restauranteOp.get());
  }

  @POST
  @Path("{idRestaurante}/pratos")
  @Tag(name = "prato")
  @Transactional
  public Response adicionarPrato(@PathParam("idRestaurante") Long idRestaurante, Prato dto) {
    Optional <Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
    if (restauranteOp.isEmpty()) {
      throw new NotFoundException("Restaurante não existe");
    }
    Prato prato = new Prato();
    prato.nome = dto.nome;
    prato.descricao = dto.descricao;

    prato.preco = dto.preco;
    prato.restaurante = restauranteOp.get();
    prato.persist();
    return Response.status(Status.CREATED).build();
  }

  @PUT
  @Path("{idRestaurante}/pratos{id}")
  @Tag(name = "prato")
  @Transactional
  public void  atualizarPrato(@PathParam("idRestaurante") Long idRestaurante, @PathParam("id") Long id, Prato dto) {
    Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
    if (restauranteOp.isEmpty()) {
      throw new NotFoundException("Restaurante não existe");
    }

    Optional<Prato> pratoOp = Prato.findByIdOptional(id);

    if (pratoOp.isEmpty()) {
      throw new NotFoundException("Prato não existe");
    }
    Prato prato = pratoOp.get();
    prato.preco = dto.preco;
    prato.persist();
  }

  @DELETE
  @Path("{idRestaurante}/pratos/{id}")
  @Tag(name = "prato")
  @Transactional
  public void delete (@PathParam("idRestaurante") Long idRestaurante, @PathParam("id") Long id) {
    Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
    if (restauranteOp.isEmpty()) {
      throw new NotFoundException("Restaurante não existe");
    }

    Optional<Prato> pratoOp = Prato.findByIdOptional(id);
    pratoOp.ifPresentOrElse(Prato::delete, () -> {
      throw new NotFoundException();
    });
  }

}