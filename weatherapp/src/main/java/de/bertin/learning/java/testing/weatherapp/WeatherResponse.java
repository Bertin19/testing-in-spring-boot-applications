package de.bertin.learning.java.testing.weatherapp;

public class WeatherResponse {

  private WeatherMain main;

  public WeatherResponse() {

  }

  public WeatherResponse(WeatherMain main) {
    this.main = main;
  }

  public WeatherMain getMain() {
    return main;
  }

  public void setMain(WeatherMain main) {
    this.main = main;
  }
}