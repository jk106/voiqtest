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
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, emulateSdk = 21)
public class RegisterFragmentTest {

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

    @Inject
    Bus mBus;
    private RegisterActivity activity;
    private RegisterFragment fragment;
    private ActivityController controller;


    @Module(injects = {RegisterFragmentTest.class, RegisterFragment.class, VoiqTestApplication.class},
            overrides = true,
            complete = false
    )
    public class TestModule {
        @Provides
        @Singleton
        Bus provideBus() {
            return Mockito.spy(new Bus(ThreadEnforcer.ANY));
        }
    }

    protected List<Object> getModules() {
        List<Object> result = new ArrayList<Object>();
        result.add(new ApiModule());
        result.add(new TestModule());
        return result;
    }

    @Before
    public void setUp() throws Exception {
        ObjectGraph og = ObjectGraph.create(getModules().toArray());
        og.inject(this);
        ((VoiqTestApplication) RuntimeEnvironment.application).setObjectGraph(og);
        controller = Robolectric.buildActivity(RegisterActivity.class).create().start();
        activity = (RegisterActivity) controller.resume().get();
        fragment = (RegisterFragment) activity.getSupportFragmentManager().findFragmentById(R.id.registerFragment);
        ButterKnife.inject(this, fragment.getView());
    }

    @Test
    public void shouldToastOnEmptyForm() throws Exception {
        btnRegister.performClick();
        assertThat(ShadowToast.getLatestToast(), notNullValue());
    }

    @Test
    public void shouldToastForEmptyFirstName() throws Exception {
        btnRegister.performClick();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo(fragment.getString(
                R.string.empty_fn, activity)));
    }

    @Test
    public void shouldToastForEmptyLastName() throws Exception {
        txtFirstName.setText("a");
        btnRegister.performClick();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo(fragment.getString(
                R.string.empty_ln, activity)));
    }

    @Test
    public void shouldToastForEmptyEmail() throws Exception {
        txtFirstName.setText("a");
        txtLastName.setText("b");
        btnRegister.performClick();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo(fragment.getString(
                R.string.empty_email, activity)));
    }

    @Test
    public void shouldToastForEmptyPhone() throws Exception {
        txtFirstName.setText("a");
        txtLastName.setText("b");
        txtEmail.setText("a@b.com");
        btnRegister.performClick();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo(fragment.getString(
                R.string.empty_phone, activity)));
    }

    @Test
    public void shouldToastForEmptyZipCode() throws Exception {
        txtFirstName.setText("a");
        txtLastName.setText("b");
        txtEmail.setText("a@b.com");
        txtPhone.setText("111");
        btnRegister.performClick();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo(fragment.getString(
                R.string.empty_zc, activity)));
    }

    @Test
    public void shouldToastForBirthDate() throws Exception {
        txtFirstName.setText("a");
        txtLastName.setText("b");
        txtEmail.setText("a@b.com");
        txtPhone.setText("111");
        txtZipCode.setText("10128");
        btnRegister.performClick();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo(fragment.getString(
                R.string.empty_bd, activity)));
    }

    @Test
    public void shouldToastForEmptyPassword() throws Exception {
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

    @Test
    public void shouldToastForEmptyConfirm() throws Exception {
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

    @Test
    public void shouldToastForPasswordMismatch() throws Exception {
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

    @Test
    public void shouldToastForInvalidEmail() throws Exception {
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

    @Test
    public void shouldCallLogInRequesOnFormSubmit() {
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

    @Test
    public void shouldShowDialogOnNetworkError() {
        NetworkError ne = new NetworkError();
        ne.setError(RetrofitError.unexpectedError("", new Exception()));
        mBus.post(ne);
        AlertDialog alert = ShadowAlertDialog.getLatestAlertDialog();
        assertThat(alert, notNullValue());
        alert.getButton(AlertDialog.BUTTON_POSITIVE).performClick();
        assertThat(alert.isShowing(), equalTo(false));
    }

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

    @Test
    public void shouldShowDialogOnLogInSuccessWithStatus() {
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

    @Test
    public void shouldFinishOnOptionsMenu() {
        RoboMenuItem tmi2 = new RoboMenuItem(android.R.id.home);
        activity.onOptionsItemSelected(tmi2);
        assertThat(Shadows.shadowOf(activity).isFinishing(), equalTo(true));
    }
}