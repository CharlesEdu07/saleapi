package com.charlesedu.saleapi.web.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToBooleanConverter implements Converter<String, Boolean> {

    @Override
    public Boolean convert(String text) {
        text = text.toLowerCase().trim();

        if (text.equals("true")) {
            return true;
        } else if (text.equals("false")) {
            return false;
        } else {
            return null;
        }
    }
}
