package com.voiq.voiqtest;

import org.robolectric.RobolectricGradleTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAlertDialog;

/**
 * Created by juanchaparro on 30/05/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class MainActivityTest{

    @Before
    public void setUp() throws Exception {
        // setup
    }

    @Test
    public void testSomething() throws Exception {
        // test
    }

}