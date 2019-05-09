package com.altugcagri.smep.config;

import com.altugcagri.smep.converter.ChoiceRequestToChoice;
import com.altugcagri.smep.converter.ContentRequestToContent;
import com.altugcagri.smep.converter.QuestionRequestToQuestion;
import com.altugcagri.smep.converter.TopicRequestToTopic;
import com.altugcagri.smep.converter.TopicToTopicResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.core.convert.support.GenericConversionService;

@Configuration
public class ConverterConfig {

    @Bean
    public ConfigurableConversionService smepConversionService() {
        final ContentRequestToContent contentRequestToContent = new ContentRequestToContent();
        final QuestionRequestToQuestion questionRequestToQuestion = new QuestionRequestToQuestion();
        final ChoiceRequestToChoice choiceRequestToChoice = new ChoiceRequestToChoice();
        final TopicRequestToTopic topicRequestToTopic = new TopicRequestToTopic();
        final TopicToTopicResponse topicToTopicResponse = new TopicToTopicResponse();
        final ConfigurableConversionService conversionService = new GenericConversionService();
        conversionService.addConverter(contentRequestToContent);
        conversionService.addConverter(questionRequestToQuestion);
        conversionService.addConverter(choiceRequestToChoice);
        conversionService.addConverter(topicRequestToTopic);
        conversionService.addConverter(topicToTopicResponse);
        return conversionService;
    }
}
