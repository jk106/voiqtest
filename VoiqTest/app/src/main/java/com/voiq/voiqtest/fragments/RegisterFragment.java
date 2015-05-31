package com.voiq.voiqtest.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.otto.Bus;
import com.voiq.voiqtest.R;
import com.voiq.voiqtest.app.VoiqTestApplication;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class RegisterFragment extends Fragment {

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

    @Inject
    Bus mBus;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @OnClick(R.id.btnSelectDate)
    public void onSelectDateClick()
    {

    }
    @OnClick(R.id.btnRegister)
    public void onRegisterClick()
    {

    }

}
