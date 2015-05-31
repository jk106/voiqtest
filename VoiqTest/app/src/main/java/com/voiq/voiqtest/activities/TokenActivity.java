package com.voiq.voiqtest.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.voiq.voiqtest.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class TokenActivity extends AppCompatActivity {

    @InjectView(R.id.txtToken)
    EditText txtToken;

    @InjectView(R.id.btnLogout)
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);
        ButterKnife.inject(this);
        try {
            txtToken.setText(getIntent().getStringExtra("token"));
        }
        catch(Exception e)
        {
            Log.e(getString(R.string.title_activity_token), e.getMessage());
        }
        ButterKnife.inject(this);
    }

    @OnClick(R.id.btnLogout)
    public void onLogoutClick()
    {
        finish();
    }
}
