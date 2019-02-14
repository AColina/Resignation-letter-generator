package com.github.acolina.generator.service;

import com.github.acolina.generator.core.exception.EntityNotFoundException;
import com.github.acolina.generator.model.dto.ResignationLetterDTO;
import com.github.acolina.generator.model.request.ResignationLetter;

public interface ResignationLetterGeneratorService {

    ResignationLetterDTO generateResignationLetterDTO(ResignationLetter resignationLetter) throws EntityNotFoundException;
}
