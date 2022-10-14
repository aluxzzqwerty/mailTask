package com.epam.service;

import java.util.ResourceBundle;

public class DataReader {

    public static String getData(String environment, String key) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(System.getProperty(environment));
        return resourceBundle.getString(key);
    }
}
