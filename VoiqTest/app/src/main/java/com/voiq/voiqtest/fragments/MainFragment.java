package com.voiq.voiqtest.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
 */
public class MainFragment extends Fragment {

    @InjectView(R.id.txtMail)
    EditText txtMail;

    @InjectView(R.id.txtPass)
    EditText txtPass;

    @InjectView(R.id.btnLogin)
    Button btnLogin;

    @InjectView(R.id.lblApply)
    TextView lblApply;

    @Inject
    Bus mBus;

    private ProgressDialog pd;

    @Override
    public void onResume() {
        super.onResume();
        mBus.register(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        mBus.unregister(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((VoiqTestApplication)(getActivity().getApplication())).getObjectGraph().inject(this);
        if(savedInstanceState!=null && savedInstanceState.getBoolean("pd"))
            pd= ProgressDialog.show(getActivity(), getString(R.string.logging_in),
                    getString(R.string.please_wait));
    }

    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        if(pd!=null&& pd.isShowing()) {
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

    @OnClick(R.id.btnLogin)
    public void onLoginButtonClick()
    {
        String mail=txtMail.getText().toString();
        String pass=txtPass.getText().toString();
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
        else if(pass.equals(""))
        {
            Toast.makeText(getActivity(), getResources().getText(R.string.empty_pass),
                    Toast.LENGTH_LONG).show();
            return;
        }
        LogInRequest lir= new LogInRequest(mail, pass);
        pd= ProgressDialog.show(getActivity(), getString(R.string.logging_in),
                getString(R.string.please_wait));
        mBus.post(lir);
    }

    @Subscribe
    public void onLogInSuccess(LogInSuccess event)
    {
        if(pd!=null && pd.isShowing())
            pd.dismiss();
        if(event.getResponse().getStatus().equalsIgnoreCase(getString(R.string.error)))
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
            builder.setPositiveButton(getString(R.string.ok), null);
            builder.setMessage(event.getResponse().getText());
            builder.setTitle(event.getResponse().getStatus());
            builder.create().show();
        }
        else
        {
            Intent intent = new Intent(getActivity(), TokenActivity.class);
            intent.putExtra("token", event.getResponse().getToken());
            startActivity(intent);
        }
    }

    @Subscribe
    public void onNetworkError(NetworkError event)
    {
        if(pd!=null && pd.isShowing())
            pd.dismiss();
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setPositiveButton(getString(R.string.ok), null);
        builder.setMessage(getString(R.string.network_error));
        builder.setTitle(getString(R.string.error));
        builder.create().show();
    }

    @OnClick(R.id.lblApply)
    public void onApplyLabelClick()
    {
        startActivity(new Intent(getActivity(), RegisterActivity.class));
    }
}
