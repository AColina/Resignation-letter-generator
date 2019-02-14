package com.github.acolina.generator.controller;

import com.github.acolina.generator.core.exception.EntityNotFoundException;
import com.github.acolina.generator.core.exception.EntityValidatedException;
import com.github.acolina.generator.model.entity.Profession;
import com.github.acolina.generator.model.response.ResponseDTO;
import com.github.acolina.generator.service.ProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/profession")
public class ProfessionController  {
    private final ProfessionalService service;

    @Autowired
    public ProfessionController(ProfessionalService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)

    public @ResponseBody
    ResponseEntity<List<Profession>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<ResponseDTO<Profession>> findOne(@RequestParam("id") long id) {
        Optional<Profession> optional = service.findById(id);
        if (optional.isPresent()) {
            return ResponseDTO.ok(optional.get());
        } else {
            return ResponseDTO.error(EntityNotFoundException.createInstance(id));
        }
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ResponseEntity<List<Profession>> findName(@RequestParam("name") String name) {
        List<Profession> optional = service.findByNameLike(name);
        return ResponseEntity.ok(optional);
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<ResponseDTO<Profession>> save(@RequestBody Profession dto) {
        try {
            Profession clientDTO = service.save(dto);
            return ResponseDTO.ok(clientDTO);
        } catch (EntityValidatedException e) {
            return ResponseDTO.error(e);
        } catch (Exception e) {
            throw e;
        }
    }
}
