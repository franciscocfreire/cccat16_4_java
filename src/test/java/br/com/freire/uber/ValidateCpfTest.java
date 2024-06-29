package br.com.freire.uber;

import br.com.freire.uber.domain.Cpf;
import br.com.freire.uber.domain.exceptions.ValidationError;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class ValidateCpfTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "97456321558",
            "71428793860",
            "87748248800"
    })
    void deveTestarCpfValido(String cpf) {
        Cpf cpfValid = new Cpf(cpf);
        assertNotNull(cpfValid);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "11111111111",
            "123",
            "1234566789123456789"
    })
    void deveTestarCpfInvalido(String cpf) {
        assertThrows(ValidationError.class, () -> new Cpf(cpf));
    }
}
