package com.github.vitormbgoncalves.ifood.cli;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.stream.Stream;

@QuarkusMain
public class MainDoQuarkus implements QuarkusApplication {

  @Inject
  @Channel("parametros")
  Emitter<String> emiter;

  @Override
  public int run(String... args) throws Exception {
    Stream.of(args).forEach(s -> emiter.send(s));
    System.out.println("MainDoQuarkus.tun() " + Arrays.toString(args));
    Quarkus.waitForExit();
    return 0;
  }
}

/*
@QuarkusMain
public class MainDoQuarkus {

  public static void main(String[] args) {
    Quarkus.run(MainDoQuarkus2.class, args);
  }
}

class MainDoQuarkus2 implements QuarkusApplication {

  @Override
  public int run(String... args) throws Exception {
    System.out.println("MainDoQuarkus.run(2) " + Arrays.toString(args));
    Quarkus.waitForExit();
    return 0;
  }
}*/
