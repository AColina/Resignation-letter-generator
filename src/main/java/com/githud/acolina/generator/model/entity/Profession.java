package com.githud.acolina.generator.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity
public class Profession implements Serializable {

    @Id
    @SequenceGenerator(name = "profession_id_seq",
            sequenceName = "profession_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "profession_id_seq")
    @Column(columnDefinition = "serial", updatable = false, nullable = false, unique = true)
    private Long id;
    @NotNull
    @NotEmpty
    @Size(min = 5, max = 80)
    @Column(unique = true, nullable = false)
    private String name;
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 15)
    @Column(unique = true, nullable = false)
    private String acronimo;

    public String getName() {
        return name;
    }

    public String getAcronimo() {
        return acronimo;
    }
}
