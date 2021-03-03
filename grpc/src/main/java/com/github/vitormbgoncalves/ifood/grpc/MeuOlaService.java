package com.github.vitormbgoncalves.ifood.grpc;

import com.github.vitormbgoncalves.proto.MutinyOlaServiceGrpc;
import com.github.vitormbgoncalves.proto.OlaRequest;
import com.github.vitormbgoncalves.proto.OlaResponse;
import com.github.vitormbgoncalves.proto.OlaServiceGrpc;
import io.grpc.stub.StreamObserver;
import io.smallrye.mutiny.Uni;

import javax.inject.Singleton;
import java.util.concurrent.atomic.AtomicInteger;

// Se quiser trabalhar sem o Mutiny
/*@Singleton
public class MeuOlaService extends OlaServiceGrpc.OlaServiceImplBase {

  AtomicInteger inteiro = new AtomicInteger();

  @Override
  public void digaOla(OlaRequest request, StreamObserver<OlaResponse> responseObserver) {
    String nome = request.getNome();

    responseObserver.onNext(
      OlaResponse.newBuilder()
        .setMensagem("Ola "+nome)
        .setQuantidadeDeChamadas(inteiro.getAndIncrement()).build()
    );
    responseObserver.onCompleted();
  }
}*/

//Trabalhando com Mutiny
@Singleton
public class MeuOlaService extends MutinyOlaServiceGrpc.OlaServiceImplBase {

  AtomicInteger inteiro = new AtomicInteger();

  @Override
  public Uni<OlaResponse> digaOla(OlaRequest request) {
    OlaResponse response = OlaResponse.newBuilder()
      .setMensagem("Ola Mutiny " +request.getNome())
      .setQuantidadeDeChamadas(inteiro.getAndIncrement()).build();
    return Uni.createFrom().item(response);
  }
}