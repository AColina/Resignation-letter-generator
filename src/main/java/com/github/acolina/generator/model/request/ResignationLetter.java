package com.github.acolina.generator.model.request;

import com.github.acolina.generator.model.constant.Gender;
import com.github.acolina.generator.model.constant.OutputFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ResignationLetter {

    private String ciudad;
    private String pais;
    private String nombreJefe;
    private String apellidoJefe;
    private Gender genero;
    private long profesion;
    private LocalDate fechaIngreso;
    private LocalDate fechaFin;
    private String motivo;
    private String nombre;
    private String apellido;
    private String cargo;
    private String cedula;
    private OutputFormat format;

    public OutputFormat getFormat() {
        return format;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public Gender getGenero() {
        return genero;
    }

    public long getProfesion() {
        return profesion;
    }

    public String getCargo() {
        return cargo;
    }
}
