package com.githud.acolina.generator.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.githud.acolina.generator.core.exception.EntityValidatedException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Angel Colina
 * @version 1.0
 */
@Getter
@Setter
public class ResponseDTO<T> implements Serializable {

    private boolean error;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("error_msg")
    private String errorMessage;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, List<String>> errorField;
    private T object;

    public static <T> ResponseEntity<ResponseDTO<T>> ok(T object) {
        if (Objects.isNull(object)) {
            return error("Error desconocido. Vuelva a intentar");
        }
        ResponseDTO<T> dto = new ResponseDTO<>();
        dto.error = false;
        dto.errorMessage = null;
        dto.object = object;
        return ResponseEntity.ok(dto);
    }

    public static <T> ResponseEntity<ResponseDTO<T>> error(@Nullable String errorMessage) {
        ResponseDTO<T> dto = new ResponseDTO<>();
        dto.error = true;
        dto.errorMessage = errorMessage;
        dto.object = null;
        return ResponseEntity.ok(dto);
    }

    public static <T> ResponseEntity<ResponseDTO<T>> error(@Nullable EntityValidatedException ex) {
        ResponseDTO<T> dto = new ResponseDTO<>();
        dto.error = true;
        dto.errorMessage = ex.getMessage();
        dto.object = null;
        dto.errorField = ex.getErrors();
        return ResponseEntity.ok(dto);
    }

    public static <T> ResponseEntity<ResponseDTO<T>> error(@Nullable Exception ex) {
        return error(ex.toString());
    }
}
