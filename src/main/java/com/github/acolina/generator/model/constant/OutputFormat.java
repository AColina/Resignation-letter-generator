package com.github.acolina.generator.model.constant;

public enum OutputFormat {
    PDF("pdf"),
    WORD("word");
    private String value;

    OutputFormat(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
