package com.voiq.voiqtest.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;
import com.voiq.voiqtest.BuildConfig;
import com.voiq.voiqtest.R;
import com.voiq.voiqtest.activities.RegisterActivity;
import com.voiq.voiqtest.api.RegisterResponse;
import com.voiq.voiqtest.app.VoiqTestApplication;
import com.voiq.voiqtest.eventbus.NetworkError;
import com.voiq.voiqtest.eventbus.RegisterRequest;
import com.voiq.voiqtest.eventbus.RegisterSuccess;
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
import org.robolectric.fakes.RoboMenuItem;
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
import retrofit.RetrofitError;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by juanchaparro on 30/05/15.
 * Test Suites for the Register Fragment
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class RegisterFragmentTest {

    /**
     * Inject UI Elements from the fragment to use them on the tests
     */

    @InjectView(R.id.txtFirstName)
    EditText txtFirstName;

    @InjectView(R.id.txtLastName)
    EditText txtLastName;

    @InjectView(R.id.txtEmail)
    EditText txtEmail;

    @InjectView(R.id.txtZipCode)
    EditText txtZipCode;

    @InjectView(R.id.txtPhone)
    EditText txtPhone;

    @InjectView(R.id.txtPassword)
    EditText txtPassword;

    @InjectView(R.id.txtPasswordConfirmation)
    EditText txtPasswordConfirmation;

    @InjectView(R.id.btnSelectDate)
    Button btnSelectDate;

    @InjectView(R.id.btnRegister)
    Button btnRegister;

    /**
     * Inject the event bus, it will be needed for further assertions
     */
    @Inject
    Bus mBus;

    /**
     * Activity instance
     */
    private RegisterActivity activity;

    /**
     * UUT
     */
    private RegisterFragment fragment;

    /**
     * Activity Controller instance
     */
    private ActivityController controller;

    /**
     * Testing Module for the tests
     */
    @Module(injects = {RegisterFragmentTest.class, RegisterFragment.class, VoiqTestApplication.class},
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
        controller = Robolectric.buildActivity(RegisterActivity.class).create().start();
        activity = (RegisterActivity) controller.resume().get();
        fragment = (RegisterFragment) activity.getSupportFragmentManager().findFragmentById(R.id.registerFragment);
        ButterKnife.inject(this, fragment.getView());
    }

    /**
     * Test that a toast is shown if the form is empty
     */
    @Test
    public void shouldToastOnEmptyForm() throws Exception {
        btnRegister.performClick();
        assertThat(ShadowToast.getLatestToast(), notNullValue());
    }

    /**
     * Test that a toast is shown if there is no first name
     */
    @Test
    public void shouldToastForEmptyFirstName() {
        btnRegister.performClick();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo(fragment.getString(
                R.string.empty_fn, activity)));
    }

    /**
     * Test that a toast is shown if there is no last name
     */
    @Test
    public void shouldToastForEmptyLastName() {
        txtFirstName.setText("a");
        btnRegister.performClick();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo(fragment.getString(
                R.string.empty_ln, activity)));
    }

    /**
     * Test that the right message is shown in case there is no e-mail
     */
    @Test
    public void shouldToastForEmptyEmail() throws Exception {
        txtFirstName.setText("a");
        txtLastName.setText("b");
        btnRegister.performClick();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo(fragment.getString(
                R.string.empty_email, activity)));
    }

    /**
     * Test that the right message is shown in case ther is no phone number
     */
    @Test
    public void shouldToastForEmptyPhone() {
        txtFirstName.setText("a");
        txtLastName.setText("b");
        txtEmail.setText("a@b.com");
        btnRegister.performClick();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo(fragment.getString(
                R.string.empty_phone, activity)));
    }

    /**
     * Test the right message is shown on empty zip code
     */
    @Test
    public void shouldToastForEmptyZipCode() {
        txtFirstName.setText("a");
        txtLastName.setText("b");
        txtEmail.setText("a@b.com");
        txtPhone.setText("111");
        btnRegister.performClick();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo(fragment.getString(
                R.string.empty_zc, activity)));
    }

    /**
     * Test that the right message is shown on no date selected
     */
    @Test
    public void shouldToastForBirthDate() {
        txtFirstName.setText("a");
        txtLastName.setText("b");
        txtEmail.setText("a@b.com");
        txtPhone.setText("111");
        txtZipCode.setText("10128");
        btnRegister.performClick();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo(fragment.getString(
                R.string.empty_bd, activity)));
    }

    /**
     * Test that the right message is shown on empty password
     */
    @Test
    public void shouldToastForEmptyPassword() {
        txtFirstName.setText("a");
        txtLastName.setText("b");
        txtEmail.setText("a@b.com");
        txtPhone.setText("111");
        txtZipCode.setText("10128");
        btnSelectDate.setText("1986-01-01");
        btnRegister.performClick();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo(fragment.getString(
                R.string.empty_pass, activity)));
    }

    /**
     * Test that the right message is shown on empty password confirmation
     */
    @Test
    public void shouldToastForEmptyConfirm() {
        txtFirstName.setText("a");
        txtLastName.setText("b");
        txtEmail.setText("a@b.com");
        txtPhone.setText("111");
        txtZipCode.setText("10128");
        btnSelectDate.setText("1986-01-01");
        txtPassword.setText("123");
        btnRegister.performClick();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo(fragment.getString(
                R.string.empty_confirm, activity)));
    }

    /**
     * Test that the right message is shown on password mismatch
     */
    @Test
    public void shouldToastForPasswordMismatch() {
        txtFirstName.setText("a");
        txtLastName.setText("b");
        txtEmail.setText("a@b.com");
        txtPhone.setText("111");
        txtZipCode.setText("10128");
        btnSelectDate.setText("1986-01-01");
        txtPassword.setText("123");
        txtPasswordConfirmation.setText("124");
        btnRegister.performClick();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo(fragment.getString(
                R.string.password_mismatch, activity)));
    }

    /**
     * Test that the right message is shown on invalid e-mail
     */
    @Test
    public void shouldToastForInvalidEmail() {
        txtFirstName.setText("a");
        txtLastName.setText("b");
        txtEmail.setText("ab.com");
        txtPhone.setText("111");
        txtZipCode.setText("10128");
        btnSelectDate.setText("1986-01-01");
        txtPassword.setText("123");
        txtPasswordConfirmation.setText("123");
        btnRegister.performClick();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo(fragment.getString(
                R.string.invalid_email, activity)));
    }

    /**
     * Test that the progress dialog is shown if the log in form is correctly filled
     */
    @Test
    public void shouldShowProgressDialogOnSubmit() {
        txtFirstName.setText("a");
        txtLastName.setText("b");
        txtEmail.setText("a@b.com");
        txtPhone.setText("111");
        txtZipCode.setText("10128");
        btnSelectDate.setText("1986-01-01");
        txtPassword.setText("123");
        txtPasswordConfirmation.setText("123");
        btnRegister.performClick();
        ProgressDialog pd = (ProgressDialog) ShadowProgressDialog.getLatestDialog();
        assertThat(pd, notNullValue());
    }

    /**
     * Test that the progress dialog is shown again on fragment recreation
     */
    @Test
    public void shouldShowProgressDialogOnRestoreInstanceStateAfterSubmit() {
        txtFirstName.setText("a");
        txtLastName.setText("b");
        txtEmail.setText("a@b.com");
        txtPhone.setText("111");
        txtZipCode.setText("10128");
        btnSelectDate.setText("1986-01-01");
        txtPassword.setText("123");
        txtPasswordConfirmation.setText("123");
        btnRegister.performClick();
        controller.restart();
        activity = (RegisterActivity) controller.resume().get();
        fragment = (RegisterFragment) activity.getSupportFragmentManager().findFragmentById(R.id.registerFragment);
        ProgressDialog pd = (ProgressDialog) ShadowProgressDialog.getLatestDialog();
        assertThat(pd, notNullValue());
    }

    /**
     * Test that the Register Request Event is triggered when the form is correctly filled
     */
    @Test
    public void shouldCallRegisterRequestOnFormSubmit() {
        txtFirstName.setText("a");
        txtLastName.setText("b");
        txtEmail.setText("a@b.com");
        txtPhone.setText("111");
        txtZipCode.setText("10128");
        btnSelectDate.setText("1986-01-01");
        txtPassword.setText("123");
        txtPasswordConfirmation.setText("123");
        btnRegister.performClick();
        Mockito.verify(mBus, Mockito.times(1)).post(Mockito.isA(RegisterRequest.class));
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
     * Test that the activity is finished on successful response and dialog click
     */
    @Test
    public void shouldFinishOnRegisterSuccessAndDialogClick() {
        txtFirstName.setText("a");
        txtLastName.setText("b");
        txtEmail.setText("a@b.com");
        txtPhone.setText("111");
        txtZipCode.setText("10128");
        btnSelectDate.setText("1986-01-01");
        txtPassword.setText("123");
        txtPasswordConfirmation.setText("123");
        btnRegister.performClick();
        ProgressDialog pd = (ProgressDialog) ShadowProgressDialog.getLatestDialog();
        RegisterSuccess rs = new RegisterSuccess();
        RegisterResponse rp = new RegisterResponse();
        rp.setStatus("success");
        rs.setResponse(rp);
        mBus.post(rs);
        assertThat(pd.isShowing(), equalTo(false));
        AlertDialog alert = ShadowAlertDialog.getLatestAlertDialog();
        assertThat(alert, notNullValue());
        alert.getButton(AlertDialog.BUTTON_POSITIVE).performClick();
        assertThat(Shadows.shadowOf(activity).isFinishing(), equalTo(true));
    }

    /**
     * Test an alert dialog is shown if a success event comes with no token and error status
     */
    @Test
    public void shouldShowDialogOnRegisterSuccessWithStatus() {
        txtFirstName.setText("a");
        txtLastName.setText("b");
        txtEmail.setText("a@b.com");
        txtPhone.setText("111");
        txtZipCode.setText("10128");
        btnSelectDate.setText("1986-01-01");
        txtPassword.setText("123");
        txtPasswordConfirmation.setText("123");
        btnRegister.performClick();
        ProgressDialog pd = (ProgressDialog) ShadowProgressDialog.getLatestDialog();
        RegisterSuccess rs = new RegisterSuccess();
        RegisterResponse rp = new RegisterResponse();
        rp.setStatus(fragment.getString(R.string.error));
        rs.setResponse(rp);
        mBus.post(rs);
        assertThat(pd.isShowing(), equalTo(false));
        AlertDialog alert = ShadowAlertDialog.getLatestAlertDialog();
        assertThat(alert, notNullValue());
        alert.getButton(AlertDialog.BUTTON_POSITIVE).performClick();
        assertThat(alert.isShowing(), equalTo(false));
    }

    /**
     * Test the activity finishes on back button click
     */
    @Test
    public void shouldFinishOnOptionsMenu() {
        RoboMenuItem tmi2 = new RoboMenuItem(android.R.id.home);
        activity.onOptionsItemSelected(tmi2);
        assertThat(Shadows.shadowOf(activity).isFinishing(), equalTo(true));
    }
}