package com.altugcagri.smep.converter;

import com.altugcagri.smep.TestUtils;
import com.altugcagri.smep.controller.dto.request.TopicRequest;
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
public class TopicRequestToTopicTest {

    @InjectMocks
    private TopicRequestToTopic sut;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testConvertSuccessfully() {
        //Prepare
        final TopicRequest source = TestUtils.createDummyTopicRequest();
        //Test
        final Topic topic = sut.convert(source);
        //Verify
        assertNotNull(topic);
        assertEquals(topic.getId(), source.getId());
        assertEquals(topic.getDescription(), source.getDescription());
        assertEquals(topic.getTitle(), source.getTitle());
        assertEquals(topic.getImageUrl(), source.getImageUrl());
        assertEquals(topic.getEnrolledUsers(), source.getEnrolledUsers());
        assertEquals(topic.getWikiDataSet(), source.getWikiData());
    }

}
