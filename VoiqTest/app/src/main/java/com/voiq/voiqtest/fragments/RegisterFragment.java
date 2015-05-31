package com.voiq.voiqtest.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.voiq.voiqtest.R;
import com.voiq.voiqtest.app.VoiqTestApplication;
import com.voiq.voiqtest.eventbus.NetworkError;
import com.voiq.voiqtest.eventbus.RegisterRequest;
import com.voiq.voiqtest.eventbus.RegisterSuccess;

import java.util.Calendar;
import java.util.TimeZone;

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

    @InjectView(R.id.btnRegister)
    Button btnRegister;

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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @OnClick(R.id.btnSelectDate)
    public void onSelectDateClick()
    {
        try{
            Calendar cal = Calendar.getInstance(TimeZone.getDefault());
            DatePickerDialog datePicker = new DatePickerDialog(getActivity(),
                    R.style.AppTheme, datePickerListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH));
            datePicker.setCancelable(false);
            datePicker.setTitle(getString(R.string.select_date));

            datePicker.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @OnClick(R.id.btnRegister)
    public void onRegisterClick()
    {
        String fn=txtFirstName.getText().toString();
        String ln=txtLastName.getText().toString();
        String mail=txtEmail.getText().toString();
        String phone=txtPhone.getText().toString();
        String zc=txtZipCode.getText().toString();
        String bd=btnSelectDate.getText().toString();
        String pass=txtPassword.getText().toString();
        String confirm=txtPasswordConfirmation.getText().toString();
        if(fn.trim().equals(""))
        {
            Toast.makeText(getActivity(),getString(R.string.empty_fn), Toast.LENGTH_LONG).show();
            return;
        }
        else if(ln.trim().equals(""))
        {
            Toast.makeText(getActivity(),getString(R.string.empty_ln), Toast.LENGTH_LONG).show();
            return;
        }
        else if(mail.trim().equals(""))
        {
            Toast.makeText(getActivity(),getString(R.string.empty_email), Toast.LENGTH_LONG).show();
            return;
        }
        else if(phone.trim().equals(""))
        {
            Toast.makeText(getActivity(),getString(R.string.empty_phone), Toast.LENGTH_LONG).show();
            return;
        }
        else if(zc.trim().equals(""))
        {
            Toast.makeText(getActivity(),getString(R.string.empty_zc), Toast.LENGTH_LONG).show();
            return;
        }
        else if(bd.trim().equals(getString(R.string.select_date)))
        {
            Toast.makeText(getActivity(),getString(R.string.empty_bd), Toast.LENGTH_LONG).show();
            return;
        }
        else if(pass.trim().equals(""))
        {
            Toast.makeText(getActivity(),getString(R.string.empty_pass), Toast.LENGTH_LONG).show();
            return;
        }
        else if(confirm.trim().equals(""))
        {
            Toast.makeText(getActivity(),getString(R.string.empty_confirm), Toast.LENGTH_LONG).show();
            return;
        }
        else if(!pass.equals(confirm))
        {
            Toast.makeText(getActivity(),getString(R.string.password_mismatch), Toast.LENGTH_LONG).show();
            return;
        }
        else if(!mail.matches("[A-Z0-9a-z\\._%+-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}"))
        {
            Toast.makeText(getActivity(), getString(R.string.invalid_email),
                    Toast.LENGTH_LONG).show();
            return;
        }
        RegisterRequest rr= new RegisterRequest("","",0,"",fn,ln,mail,bd,zc,phone,1,pass,confirm);
        pd= ProgressDialog.show(getActivity(), getString(R.string.logging_in),
                getString(R.string.please_wait));
        mBus.post(rr);
    }

    @Subscribe
    public void onRegisterSuccess(RegisterSuccess event)
    {
        if(pd!=null && pd.isShowing())
            pd.dismiss();
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        builder.setMessage(event.getResponse().getText());
        builder.setTitle(event.getResponse().getStatus());
        if(event.getResponse().getStatus().equalsIgnoreCase(getString(R.string.error)))
            builder.setPositiveButton(getString(R.string.ok), null);
        else
            builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    getActivity().finish();
                }
            });
        builder.create().show();
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

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            String year1 = String.valueOf(selectedYear);
            String month1 = String.valueOf(selectedMonth + 1);
            String day1 = String.valueOf(selectedDay);
            if(selectedDay<10)
                day1="0"+day1;
            if(selectedMonth<9)
                month1="0"+month1;
            btnSelectDate.setText(year1 + "-" + month1 + "-" + day1);
        }
    };
}
