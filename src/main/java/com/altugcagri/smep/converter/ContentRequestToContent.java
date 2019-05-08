package com.altugcagri.smep.converter;

import com.altugcagri.smep.controller.dto.request.ContentRequest;
import com.altugcagri.smep.persistence.model.Content;
import org.springframework.core.convert.converter.Converter;

public class ContentRequestToContent implements Converter<ContentRequest, Content> {

    @Override
    public Content convert(ContentRequest source) {
        return Content.builder()
                .title(source.getTitle())
                .text(source.getText())
                .build();
    }
}
