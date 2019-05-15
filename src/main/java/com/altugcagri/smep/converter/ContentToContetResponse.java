package com.altugcagri.smep.converter;

import com.altugcagri.smep.controller.dto.response.ContentResponse;
import com.altugcagri.smep.persistence.model.Content;
import org.springframework.core.convert.converter.Converter;

public class ContentToContetResponse implements Converter<Content, ContentResponse> {

    @Override
    public ContentResponse convert(Content source) {
        return ContentResponse.builder()
                .id(source.getId())
                .title(source.getTitle())
                .text(source.getText())
                .topicId(source.getTopic().getId())
                .build();
    }
}
