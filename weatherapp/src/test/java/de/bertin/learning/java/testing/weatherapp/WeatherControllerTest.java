package de.bertin.learning.java.testing.weatherapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class WeatherControllerTest {
    // You will notice in this test we are not testing any spring components to URL mappings,
    // we are only testing the controller logic.

    public static final String CITY = "Berlin";
    public static final double TEMP = 4.3;

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private WeatherController weatherController;


    @Test
    void getCurrentTemperature() {
        WeatherMain weatherMain = new WeatherMain(TEMP);
        WeatherResponse weatherResponse = new WeatherResponse(weatherMain);
        when(weatherService.getWeather(CITY))
                .thenReturn(weatherResponse);

        ResponseEntity<Double> responseEntity = weatherController.getCurrentTemperature(CITY);

        assert responseEntity.getStatusCode().equals(HttpStatus.OK);
        assert responseEntity.getBody() != null;
        assert responseEntity.getBody().equals(TEMP);
    }

    @Test
    void getCurrentTemperature_error(){
        when(weatherService.getWeather(anyString())).thenThrow(new RuntimeException());
        ResponseEntity<Double> responseEntity = weatherController.getCurrentTemperature(CITY);
        assert responseEntity.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}