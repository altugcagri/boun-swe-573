package com.altugcagri.smep.converter;

import com.altugcagri.smep.controller.dto.request.QuestionRequest;
import com.altugcagri.smep.persistence.model.Question;
import org.springframework.core.convert.converter.Converter;

public class QuestionRequestToQuestion implements Converter<QuestionRequest, Question> {

    @Override
    public Question convert(QuestionRequest source) {
        return Question.builder()
                .text(source.getText())
                .build();
    }
}
