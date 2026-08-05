package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class HelloWorld {
    private String message;

    public void setMessage(String message){
        this.message  = message;
    }

    public void getMessage(){
        System.out.println("Your Message : " + message);
    }

}
