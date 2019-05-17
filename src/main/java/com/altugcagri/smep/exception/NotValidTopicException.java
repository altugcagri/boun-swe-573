package com.altugcagri.smep.exception;

public class NotValidTopicException extends RuntimeException {

    public NotValidTopicException(String topicId, String message) {

        super(String
                .format("Topic with id: '%s' is not valid Reason: '%s'", topicId, message));
    }
}
