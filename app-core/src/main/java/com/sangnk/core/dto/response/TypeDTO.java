package com.sangnk.core.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TypeDTO<T> {
    private String name;
    private T type;

    public TypeDTO(String name, T type) {
        this.name = name;
        this.type = type;
    }
}
