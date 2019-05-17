package com.altugcagri.smep.converter;

import com.altugcagri.smep.TestUtils;
import com.altugcagri.smep.controller.dto.request.TopicRequest;
import com.altugcagri.smep.controller.dto.response.TopicResponse;
import com.altugcagri.smep.persistence.model.Topic;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class TopicToTopicResponseTest {


    @InjectMocks
    private TopicToTopicResponse sut;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testConvertSuccessfully() {
        //Prepare
        final Topic source = TestUtils.createDummyTopic();
        //Test
        final TopicResponse topicResponse = sut.convert(source);
        //Verify
        assertNotNull(topicResponse);
        assertEquals(topicResponse.getId(), source.getId());
        assertEquals(topicResponse.getContentList(), source.getContentList());
        assertEquals(topicResponse.getDescription(), source.getDescription());
        assertEquals(topicResponse.getImageUrl(), source.getImageUrl());
        assertEquals(topicResponse.getTitle(), source.getTitle());
        assertEquals(topicResponse.getWikiData(), source.getWikiDataSet());
    }
}
