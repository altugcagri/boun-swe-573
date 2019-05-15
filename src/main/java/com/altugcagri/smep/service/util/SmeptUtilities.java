package com.altugcagri.smep.service.util;

import com.altugcagri.smep.exception.CreatedByException;

public class SmeptUtilities {

    private SmeptUtilities() {
    }

    public static void checkCreatedBy(String entity, Long userId, Long createdBy) {

        if (!userId.equals(createdBy)) {
            throw new CreatedByException(entity, userId.toString(), createdBy.toString());
        }
    }

}
