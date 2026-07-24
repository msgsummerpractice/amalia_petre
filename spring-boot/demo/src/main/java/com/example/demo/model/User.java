package com.example.demo.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import com.example.demo.model.ConfiguratieVehicul;

public class User {

    private Long id;
    private String name;
    private int age;
    @Autowired
    private ConfiguratieVehicul configuratieVehicul;

    public User(){}

    public User(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.configuratieVehicul = new ConfiguratieVehicul();
    }

    public Long getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getConfiguratieVehicul() {
        return configuratieVehicul.getMarca() + " " + configuratieVehicul.getTip();
    }
    
}
