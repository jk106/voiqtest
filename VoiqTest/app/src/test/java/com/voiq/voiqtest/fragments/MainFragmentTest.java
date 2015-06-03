package com.voiq.voiqtest.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;
import com.voiq.voiqtest.BuildConfig;
import com.voiq.voiqtest.R;
import com.voiq.voiqtest.activities.MainActivity;
import com.voiq.voiqtest.activities.RegisterActivity;
import com.voiq.voiqtest.activities.TokenActivity;
import com.voiq.voiqtest.api.LogInResponse;
import com.voiq.voiqtest.app.VoiqTestApplication;
import com.voiq.voiqtest.eventbus.LogInRequest;
import com.voiq.voiqtest.eventbus.LogInSuccess;
import com.voiq.voiqtest.eventbus.NetworkError;
import com.voiq.voiqtest.modules.ApiModule;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowProgressDialog;
import org.robolectric.shadows.ShadowToast;
import org.robolectric.util.ActivityController;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by juanchaparro on 30/05/15.
 * Test Suites for the MainFragment
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class MainFragmentTest {

    /**
     * Inject UI Elements from the fragment to use them on the tests
     */

    @InjectView(R.id.txtMail)
    EditText txtMail;

    @InjectView(R.id.txtPass)
    EditText txtPass;

    @InjectView(R.id.btnLogin)
    Button btnLogin;

    @InjectView(R.id.lblApply)
    TextView lblApply;

    /**
     * Inject the event bus, it will be needed for further assertions
     */
    @Inject
    Bus mBus;

    /**
     * Activity instance
     */
    private MainActivity activity;

    /**
     * UUT
     */
    private MainFragment fragment;

    /**
     * Activity Controller instance
     */
    private ActivityController controller;


    /**
     * Testing Module for the tests
     */
    @Module(injects = {MainFragmentTest.class, MainFragment.class, VoiqTestApplication.class},
            overrides = true,
            complete = false
    )
    public class TestModule {
        @Provides
        @Singleton
        Bus provideBus() {
            //Provide a partial mock for the Event Bus
            return Mockito.spy(new Bus(ThreadEnforcer.ANY));
        }
    }

    /**
     * Modules List for the tests
     * @return the list
     */
    protected List<Object> getModules() {
        List<Object> result = new ArrayList<Object>();
        result.add(new ApiModule());
        result.add(new TestModule());
        return result;
    }

    @Before
    public void setUp() throws Exception {
        //Create the object graph and replace the one from the Robolectric application
        ObjectGraph og = ObjectGraph.create(getModules().toArray());
        og.inject(this);
        ((VoiqTestApplication) RuntimeEnvironment.application).setObjectGraph(og);
        //Get the instances and the UI elements
        controller=Robolectric.buildActivity(MainActivity.class).create().start();
        activity = (MainActivity)controller.resume().get();
        fragment = (MainFragment) activity.getSupportFragmentManager().findFragmentById(R.id.mainFragment);
        ButterKnife.inject(this, fragment.getView());
    }

    /**
     * Test that a toast is shown if the form is empty
     */
    @Test
    public void shouldToastOnEmptyForm() {
        btnLogin.performClick();
        assertThat(ShadowToast.getLatestToast(), notNullValue());
    }

    /**
     * Test that the right message is shown in case there is no e-mail
     */
    @Test
    public void shouldToastForEmptyEmail() {
        btnLogin.performClick();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo(fragment.getString(
                R.string.empty_email, activity)));
    }

    /**
     * Test that the right message is shown on invalid e-mail
     */
    @Test
    public void shouldToastOnInvalidEmail() {
        txtMail.setText("aaaa");
        btnLogin.performClick();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo(fragment.getString(
                R.string.invalid_email, activity)));
    }

    /**
     * Test that the right message is shown on empty password
     */
    @Test
    public void shouldToastOnEmptyPassword() {
        txtMail.setText("a@b.com");
        btnLogin.performClick();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo(fragment.getString(
                R.string.empty_pass, activity)));
    }

    /**
     * Test that the progress dialog is shown if the log in form is correctly filled
     */
    @Test
    public void shouldShowProgressDialogOnSubmit() {
        txtMail.setText("aa@aa.com");
        txtPass.setText("1111");
        btnLogin.performClick();
        ProgressDialog pd = (ProgressDialog) ShadowProgressDialog.getLatestDialog();
        assertThat(pd, notNullValue());
    }

    /**
     * Test that the progress dialog is shown again on fragment recreation
     */
    @Test
    public void shouldShowProgressDialogOnRestoreInstanceStateAfterSubmit() {
        txtMail.setText("aa@aa.com");
        txtPass.setText("1111");
        btnLogin.performClick();
        controller.restart();
        activity = (MainActivity)controller.resume().get();
        fragment = (MainFragment) activity.getSupportFragmentManager().findFragmentById(R.id.mainFragment);
        ProgressDialog pd = (ProgressDialog) ShadowProgressDialog.getLatestDialog();
        assertThat(pd, notNullValue());
    }

    /**
     * Test that the log in request event is called after submitting the form
     */
    @Test
    public void shouldCallLogInRequesOnFormSubmit() {
        txtMail.setText("aa@aa.com");
        txtPass.setText("1111");
        btnLogin.performClick();
        Mockito.verify(mBus, Mockito.times(1)).post(Mockito.isA(LogInRequest.class));
    }

    /**
     * Test an alert dialog is shown on Network Error event
     */
    @Test
    public void shouldShowDialogOnNetworkError() {
        NetworkError ne = new NetworkError();
        mBus.post(ne);
        AlertDialog alert = ShadowAlertDialog.getLatestAlertDialog();
        assertThat(alert, notNullValue());
        alert.getButton(AlertDialog.BUTTON_POSITIVE).performClick();
        assertThat(alert.isShowing(), equalTo(false));
    }

    /**
     * Test the Token Activity is started if the request success event has a token
     */
    @Test
    public void shouldStartTokenActivityOnLoginSuccessWithToken() {
        txtMail.setText("aa@aa.com");
        txtPass.setText("1111");
        btnLogin.performClick();
        ProgressDialog pd = (ProgressDialog) ShadowProgressDialog.getLatestDialog();
        LogInSuccess lis = new LogInSuccess();
        LogInResponse lir = new LogInResponse();
        lir.setStatus("success");
        lir.setToken("aaa");
        lis.setResponse(lir);
        mBus.post(lis);
        assertThat(pd.isShowing(), equalTo(false));
        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
        assertThat(TokenActivity.class.getCanonicalName(), equalTo(intent.getComponent().getClassName()));
        assertThat(intent.getStringExtra("token"), equalTo("aaa"));
    }

    /**
     * Test an alert dialog is shown if a success event comes with no token and error status
     */
    @Test
    public void shouldShowDialogOnLogInSuccessWithStatus() {
        txtMail.setText("aa@aa.com");
        txtPass.setText("1111");
        btnLogin.performClick();
        ProgressDialog pd = (ProgressDialog) ShadowProgressDialog.getLatestDialog();
        LogInSuccess lis = new LogInSuccess();
        LogInResponse lir = new LogInResponse();
        lir.setStatus("error");
        lis.setResponse(lir);
        mBus.post(lis);
        assertThat(pd.isShowing(), equalTo(false));
        AlertDialog alert = ShadowAlertDialog.getLatestAlertDialog();
        assertThat(alert, notNullValue());
        alert.getButton(AlertDialog.BUTTON_POSITIVE).performClick();
        assertThat(alert.isShowing(), equalTo(false));
    }

    /**
     * Test the register activity when the apply here label is clicked
     */
    @Test
    public void shouldStartRegisterActivityOnApplyHereClick()
    {
        lblApply.performClick();
        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
        assertThat(RegisterActivity.class.getCanonicalName(), equalTo(intent.getComponent().getClassName()));
    }
}