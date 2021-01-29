package com.github.vitormbgoncalves.ifood.dto;

import com.github.vitormbgoncalves.ifood.Restaurante;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "CDI", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestauranteMapper {

  public Restaurante toRestaurante(AdcionarRestauranteDTO dto);

  public void toRestaurante(AtualizarRestuaranteDTO dto, @MappingTarget Restaurante restaurante);

  @Mapping(target = "dataCriacao", dateFormat = "dd/MM/yyyy HH:mm:ss")
  public RestauranteDTO toRestauranteDTO(Restaurante r);
}
