package com.github.rahmnathan.weather.openweathermap.current;

import com.github.rahmnathan.weather.current.CurrentWeather;
import org.junit.Test;

public class OpenWeatherMapCurrentWeatherProviderTest {
    private final OpenWeatherMapCurrentWeatherProvider weatherProvider = new OpenWeatherMapCurrentWeatherProvider("6ee148d52a5d1ea92490c4ce7b649b45");

    @Test
    public void getContentTest(){
        CurrentWeather currentWeather = weatherProvider.getCurrentWeather(55416);
        System.out.println(currentWeather.getSky());
        System.out.println(currentWeather.getTemp());
    }
}
