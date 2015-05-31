package com.voiq.voiqtest.util;

import android.app.Activity;

/**
 * Created by juanchaparro on 31/05/15.
 */
public class TestUtilities {

    public static String getStringById(int id, Activity activity)
    {
        return activity.getResources().getString(id);
    }
}
