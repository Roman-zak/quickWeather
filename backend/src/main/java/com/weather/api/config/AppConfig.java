package com.weather.api.config;
import com.weather.api.weatherclient.OpenMeteoWeatherClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Bean
    public OpenMeteoWeatherClient openMeteoWeatherClient() {
        return new OpenMeteoWeatherClient(restTemplate());
    }
}
