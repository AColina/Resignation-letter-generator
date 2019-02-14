package com.github.acolina.generator.service.impl;

import com.github.acolina.generator.core.exception.EntityValidatedException;
import com.github.acolina.generator.model.entity.Profession;
import com.github.acolina.generator.repository.ProfessionRepository;
import com.github.acolina.generator.service.ProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProfessionalServiceImpl implements ProfessionalService {

    private final ProfessionRepository repository;
    private final Validator validator;

    @Autowired
    public ProfessionalServiceImpl(ProfessionRepository repository, Validator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public Profession findByName(String name) {
        return repository.findByName(name);
    }

    public List<Profession> findByNameLike(String name) {
        return repository.findByNameLike(name);
    }

    public List<Profession> findAll() {
        return repository.findAll();
    }

    public Profession save(Profession s) throws EntityValidatedException {
        Set<ConstraintViolation<Object>> error = validator.validate(s);
        if (!error.isEmpty()) {
            throw new EntityValidatedException(error);
        }

        return repository.saveAndFlush(s);
    }

    public Optional<Profession> findById(Long aLong) {
        return repository.findById(aLong);
    }

    public boolean existsById(Long aLong) {
        return repository.existsById(aLong);
    }

    public long count() {
        return repository.count();
    }

    public void deleteById(Long aLong) {
        repository.deleteById(aLong);
    }
}
