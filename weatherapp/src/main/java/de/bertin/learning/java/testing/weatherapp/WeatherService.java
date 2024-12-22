package de.bertin.learning.java.testing.weatherapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class WeatherService {

  @Value("${weather.api.key}")
  private String apiKey;


  private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public WeatherResponse getWeather(String city) {
    String url = "https://api.openweathermap.org/data/2.5/weather";
    String units = "metric"; // to get the temperature in Celsius. Imperial is for Fahrenheit.

    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
        .queryParam("q", city)
        .queryParam("appid", apiKey)
        .queryParam("units", units);

    return restTemplate.getForObject(builder.toUriString(), WeatherResponse.class);
  }
}
