package com.github.vitormbgoncalves.ifood.dto;

import com.github.vitormbgoncalves.ifood.Restaurante;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "CDI")
public interface RestauranteMapper {


  public Restaurante toRestaurante(AdcionarRestauranteDTO dto);

  public void toRestaurante(AtualizarRestuaranteDTO dto, @MappingTarget Restaurante restaurante);

  public RestauranteDTO toRestauranteDTO(Restaurante r);
}
