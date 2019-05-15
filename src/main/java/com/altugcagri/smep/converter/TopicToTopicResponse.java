package com.altugcagri.smep.converter;

import com.altugcagri.smep.controller.dto.response.TopicResponse;
import com.altugcagri.smep.persistence.model.Topic;
import org.springframework.core.convert.converter.Converter;

public class TopicToTopicResponse implements Converter<Topic, TopicResponse> {

    @Override
    public TopicResponse convert(Topic source) {
        return TopicResponse.builder()
                .id(source.getId())
                .title(source.getTitle())
                .description(source.getDescription())
                .imageUrl(source.getImageUrl())
                .contentList(source.getContentList())
                .createdBy(source.getCreatedBy())
                .creationDateTime(source.getCreatedAt())
                .wikiData(source.getWikiData())
                .build();
    }
}
