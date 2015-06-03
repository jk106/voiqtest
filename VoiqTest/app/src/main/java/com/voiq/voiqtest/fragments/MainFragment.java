package com.voiq.voiqtest.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.voiq.voiqtest.R;
import com.voiq.voiqtest.activities.RegisterActivity;
import com.voiq.voiqtest.activities.TokenActivity;
import com.voiq.voiqtest.app.VoiqTestApplication;
import com.voiq.voiqtest.eventbus.LogInRequest;
import com.voiq.voiqtest.eventbus.LogInSuccess;
import com.voiq.voiqtest.eventbus.NetworkError;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by juanchaparro on 30/05/15.
 * Main Fragment, for the main activity, implements the log in flow
 */
public class MainFragment extends Fragment {

    /**
     * E-mail field
     */
    @InjectView(R.id.txtMail)
    EditText txtMail;

    /**
     * Password Field
     */
    @InjectView(R.id.txtPass)
    EditText txtPass;

    /**
     * Log in button
     */
    @InjectView(R.id.btnLogin)
    Button btnLogin;

    /**
     * Apply Here with "link" look and feel
     */
    @InjectView(R.id.lblApply)
    TextView lblApply;

    /**
     * Event Bus instance, injected with Dagger
     */
    @Inject
    Bus mBus;

    /**
     * Progress Dialog to be shown while the request is being done.
     */
    private ProgressDialog pd;

    @Override
    public void onResume() {
        super.onResume();
        //Register on the EventBus
        mBus.register(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        //Unregister, or the fragment will keep on listening and acting up on events
        mBus.unregister(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inject ourselves on the object graph
        ((VoiqTestApplication)(getActivity().getApplication())).getObjectGraph().inject(this);
        if(savedInstanceState!=null && savedInstanceState.getBoolean("pd"))
            //If the savedInstanceState has the pd key, show a Progress Dialog
            pd= ProgressDialog.show(getActivity(), getString(R.string.logging_in),
                    getString(R.string.please_wait));
    }

    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        if(pd!=null&& pd.isShowing()) {
            //If a progress dialog is being shown, store it so it shows on fragment restart.
            pd.dismiss();
            outState.putBoolean("pd", true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    /**
     * If the log in button is clicked, start the request process
     */
    @OnClick(R.id.btnLogin)
    public void onLoginButtonClick()
    {
        String mail=txtMail.getText().toString();
        String pass=txtPass.getText().toString();
        /*
        Validate the e-mail. Non-blank, and regex.
         */
        if(mail.trim().equals(""))
        {
            Toast.makeText(getActivity(), getString(R.string.empty_email),
                    Toast.LENGTH_LONG).show();
            return;
        }
        else if(!mail.matches("[A-Z0-9a-z\\._%+-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}"))
        {
            Toast.makeText(getActivity(), getString(R.string.invalid_email),
                    Toast.LENGTH_LONG).show();
            return;
        }
        /*
        Password should not be empty
         */
        else if(pass.equals(""))
        {
            Toast.makeText(getActivity(), getResources().getText(R.string.empty_pass),
                    Toast.LENGTH_LONG).show();
            return;
        }
        /*
        Show the progress dialog, and post the event to start the request
         */
        LogInRequest lir= new LogInRequest(mail, pass);
        pd= ProgressDialog.show(getActivity(), getString(R.string.logging_in),
                getString(R.string.please_wait));
        mBus.post(lir);
    }

    /**
     * Listen to the Log In Success event
     * @param event success event
     */
    @Subscribe
    public void onLogInSuccess(LogInSuccess event)
    {
        //dismiss the dialog
        if(pd!=null && pd.isShowing())
            pd.dismiss();
        if(event.getResponse().getStatus().equalsIgnoreCase(getString(R.string.error)))
        {
            //Show an alert dialog with the error message
            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
            builder.setPositiveButton(getString(R.string.ok), null);
            builder.setMessage(event.getResponse().getText());
            builder.setTitle(event.getResponse().getStatus());
            builder.create().show();
        }
        else
        {
            //If everything went well, start the next activity
            Intent intent = new Intent(getActivity(), TokenActivity.class);
            intent.putExtra("token", event.getResponse().getToken());
            startActivity(intent);
        }
    }

    /**
     * Listen for network error event
     * @param event error event
     */
    @Subscribe
    public void onNetworkError(NetworkError event)
    {
        //dismiss the dialog
        if(pd!=null && pd.isShowing())
            pd.dismiss();
        //Show an alert dialog with an error message
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setPositiveButton(getString(R.string.ok), null);
        builder.setMessage(getString(R.string.network_error));
        builder.setTitle(getString(R.string.error));
        builder.create().show();
    }

    /**
     * If the TextView is clicked, start the register activity
     */
    @OnClick(R.id.lblApply)
    public void onApplyLabelClick()
    {
        startActivity(new Intent(getActivity(), RegisterActivity.class));
    }
}
