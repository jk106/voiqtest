package com.voiq.voiqtest.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.voiq.voiqtest.R;

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
