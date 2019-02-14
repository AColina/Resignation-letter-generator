package com.githud.acolina.generator.service;

import com.githud.acolina.generator.core.exception.EntityNotFoundException;
import com.githud.acolina.generator.model.dto.ResignationLetterDTO;
import com.githud.acolina.generator.model.request.ResignationLetter;

public interface ResignationLetterGeneratorService {

    ResignationLetterDTO generateResignationLetterDTO(ResignationLetter resignationLetter) throws EntityNotFoundException;
}
