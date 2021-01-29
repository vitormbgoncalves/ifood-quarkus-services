package com.github.vitormbgoncalves.ifood.dto;

import com.github.vitormbgoncalves.ifood.Prato;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "CDI", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PratoMapper {

  public Prato toPrato(AdcionarPratoDTO dto);

  public void toPrato(AtualizarPratoDTO dto, @MappingTarget Prato prato);

  PratoDTO toDTO(Prato p);
}
