package de.bertin.learning.java.testing.weatherapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class WeatherServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private WeatherService weatherService;

    private static final String CITY = "Berlin";
    private static final double TEMP = 4.3;
    private static final String API = "123456";

    @BeforeEach
    void setUp() {
        // Use to set the value of the apiKey field in the WeatherService class, we can use ReflectionTestUtils.setField() from the Spring Test module.
        ReflectionTestUtils.setField(weatherService, "apiKey", API);
    }

    @Test
    void getWeather() {
        WeatherMain weatherMain = new WeatherMain(TEMP);
        WeatherResponse mockWeatherResponse = new WeatherResponse(weatherMain);
        // Use when() and thenReturn() to mock the behavior of the restTemplate.getForObject() method.
        when(
                restTemplate
                        .getForObject("https://api.openweathermap.org/data/2.5/weather?q=" + CITY
                                + "&appid="+API+"&units=metric",
                                WeatherResponse.class)
        )
                .thenReturn(mockWeatherResponse);

        WeatherResponse response = weatherService.getWeather(CITY);
        assertEquals(mockWeatherResponse, response);
    }

    @Test
    void getWeather_apiError() {
        when(
                restTemplate
                        .getForObject("https://api.openweathermap.org/data/2.5/weather?q=" + CITY
                                + "&appid="+API+"&units=metric",
                                WeatherResponse.class)
        )
                .thenThrow(new RestClientException("API error"));

        Exception exception = assertThrows(RestClientException.class, () -> weatherService.getWeather(CITY));
        assertEquals("API error", exception.getMessage());
    }
}