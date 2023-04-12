package com.weather.api;

import com.weather.api.models.Coordinates;
import com.weather.api.models.Weather;
import com.weather.api.services.OpenMeteoWeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class WeatherControllerIntegrationTestMockedService {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OpenMeteoWeatherService openMeteoWeatherService;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }
    @Test
    void weatherControllerMockMvc_returnWeather() throws Exception {
        Coordinates coordinates = new Coordinates(49.8125, 24.0);
        Weather expectedWeather = new Weather(coordinates, 9.2, 11.8, 38.0, "Overcast", LocalDateTime.parse("2023-04-10T08:00"));

        Mockito.when(openMeteoWeatherService.getCurrentWeather(coordinates)).thenReturn(expectedWeather);

        mockMvc.perform(MockMvcRequestBuilders.get("/currentWeather")
                        .param("latitude", "49.8125")
                        .param("longitude", "24.0"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.coordinates.latitude").value(49.8125))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coordinates.longitude").value(24.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.temperature").value(9.2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.windspeed").value(11.8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.winddirection").value(38.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Overcast"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.time").value("2023-04-10T08:00:00"));



    }
    @Test
    void weatherControllerMockMvc_badRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/currentWeather")
                        .param("latitude", "45.0")
                        .param("longitude", "180.0"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}
