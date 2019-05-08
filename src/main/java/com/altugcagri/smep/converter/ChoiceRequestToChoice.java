package com.altugcagri.smep.converter;

import com.altugcagri.smep.controller.dto.request.ChoiceRequest;
import com.altugcagri.smep.persistence.model.Choice;
import org.springframework.core.convert.converter.Converter;

public class ChoiceRequestToChoice implements Converter<ChoiceRequest, Choice> {

    @Override
    public Choice convert(ChoiceRequest source) {
        return Choice.builder()
                .text(source.getText())
                .correct(source.getCorrect())
                .build();
    }
}
