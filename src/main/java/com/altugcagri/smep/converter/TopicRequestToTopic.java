package com.altugcagri.smep.converter;

import com.altugcagri.smep.controller.dto.request.TopicRequest;
import com.altugcagri.smep.persistence.model.Topic;
import org.springframework.core.convert.converter.Converter;

public class TopicRequestToTopic implements Converter<TopicRequest, Topic> {

    @Override
    public Topic convert(TopicRequest source) {

        final Topic topic = Topic.builder()
                .title(source.getTitle())
                .description(source.getDescription())
                .wikiDataSet(source.getWikiData())
                .imageUrl(source.getImageUrl())
                .build();

        if (source.getId() != 0L) {
            topic.setId(source.getId());
        }

        return topic;
    }
}
