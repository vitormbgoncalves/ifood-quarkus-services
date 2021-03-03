package com.github.vitormbgoncalves.ifood.cli;

import io.quarkus.picocli.runtime.annotations.TopCommand;
import picocli.CommandLine;

//@QuarkusMain
@TopCommand
@CommandLine.Command(mixinStandardHelpOptions = true, subcommands = { Ola.class, Tchau.class })
public class MainDoQuarkus  {

/*  @Inject
  @Channel("parametros")
  Emitter<String> emiter;

  @Override
  public int run(String... args) throws Exception {
    Stream.of(args).forEach(s -> emiter.send(s));
    System.out.println("MainDoQuarkus.tun() " + Arrays.toString(args));
    Quarkus.waitForExit();
    return 0;
  }*/
}

@CommandLine.Command(name = "Ola", description = "Imprime nome do usuario")
class Ola implements Runnable {

  @CommandLine.Option(names = { "-n", "--name" }, description = "Nome completo do usuario", defaultValue = "Vitor Goncalves")
  String nome;

  @Override
  public void run() {
    System.out.println("Ola.run()" + nome);
  }
}

@CommandLine.Command(name = "tchau", description = "Imprime tchau e o nome do usuario")
class Tchau implements Runnable {

  @CommandLine.Option(names = { "-n", "--name" }, description = "Nome completo do usuario", required = true)
  String nome;

  @Override
  public void run() {
    System.out.println("Tchau.run()" + nome);
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
