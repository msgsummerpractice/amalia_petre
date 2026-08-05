package com.example.spring_data_jpa.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.JacksonXmlHttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import tools.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class Config {

    @Bean
    public JacksonXmlHttpMessageConverter mappingJackson2HttpMessageConverter(XmlMapper xmlMapper) {
        return new JacksonXmlHttpMessageConverter(xmlMapper);
    }

}
