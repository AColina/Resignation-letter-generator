package com.githud.acolina.generator.model.constant;

public enum Gender {
    MASCULINO("male"),
    FEMENIDO("female");
    private String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
