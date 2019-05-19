package com.altugcagri.smep.controller;


import com.altugcagri.smep.TestUtils;
import com.altugcagri.smep.persistence.model.User;
import com.altugcagri.smep.security.UserPrincipal;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;

@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractEntityControllerTest {

    public static UserPrincipal currentUser;

    @BeforeClass
    public static void setUp() {
        currentUser = TestUtils.createDummyCurrentUser();
    }
}
