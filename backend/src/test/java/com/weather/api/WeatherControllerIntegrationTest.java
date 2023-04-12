package com.weather.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.api.controllers.WeatherController;
import com.weather.api.models.Coordinates;
import com.weather.api.models.Weather;
import com.weather.api.services.OpenMeteoWeatherService;
import com.weather.api.weatherclient.OpenMeteoWeatherClient;
import jakarta.servlet.ServletContext;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
public class WeatherControllerIntegrationTest {
    @MockBean
    private RestTemplate restTemplate;
    @Autowired
    private OpenMeteoWeatherClient openMeteoWeatherClient;
    @Autowired
    private OpenMeteoWeatherService openMeteoWeatherService;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Test
    public void givenWac_whenServletContext_thenItProvidesWeatherController() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        Assertions.assertNotNull(servletContext);
        Assertions.assertTrue(servletContext instanceof MockServletContext);
        Assertions.assertNotNull(webApplicationContext.getBean("weatherController"));
    }
    @Test
    void getWeather_returnsWeather() throws JsonProcessingException {
        Coordinates coordinates = new Coordinates(49.8125, 24.0);
        String response = "{\"latitude\":49.8125,\"longitude\":24.0,\"generationtime_ms\":0.20503997802734375,\"utc_offset_seconds\":0,\"timezone\":\"GMT\",\"timezone_abbreviation\":\"GMT\",\"elevation\":340.0,\"current_weather\":{\"temperature\":9.2,\"windspeed\":11.8,\"winddirection\":38.0,\"weathercode\":3,\"is_day\":1,\"time\":\"2023-04-10T08:00\"}},[Date:\"Mon, 10 Apr 2023 08:48:33 GMT\", Content-Type:\"application/json; charset=utf-8\", Transfer-Encoding:\"chunked\", Connection:\"keep-alive\"]";
        Mockito.when(restTemplate.getForObject("https://api.open-meteo.com/v1/forecast?latitude=49.812500&longitude=24.000000&current_weather=true", JsonNode.class))
                .thenReturn(new ObjectMapper().readTree(response));
        Weather expectedWeather = new Weather(coordinates, 9.2, 11.8, 38.0, "Overcast", LocalDateTime.parse("2023-04-10T08:00"));
        WeatherController controller = new WeatherController(openMeteoWeatherService);
        Weather actualWeather = controller.weather(coordinates.getLatitude(), coordinates.getLongitude());
        Assertions.assertEquals(expectedWeather, actualWeather);
    }



}
