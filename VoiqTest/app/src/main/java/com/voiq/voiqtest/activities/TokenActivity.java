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

/**
 * Token Activity, which shows the token
 */
public class TokenActivity extends AppCompatActivity {

    /**
     * Token EditText field, which is disabled
     */
    @InjectView(R.id.txtToken)
    EditText txtToken;

    /**
     * Log out button
     */
    @InjectView(R.id.btnLogout)
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);
        //Use ButterKnife, or the annotations won't cut it
        ButterKnife.inject(this);
        try {
            txtToken.setText(getIntent().getStringExtra("token"));
        }
        catch(Exception e)
        {
            //Just in case there is no token, though this should rarely happen
            Log.e(getString(R.string.title_activity_token), e.getMessage());
        }
    }

    @OnClick(R.id.btnLogout)
    public void onLogoutClick()
    {
        // Dismiss the activity to "log out"
        finish();
    }
}
