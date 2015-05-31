package com.voiq.voiqtest.activities;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import com.voiq.voiqtest.BuildConfig;
import com.voiq.voiqtest.R;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by juanchaparro on 30/05/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class TokenActivityTest {

    @InjectView(R.id.txtToken)
    EditText txtToken;

    @InjectView(R.id.btnLogout)
    Button btnLogout;

    private TokenActivity activity;

    @Before
    public void setUp() throws Exception {
        // setup
        activity= Robolectric.buildActivity(TokenActivity.class).create().start().resume().get();
        ButterKnife.inject(this, activity);
    }

    @Test
    public void shouldFinishOnLogOutClick() throws Exception {
        // test
        btnLogout.performClick();
        assertThat(Shadows.shadowOf(activity).isFinishing(), equalTo(true));
    }

    @Test
    public void shouldDisplayToken()
    {
        Intent intent = new Intent();
        String token = "thetoken";
        intent.putExtra("token",token);
        activity= Robolectric.buildActivity(TokenActivity.class).withIntent(intent).create().
                start().resume().get();
        ButterKnife.inject(this, activity);
        assertThat(txtToken.getText().toString(), equalTo(token));
    }

}