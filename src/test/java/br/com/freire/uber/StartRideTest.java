package br.com.freire.uber;

import br.com.freire.uber.application.usecase.*;
import br.com.freire.uber.infrastructure.http.SignupRequest;
import br.com.freire.uber.infrastructure.http.SignupResponse;
import br.com.freire.uber.infrastructure.repository.AccountRepository;
import br.com.freire.uber.infrastructure.repository.RideRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StartRideTest {

    @Autowired
    Signup signup;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    RideRepository rideRepository;

    @Test
    @DisplayName("Deve inciar uma corrida")
    public void deveIniciarUmaCorrida() {
        //Given
        String expectedName = "John Doe";
        String expectedEmail = "john.doe" + Math.random() + "@gmail.com";
        String expectedCpf = "87748248800";
        SignupRequest request = new SignupRequest();
        request.setName(expectedName);
        request.setEmail(expectedEmail);
        request.setCpf(expectedCpf);
        request.setPassenger(true);
        request.setDriver(false);
        SignupResponse outputSignup = signup.execute(objectMapper.convertValue(request, new TypeReference<>() {
        }));
        String expectedNameDriver = "John Doe";
        String expectedEmailDriver = "john.doe" + Math.random() + "@gmail.com";
        String expectedCpfDriver = "87748248800";
        String expectedCarPlate = "AAA9999";
        boolean expectedIsPassengerDriver = false;
        boolean expectedIsDriverDriver = true;
        SignupRequest signupRequestDriver = new SignupRequest();
        signupRequestDriver.setName(expectedNameDriver);
        signupRequestDriver.setEmail(expectedEmailDriver);
        signupRequestDriver.setCpf(expectedCpfDriver);
        signupRequestDriver.setPassenger(expectedIsPassengerDriver);
        signupRequestDriver.setDriver(expectedIsDriverDriver);
        signupRequestDriver.setCarPlate(expectedCarPlate);
        SignupResponse outputSignupDriver = signup.execute(objectMapper.convertValue(signupRequestDriver, new TypeReference<>() {
        }));
        var requestRide = new ResquestRide(accountRepository, rideRepository);
        ResquestRide.InputRequestRide inputRequestRide = new ResquestRide.InputRequestRide(
                outputSignup.getAccountId(),
                BigDecimal.valueOf(-27.584905257808835),
                BigDecimal.valueOf(-48.545022195325124),
                BigDecimal.valueOf(-27.496887588317275),
                BigDecimal.valueOf(-48.522234807851476));
        var outputRequestRide = requestRide.execute(inputRequestRide);
        var acceptRide = new AcceptRide(accountRepository, rideRepository);
        AcceptRide.InputAcceptRide inputAcceptRide = new AcceptRide.InputAcceptRide(outputRequestRide.rideId(), outputSignupDriver.getAccountId());
        acceptRide.execute(inputAcceptRide);

        var startRide = new StartRide(rideRepository);
        StartRide.InputStartRide inputStartRide = new StartRide.InputStartRide(outputRequestRide.rideId());
        startRide.execute(inputStartRide);

        var getRide = new GetRide(accountRepository, rideRepository);
        var inputGetRide = new GetRide.InputGetRide(outputRequestRide.rideId());
        //When
        var outputGetRide = getRide.execute(inputGetRide);
        //Then
        assertEquals("in_progress", outputGetRide.status());
        assertEquals(outputRequestRide.rideId(), outputGetRide.rideId());

    }
}
