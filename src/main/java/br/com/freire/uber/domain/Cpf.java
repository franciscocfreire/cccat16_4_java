package br.com.freire.uber.domain;

import br.com.freire.uber.domain.exceptions.ValidationError;
import lombok.Getter;

@Getter
public class Cpf {
    private final String value;

    public Cpf(String cpf) {
        if (!validate(cpf)) throw new ValidationError("Invalid CPF", -1);
        this.value = cpf;
    }

    public boolean validate(String cpf) {
        int FACTOR_FIRST_DIGIT = 9;
        int FACTOR_SECOND_DIGIT = 10;
        if (cpf == null) return false;
        cpf = removeNonDigits(cpf);
        if (isValidLength(cpf)) return false;
        if (allDigitsEqual(cpf)) return false;
        int firstDigit = calculateDigit(cpf, FACTOR_FIRST_DIGIT);
        int secondDigit = calculateDigit(cpf, FACTOR_SECOND_DIGIT);
        String calculatedCheckDigits = "" + firstDigit + secondDigit;
        String actualCheckDigits = cpf.substring(9, 11);
        return calculatedCheckDigits.equals(actualCheckDigits);
    }

    private static String removeNonDigits(String cpf) {
        cpf = cpf.replaceAll("\\D", "");
        return cpf;
    }

    private static boolean isValidLength(String cpf) {
        if (cpf.length() != 11) {
            return true;
        }
        return false;
    }

    private boolean allDigitsEqual(String cpf) {
        char firstDigit = cpf.charAt(0);
        for (int i = 1; i < cpf.length(); i++) {
            if (cpf.charAt(i) != firstDigit) {
                return false;
            }
        }
        return true;
    }

    private int calculateDigit(String cpf, int length) {
        int sum = 0;
        int weight = length + 1;
        for (int i = 0; i < length; i++) {
            sum += (cpf.charAt(i) - '0') * weight--;
        }
        int remainder = sum % 11;
        return (remainder < 2) ? 0 : 11 - remainder;
    }
}
