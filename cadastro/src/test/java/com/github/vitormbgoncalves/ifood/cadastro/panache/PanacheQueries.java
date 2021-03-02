package com.github.vitormbgoncalves.ifood.cadastro.panache;

import com.github.vitormbgoncalves.ifood.Restaurante;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;

public class PanacheQueries {

  public void exemplosSelects() {
    // -- Métodos de Classe --

    //findAll com sort

    //Consulta Avançada
    PanacheQuery<PanacheEntityBase> findAll = Restaurante.findAll();

    //classificacao(Sort)
    Restaurante.findAll(Sort.by("nome", Sort.Direction.Ascending));

    //paginacao
    PanacheQuery<PanacheEntityBase> page = findAll.page(Page.ofSize(10));

    PanacheQuery<Restaurante> page2 = findAll.page(Page.of(3, 10));

    //findAll sem sort

    Map<String, Object> params = new HashMap<>();

    //consultas simplificadas com HQL
    Restaurante.find("select from Restaurante where none = :nome", params);

    String nome = "";

    Restaurante.find("select r from Restaurante where nome = $1", nome);

    Restaurante.find("select r from Restaurante where nome = :nome and id = :id",
      Parameters.with("nome", "").and("id", 1L));

    //Atalho para findAll.stream, mas precisa de transacao se nao o cursor pode fechar antes

    Restaurante.stream("select r from Restaurante where nome = :nome", params);

    Restaurante.stream("select r from Restaurante where nome = $1", nome);

    Restaurante.stream("select r from Restaurante where nome = :nome and id = :id",
      Parameters.with("nome", "").and("id", 1L));

    //find by id

    PanacheEntityBase findById = Restaurante.findById(1L);

    //Persistir

    Restaurante.persist(findById, findById);

    //Delete

    Restaurante.delete("delete Restaurante where nome = :nome", params);

    Restaurante.delete("delete Restaurante where nome = $1", nome);

    Restaurante.delete("nome = :nome and id = :id",
      Parameters.with("nome", "").and("id", 1L));

    //Update

    Restaurante.update("update Restaurante where nome = :nome", params);

    Restaurante.update("update Restaurante where nome = $1", nome);

    //Count

    Restaurante.count();

    //-- Métodos de Objeto--

    Restaurante restaurante = new Restaurante();

    restaurante.persist();

    restaurante.isPersistent();

    restaurante.delete();
  }
}

@Entity
class MinhaEntidade1 extends PanacheEntity {

  @Id
  @GeneratedValue
  public Long id;

  public String nome;
}

@Entity
class MinhaEntidade2 extends PanacheEntityBase {

  @Id
  @GeneratedValue
  public Long id;

  public String nome;

  public MinhaEntidade2() {

  }
}

@Entity
class MinhaEntidade3 {

  @Id
  @GeneratedValue
  public Long id;

  public String nome;

  public MinhaEntidade3() {

  }
}

@ApplicationScoped
class MeuRepositorio implements PanacheRepository<MinhaEntidade3> {

}