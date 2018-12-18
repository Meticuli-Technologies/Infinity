package com.meti.lib.util;

import com.meti.lib.convert.Converter;

import java.util.Properties;

public class PropertiesUtil {
    private PropertiesUtil(){}

    public static <T> T fromProperties(Properties properties, String propertyKey, Converter<T> converter) {
        return Converter.applyConverter(properties.get(propertyKey), converter);
    }
}
