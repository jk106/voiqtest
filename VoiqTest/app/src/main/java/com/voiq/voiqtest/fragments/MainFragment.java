package com.voiq.voiqtest.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.squareup.otto.Bus;
import com.voiq.voiqtest.R;
import com.voiq.voiqtest.app.VoiqTestApplication;

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
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this,rootView);
        return rootView;
    }

    @OnClick(R.id.btnLogin)
    public void onLoginButtonClick()
    {

    }

    @OnClick(R.id.lblApply)
    public void onApplyLabelClick()
    {

    }
}
