package com.altugcagri.smep.service;

import com.altugcagri.smep.TestUtils;
import com.altugcagri.smep.security.UserPrincipal;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractServiceTest {

    public static UserPrincipal currentUser;

    @BeforeClass
    public static void setUp() {
        currentUser = TestUtils.createDummyCurrentUser();
    }
}
