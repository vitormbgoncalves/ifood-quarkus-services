package com.github.vitormbgoncalves.ifood.infra;

import javax.validation.ConstraintValidatorContext;

public interface DTO {

  default boolean isValid(ConstraintValidatorContext constraintValidatorContext) {
    return true;
  }
}
