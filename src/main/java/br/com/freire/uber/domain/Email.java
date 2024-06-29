package br.com.freire.uber.domain;

import br.com.freire.uber.domain.exceptions.ValidationError;
import lombok.Getter;

import java.util.regex.Pattern;

@Getter
public class Email {
    private final String value;

    public Email(String email) {
        if (!Pattern.matches("^(.+)@(.+)$", email)) throw new ValidationError("Invalid email", -2);
        this.value = email;
    }
}