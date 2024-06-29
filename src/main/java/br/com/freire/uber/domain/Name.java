package br.com.freire.uber.domain;

import br.com.freire.uber.domain.exceptions.ValidationError;
import lombok.Getter;

import java.util.regex.Pattern;

@Getter
public class Name {
    private final String value;

    public Name(String name) {
        if (!(Pattern.matches("[a-zA-Z]+ [a-zA-Z]+", name))) throw new ValidationError("Invalid name", -3);
        this.value = name;
    }
}
