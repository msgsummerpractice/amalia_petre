package com.example;

import java.beans.BeanProperty;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.example") // cauta Bean-ul cu adnotatia @Component (adica) Hellos.class
public class AppConfig {
    
    //@Qualifier("helloRo")
    @Bean(name="helloRo")
    public HelloWorld helloRo(){
        HelloWorld helloRo = new HelloWorld();
        helloRo.setMessage("Salut Primavara!");
        return helloRo;
    }

    @Bean
    oublic HelloWorld helloFr(){
        HelloWorld helloFr = new HelloWorld();
        helloFr.setMessage("Bonjour le printemps!");
        return helloFr;
    }

    @Bean
    //@Qualifier("helloEn")
    public HelloWorld helloEn(){
        HelloWorld helloEn = new HelloWorld();
        helloEn.setMessage("Hello Spring!");
        return helloEn;
    }
}
