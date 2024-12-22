package de.bertin.learning.java.testing.weatherapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class WeatherappApplicationIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockitoBean
    private RestTemplate restTemplate;

    @Test
    void getCurrentTemperature() {
        WeatherMain weatherMain = new WeatherMain(4.08);
        WeatherResponse mockWeatherResponse = new WeatherResponse(weatherMain);
        when(restTemplate.getForObject("https://api.openweathermap.org/data/2.5/weather?q=Berlin&appid=123456&units=metric", WeatherResponse.class))
                .thenReturn(mockWeatherResponse);

        Double temperature = testRestTemplate.getForObject("/getCurrentTemperature/Berlin", Double.class);

        assertEquals(4.08, temperature);
    }
}