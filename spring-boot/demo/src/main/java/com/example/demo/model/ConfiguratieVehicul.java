package com.example.demo.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


// This class is used to bind the properties defined in application.properties file with the fields of this class
// The prefix "configuratie-vehicul" is used to map the properties.
@ConfigurationProperties(prefix = "configuratie-vehicul")
@Component
public class ConfiguratieVehicul {

    private String marca="Ford";
    private String tip="Diesel";

    public ConfiguratieVehicul() {
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
