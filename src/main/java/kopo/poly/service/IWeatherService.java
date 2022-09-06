package kopo.poly.service;

import kopo.poly.dto.WeatherDTO;

public interface IWeatherService {

    String apiURL = "https://api.openweathermap.org/data/3.0/onecall";

    WeatherDTO getWeather(WeatherDTO pDTO) throws Exception;
}
