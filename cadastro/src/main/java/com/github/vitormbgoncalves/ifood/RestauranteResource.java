package com.github.vitormbgoncalves.ifood;

import com.github.vitormbgoncalves.ifood.dto.*;
import com.github.vitormbgoncalves.ifood.infra.ConstraintViolationResponse;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Path("/restaurantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "restaurante")
@RolesAllowed("proprietario")
@SecurityScheme(securitySchemeName = "ifood-oauth", type = SecuritySchemeType.OAUTH2, flows = @OAuthFlows(password = @OAuthFlow(tokenUrl = "http://localhost:8180/auth/realms/ifood/protocol/openid-connect/token")))
@SecurityRequirement(name = "ifood-oauth", scopes = {})
public class RestauranteResource {

  @Inject
  RestauranteMapper restauranteMapper;

  @Inject
  PratoMapper pratoMapper;

  @GET
  @Counted(name = "Quantidade de busca Restaurante")
  @SimplyTimed(name = "Tempo simples de busca")
  @Timed(name = "Tempo completo de busca")
  public List<RestauranteDTO> buscarRestaurantes() {
    Stream<Restaurante> restaurantes = Restaurante.streamAll();
    return restaurantes.map(r -> restauranteMapper.toRestauranteDTO(r)).collect(Collectors.toList());
  }

  @POST
  @Transactional
  @APIResponse(responseCode = "201", description = "Caso restaurante seja cadastrado com sucesso")
  @APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = ConstraintViolationResponse.class)))
  public Response adicionarRestaurante(@Valid AdicionarRestauranteDTO dto) {
    Restaurante restaurante = restauranteMapper.toRestaurante(dto);
    restaurante.persist();
    return Response.status(Status.CREATED).build();
  }

  @PUT
  @Path("{idRestaurante}")
  @Transactional
  public void atualizarRestaurante(@PathParam("idRestaurante") Long idRestaurante, AtualizarRestuaranteDTO dto) {
    Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
    if(restauranteOp.isEmpty()) {
      throw new NotFoundException();
    }
    Restaurante restaurante = restauranteOp.get();
    restauranteMapper.toRestaurante(dto, restaurante);
    restaurante.persist();
  }

  @DELETE
  @Path("{idRestaurante}")
  @Transactional
  public void removerResturante(@PathParam("idRestaurante") Long idRestaurante) {
    Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);

    restauranteOp.ifPresentOrElse(Restaurante::delete, () -> {
      throw new NotFoundException();
    });
  }

  //Pratos

  @GET
  @Path("{idRestaurante}/pratos")
  @Tag(name = "prato")
  public List<PratoDTO> buscarPratos(@PathParam("idRestaurante") Long idRestaurante) {
    Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
    if (restauranteOp.isEmpty()) {
      throw new NotFoundException("Restaurante não existe");
    }
    Stream<Prato> pratos = Prato.stream("restaurante", restauranteOp.get());
    return pratos.map(p -> pratoMapper.toDTO(p)).collect(Collectors.toList());
  }

  @POST
  @Path("{idRestaurante}/pratos")
  @Tag(name = "prato")
  @Transactional
  @APIResponse(responseCode = "201", description = "Caso prato seja cadastrado com sucesso")
  public Response adicionarPrato(@PathParam("idRestaurante") Long idRestaurante, AdcionarPratoDTO dto) {
    Optional <Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
    if (restauranteOp.isEmpty()) {
      throw new NotFoundException("Restaurante não existe");
    }
    Prato prato = pratoMapper.toPrato(dto);
    prato.restaurante = restauranteOp.get();
    prato.persist();
    return Response.status(Status.CREATED).build();
  }

  @PUT
  @Path("{idRestaurante}/pratos/{idPrato}")
  @Tag(name = "prato")
  @Transactional
  public void  atualizarPrato(@PathParam("idRestaurante") Long idRestaurante, @PathParam("idPrato") Long idPrato, AtualizarPratoDTO dto) {
    Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
    if (restauranteOp.isEmpty()) {
      throw new NotFoundException("Restaurante não existe");
    }

    Optional<Prato> pratoOp = Prato.findByIdOptional(idPrato);

    if (pratoOp.isEmpty()) {
      throw new NotFoundException("Prato não existe");
    }
    Prato prato = pratoOp.get();
    pratoMapper.toPrato(dto, prato);
    prato.persist();
  }

  @DELETE
  @Path("{idRestaurante}/pratos/{idPrato}")
  @Tag(name = "prato")
  @Transactional
  public void RemoverPrato(@PathParam("idRestaurante") Long idRestaurante, @PathParam("idPrato") Long idPrato) {
    Optional<Restaurante> restauranteOp = Restaurante.findByIdOptional(idRestaurante);
    if (restauranteOp.isEmpty()) {
      throw new NotFoundException("Restaurante não existe");
    }

    Optional<Prato> pratoOp = Prato.findByIdOptional(idPrato);
    pratoOp.ifPresentOrElse(Prato::delete, () -> {
      throw new NotFoundException();
    });
  }

}