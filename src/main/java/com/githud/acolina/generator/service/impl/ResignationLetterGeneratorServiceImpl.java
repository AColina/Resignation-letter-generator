package com.githud.acolina.generator.service.impl;

import com.githud.acolina.generator.core.exception.EntityNotFoundException;
import com.githud.acolina.generator.model.dto.ResignationLetterDTO;
import com.githud.acolina.generator.model.entity.Profession;
import com.githud.acolina.generator.model.request.ResignationLetter;
import com.githud.acolina.generator.service.ProfessionalService;
import com.githud.acolina.generator.service.ResignationLetterGeneratorService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static java.time.format.DateTimeFormatter.ofPattern;

@Service
public class ResignationLetterGeneratorServiceImpl implements ResignationLetterGeneratorService {

    private static final DateTimeFormatter FORMATTER = ofPattern("dd 'de' MMMM 'del' yyyy");

    private final ModelMapper mapper;
    @Resource
    private Environment environment;
    private final ProfessionalService professionalService;

    @Autowired
    public ResignationLetterGeneratorServiceImpl(ModelMapper mapper, ProfessionalService professionalService) {
        this.mapper = mapper;
        this.professionalService = professionalService;
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Override
    public ResignationLetterDTO generateResignationLetterDTO(ResignationLetter resignationLetter) throws EntityNotFoundException {

        ResignationLetterDTO dto = mapper.map(resignationLetter, ResignationLetterDTO.class);


        String message1 = String.format(environment.getProperty("message1"),
                formatDate(resignationLetter.getFechaIngreso()),
                resignationLetter.getCargo());

        String message2 = String.format(environment.getProperty("message2"),
                formatDate(resignationLetter.getFechaFin()));

        dto.setMessage1(message1);
        dto.setMessage2(message2);
        dto.setMessage3(environment.getProperty("message3"));
        dto.setFecha(formatDate(LocalDate.now()));
        dto.setLastPage(environment.getProperty("final"));
        dto.setSexo(environment.getProperty(resignationLetter.getGenero().getValue()));

        Optional<Profession> optional = professionalService.findById(resignationLetter.getProfesion());

        Profession profession = optional.orElseThrow(() -> EntityNotFoundException.createInstance(resignationLetter.getProfesion()));

        dto.setProfession(profession.getAcronimo());
        return dto;
    }

    private String formatDate(LocalDate date) {
        return date.format(FORMATTER);
    }
}
