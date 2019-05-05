package com.altugcagri.smep.util;

import com.altugcagri.smep.controller.dto.response.TopicResponse;
import com.altugcagri.smep.controller.dto.response.UserSummary;
import com.altugcagri.smep.persistence.model.Topic;
import com.altugcagri.smep.persistence.model.User;

public class ModelMapper {

    public static TopicResponse mapTopicToTopicResponse(Topic topic, User creator){
        TopicResponse topicResponse = new TopicResponse();
        topicResponse.setId(topic.getId());
        topicResponse.setTitle(topic.getTitle());
        topicResponse.setDescription(topic.getDescription());
        topicResponse.setCreationDateTime(topic.getCreatedAt());
        topicResponse.setWikiData(topic.getWikiData());
        topicResponse.setContentList(topic.getContentList());

        UserSummary creatorSummary = new UserSummary(creator.getId(), creator.getUsername(), creator.getName());
        topicResponse.setCreatedBy(creatorSummary);

        return topicResponse;
    }
}
