package com.altugcagri.smep.converter;

import com.altugcagri.smep.controller.dto.request.TopicRequest;
import com.altugcagri.smep.persistence.model.Topic;
import org.springframework.core.convert.converter.Converter;

public class TopicRequestToTopic implements Converter<TopicRequest, Topic> {

    @Override
    public Topic convert(TopicRequest source) {
        return Topic.builder()
                .title(source.getTitle())
                .description(source.getDescription())
                .imageUrl(source.getImageUrl())
                .wikiData(source.getWikiData())
                .build();
    }
}
