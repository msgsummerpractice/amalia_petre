package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Hellos {

    @Autowired
    @Qualifier("helloRo")
    private HelloWorld helloRo;
    @Autowired
    @Qualifier("helloEn")
    private HelloWorld helloEn;

    public void getHellos(){
        helloRo.getMessage();
        helloEn.getMessage();
    }
    
}
