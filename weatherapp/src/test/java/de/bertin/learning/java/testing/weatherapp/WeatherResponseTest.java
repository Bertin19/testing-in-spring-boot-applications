package de.bertin.learning.java.testing.weatherapp;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
class WeatherResponseTest {

    @Test
    void getMain() {
        WeatherMain weatherMain = new WeatherMain(10.0);
        WeatherResponse weatherResponse = new WeatherResponse(weatherMain);
        assertEquals(weatherMain, weatherResponse.getMain());
    }

    @Test
    void setMain() {
        WeatherMain weatherMain = new WeatherMain(12.0);
        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setMain(weatherMain);
        assertEquals(weatherMain, weatherResponse.getMain());
    }
}