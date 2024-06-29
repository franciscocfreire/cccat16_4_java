package br.com.freire.uber;

import br.com.freire.uber.domain.Account;
import br.com.freire.uber.application.usecase.GetAccount;
import br.com.freire.uber.application.usecase.Signup;
import br.com.freire.uber.domain.exceptions.ValidationError;
import br.com.freire.uber.infrastructure.http.SignupRequest;
import br.com.freire.uber.infrastructure.http.SignupResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SignupTest {
    @Autowired
    GetAccount getAccount;
    @Autowired
    Signup signup;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve criar uma conta para o passageiro")
    void deveCriarContaParaPassageiro() {
        String expectedName = "John Doe";
        String expectedEmail = "john.doe" + Math.random() + "@gmail.com";
        String expectedCpf = "87748248800";

        SignupRequest request = new SignupRequest();
        request.setName(expectedName);
        request.setEmail(expectedEmail);
        request.setCpf(expectedCpf);
        request.setPassenger(true);
        request.setDriver(false);

        SignupResponse responseSignup = signup.execute(objectMapper.convertValue(request, new TypeReference<>() {
        }));

        assertNotNull(responseSignup);
        assertNotNull(responseSignup.getAccountId());

        Account account = getAccount.getAccount(UUID.fromString(responseSignup.getAccountId()));
        assertEquals(expectedName, account.getName());
        assertEquals(expectedEmail, account.getEmail());
        assertEquals(expectedCpf, account.getCpf());
    }

    @Test
    @DisplayName("Não deve criar uma conta para o passageiro")
    void naoDeveCriarContaParaPassageiro() {

        int expectedError = -3;
        String expectedName = "John";
        String expectedEmail = "john.doe" + Math.random() + "@gmail.com";
        String expectedCpf = "87748248800";

        SignupRequest request = new SignupRequest();
        request.setName(expectedName);
        request.setEmail(expectedEmail);
        request.setCpf(expectedCpf);
        request.setPassenger(true);
        request.setDriver(false);

        ValidationError validationError = assertThrows(ValidationError.class, () -> signup.execute(objectMapper.convertValue(request, new TypeReference<>() {
        })));

        assertEquals(expectedError, validationError.getErrorCode());
    }

    @Test
    @DisplayName("Deve criar uma conta para o motorista")
    void deveCriarContaParaMotorista() {
        String expectedName = "John Doe";
        String expectedEmail = "john.doe" + Math.random() + "@gmail.com";
        String expectedCpf = "87748248800";
        String expectedCarPlate = "DBG9456";


        SignupRequest request = new SignupRequest();
        request.setName(expectedName);
        request.setEmail(expectedEmail);
        request.setCpf(expectedCpf);
        request.setPassenger(false);
        request.setDriver(true);
        request.setCarPlate(expectedCarPlate);

        SignupResponse responseSignup = signup.execute(objectMapper.convertValue(request, new TypeReference<>() {
        }));

        assertNotNull(responseSignup);
        assertNotNull(responseSignup.getAccountId());

        Account account = getAccount.getAccount(UUID.fromString(responseSignup.getAccountId()));
        assertEquals(expectedName, account.getName());
        assertEquals(expectedEmail, account.getEmail());
        assertEquals(expectedCpf, account.getCpf());
    }


}
