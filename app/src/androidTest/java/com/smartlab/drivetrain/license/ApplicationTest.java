package com.smartlab.drivetrain.license;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void test () throws Exception{
        final int i = 1;
        final int j = 1;
        assertEquals(i,j);
    }
}