package com.voiq.voiqtest.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;
import com.voiq.voiqtest.BuildConfig;
import com.voiq.voiqtest.R;
import com.voiq.voiqtest.activities.MainActivity;
import com.voiq.voiqtest.activities.TokenActivity;
import com.voiq.voiqtest.api.LogInResponse;
import com.voiq.voiqtest.app.VoiqTestApplication;
import com.voiq.voiqtest.eventbus.LogInRequest;
import com.voiq.voiqtest.eventbus.LogInSuccess;
import com.voiq.voiqtest.eventbus.NetworkError;
import com.voiq.voiqtest.modules.ApiModule;
import com.voiq.voiqtest.util.TestUtilities;

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
public class MainFragmentTest {

    @InjectView(R.id.txtMail)
    EditText txtMail;

    @InjectView(R.id.txtPass)
    EditText txtPass;

    @InjectView(R.id.btnLogin)
    Button btnLogin;

    @Inject
    Bus mBus;
    private MainActivity activity;
    private MainFragment fragment;


    @Module(injects = {MainFragmentTest.class, MainFragment.class, VoiqTestApplication.class},
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
        activity = Robolectric.buildActivity(MainActivity.class).create().start().resume().get();
        fragment = (MainFragment) activity.getSupportFragmentManager().findFragmentById(R.id.mainFragment);
        ButterKnife.inject(this, fragment.getView());
    }

    @Test
    public void shouldToastOnEmptyForm() throws Exception {
        btnLogin.performClick();
        assertThat(ShadowToast.getLatestToast(), notNullValue());
    }

    @Test
    public void shouldToastForEmptyEmail() throws Exception {
        btnLogin.performClick();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo(TestUtilities.getStringById(
                R.string.empty_email, activity)));
    }

    @Test
    public void shouldToastOnInvalidEmail() throws Exception {
        txtMail.setText("aaaa");
        btnLogin.performClick();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo(TestUtilities.getStringById(
                R.string.invalid_email, activity)));
    }

    @Test
    public void shouldToastOnEmptyPassword() throws Exception {
        txtMail.setText("a@b.com");
        btnLogin.performClick();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo(TestUtilities.getStringById(
                R.string.empty_pass, activity)));
    }

    @Test
    public void shouldShowProgressDialogOnSubmit() {
        txtMail.setText("aa@aa.com");
        txtPass.setText("1111");
        btnLogin.performClick();
        ProgressDialog pd = (ProgressDialog) ShadowProgressDialog.getLatestDialog();
        assertThat(pd, notNullValue());
    }

    @Test
    public void shouldShowProgressDialogOnRestoreInstanceStateAfterSubmit() {
        txtMail.setText("aa@aa.com");
        txtPass.setText("1111");
        btnLogin.performClick();
        activity.recreate();
        fragment = (MainFragment) activity.getSupportFragmentManager().findFragmentById(R.id.mainFragment);
        ProgressDialog pd = (ProgressDialog) ShadowProgressDialog.getLatestDialog();
        assertThat(pd, notNullValue());
    }

    @Test
    public void shouldCallLogInRequesOnFormSubmit() {
        txtMail.setText("aa@aa.com");
        txtPass.setText("1111");
        btnLogin.performClick();
        Mockito.verify(mBus, Mockito.times(1)).post(Mockito.isA(LogInRequest.class));
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
    public void shouldStartTokenActivityOnLoginSuccessWithToken() {
        txtMail.setText("aa@aa.com");
        txtPass.setText("1111");
        btnLogin.performClick();
        ProgressDialog pd = (ProgressDialog) ShadowProgressDialog.getLatestDialog();
        LogInSuccess lis = new LogInSuccess();
        LogInResponse lir = new LogInResponse();
        lir.setToken("aaa");
        lis.setResponse(lir);
        mBus.post(lis);
        assertThat(pd.isShowing(), equalTo(false));
        Intent intent = Shadows.shadowOf(activity).peekNextStartedActivity();
        assertThat(TokenActivity.class.getCanonicalName(), equalTo(intent.getComponent().getClassName()));
    }

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
}