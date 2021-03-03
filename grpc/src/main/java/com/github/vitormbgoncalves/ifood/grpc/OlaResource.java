package com.github.vitormbgoncalves.ifood.grpc;

import com.github.vitormbgoncalves.proto.MutinyOlaServiceGrpc;
import com.github.vitormbgoncalves.proto.OlaRequest;
import com.github.vitormbgoncalves.proto.OlaResponse;
import com.github.vitormbgoncalves.proto.OlaServiceGrpc;
import io.quarkus.grpc.runtime.annotations.GrpcService;
import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/ola")
public class OlaResource {

  @Inject
  @GrpcService("ola-service")
  OlaServiceGrpc.OlaServiceBlockingStub olaService;

  @Inject
  @GrpcService("ola-service")
  MutinyOlaServiceGrpc.MutinyOlaServiceStub olaServiceMutiny;

  @GET
  @Path("/block/{nome}")
  public String getBlock(@PathParam("nome") String nome) {
    OlaRequest request = OlaRequest.newBuilder().setNome(nome).build();
    OlaResponse response = olaService.digaOla(request);
    return response.getMensagem()+", Quantidade: "+response.getQuantidadeDeChamadas();
  }

  @GET
  @Path("/mutiny/{nome}")
  public Uni<String> getMutiny(@PathParam("nome") String nome) {
    OlaRequest request = OlaRequest.newBuilder().setNome(nome).build();
    Uni<OlaResponse> response = olaServiceMutiny.digaOla(request);
    return response.onItem().transform(i -> i.getMensagem()+", Quantidade: "+i.getQuantidadeDeChamadas());
  }
}
