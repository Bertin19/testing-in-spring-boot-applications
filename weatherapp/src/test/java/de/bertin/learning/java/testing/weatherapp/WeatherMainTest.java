package de.bertin.learning.java.testing.weatherapp;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
class WeatherMainTest {

    @Test
    void getTemp() {
        WeatherMain weatherMain = new WeatherMain(10.0);
        assertEquals(10.0, weatherMain.getTemp());
    }

    @Test
    void setTemp() {
        WeatherMain weatherMain = new WeatherMain();
        weatherMain.setTemp(12.0);
        assertEquals(12.0, weatherMain.getTemp());
    }

}