package com.githud.acolina.generator.service;

import com.githud.acolina.generator.core.exception.EntityValidatedException;
import com.githud.acolina.generator.model.entity.Profession;

import java.util.List;
import java.util.Optional;

public interface ProfessionalService {

    Profession findByName(String name);

    List<Profession> findByNameLike(String name);

    List<Profession> findAll();

    Profession save(Profession s) throws EntityValidatedException;

    Optional<Profession> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);
}
