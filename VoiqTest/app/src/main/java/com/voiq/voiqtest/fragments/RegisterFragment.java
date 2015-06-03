package com.voiq.voiqtest.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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

/**
 * Register Fragment, implementing the register flow
 */
public class RegisterFragment extends Fragment {

    /**
     * First Name field
     */
    @InjectView(R.id.txtFirstName)
    EditText txtFirstName;

    /**
     * Last Name field
     */
    @InjectView(R.id.txtLastName)
    EditText txtLastName;

    /**
     * E-mail field
     */
    @InjectView(R.id.txtEmail)
    EditText txtEmail;

    /**
     * Zip Code field
     */
    @InjectView(R.id.txtZipCode)
    EditText txtZipCode;

    /**
     * Phone number field
     */
    @InjectView(R.id.txtPhone)
    EditText txtPhone;

    /**
     * Password field
     */
    @InjectView(R.id.txtPassword)
    EditText txtPassword;

    /**
     * Password Confirmation field
     */
    @InjectView(R.id.txtPasswordConfirmation)
    EditText txtPasswordConfirmation;

    /**
     * Date Selection button
     */
    @InjectView(R.id.btnSelectDate)
    Button btnSelectDate;

    /**
     * Register Button
     */
    @InjectView(R.id.btnRegister)
    Button btnRegister;

    /**
     * Event Bus instance, injected by Dagger
     */
    @Inject
    Bus mBus;

    /**
     * Progress Dialog to be shown while the request is being done.
     */private ProgressDialog pd;

    @Override
    public void onResume() {
        super.onResume();
        //Register on the event bus
        mBus.register(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        //Unregister from the event bus
        mBus.unregister(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Inject ourselves on the object graph
        ((VoiqTestApplication)(getActivity().getApplication())).getObjectGraph().inject(this);
        if(savedInstanceState!=null && savedInstanceState.getBoolean("pd"))
            //If the savedInstanceState includes the pd key, show a Progress Dialog
            pd= ProgressDialog.show(getActivity(), getString(R.string.logging_in),
                    getString(R.string.please_wait));
    }

    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        if(pd!=null&& pd.isShowing()) {
            //If there is a progress dialog showing, store it so it shows again on fragment restart
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

    /**
     * If the select date button is clicked, show the corresponding dialog
     */
    @OnClick(R.id.btnSelectDate)
    public void onSelectDateClick()
    {
        try{
            /*
            Create a DatePickerDialog for the birth date. Use the fragment's datePickerListener
             */
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

    /**
     * If the register button is clicked, start the request process
     */
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
        //Validate First Name is not empty
        if(fn.trim().equals(""))
        {
            Toast.makeText(getActivity(),getString(R.string.empty_fn), Toast.LENGTH_LONG).show();
            return;
        }
        //Validate last name is not empty
        else if(ln.trim().equals(""))
        {
            Toast.makeText(getActivity(),getString(R.string.empty_ln), Toast.LENGTH_LONG).show();
            return;
        }
        //Validate e-mail is not empty
        else if(mail.trim().equals(""))
        {
            Toast.makeText(getActivity(),getString(R.string.empty_email), Toast.LENGTH_LONG).show();
            return;
        }
        //Validate phone number is not empty
        else if(phone.trim().equals(""))
        {
            Toast.makeText(getActivity(),getString(R.string.empty_phone), Toast.LENGTH_LONG).show();
            return;
        }
        //Validate zip code is not empty
        else if(zc.trim().equals(""))
        {
            Toast.makeText(getActivity(),getString(R.string.empty_zc), Toast.LENGTH_LONG).show();
            return;
        }
        //Validate Birth Date is not empty
        else if(bd.trim().equals(getString(R.string.select_date)))
        {
            Toast.makeText(getActivity(),getString(R.string.empty_bd), Toast.LENGTH_LONG).show();
            return;
        }
        //Validate Password is not empty
        else if(pass.trim().equals(""))
        {
            Toast.makeText(getActivity(),getString(R.string.empty_pass), Toast.LENGTH_LONG).show();
            return;
        }
        //Validate Password confirmation is not empty
        else if(confirm.trim().equals(""))
        {
            Toast.makeText(getActivity(),getString(R.string.empty_confirm), Toast.LENGTH_LONG).show();
            return;
        }
        //Validate passwords match
        else if(!pass.equals(confirm))
        {
            Toast.makeText(getActivity(),getString(R.string.password_mismatch), Toast.LENGTH_LONG).show();
            return;
        }
        //Validate email is regex-valid
        else if(!mail.matches("[A-Z0-9a-z\\._%+-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}"))
        {
            Toast.makeText(getActivity(), getString(R.string.invalid_email),
                    Toast.LENGTH_LONG).show();
            return;
        }
        //Show Progress Dialog and Post the event to make the request
        RegisterRequest rr= new RegisterRequest("","",0,"",fn,ln,mail,bd,zc,phone,1,pass,confirm);
        pd= ProgressDialog.show(getActivity(), getString(R.string.logging_in),
                getString(R.string.please_wait));
        mBus.post(rr);
    }

    /**
     * Listen to the register success event
     * @param event success event
     */
    @Subscribe
    public void onRegisterSuccess(RegisterSuccess event)
    {
        //dismiss the dialog
        if(pd!=null && pd.isShowing())
            pd.dismiss();
        //show an alert dialog with the results of the request
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setMessage(event.getResponse().getText());
        builder.setTitle(event.getResponse().getStatus());
        if(event.getResponse().getStatus().equalsIgnoreCase(getString(R.string.error)))
            //If there was an error, just dismiss the dialog on click
            builder.setPositiveButton(getString(R.string.ok), null);
        else
        //If it was ok, dismiss the activity on click
            builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    getActivity().finish();
                }
            });
        builder.create().show();
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
     * DateSetListener for the DatePickerDialog
     */
    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            //Parse the selected date, in the correct format
            String year1 = String.valueOf(selectedYear);
            String month1 = String.valueOf(selectedMonth + 1);
            String day1 = String.valueOf(selectedDay);
            //Complete the day and month if required
            if(selectedDay<10)
                day1="0"+day1;
            if(selectedMonth<9)
                month1="0"+month1;
            btnSelectDate.setText(year1 + "-" + month1 + "-" + day1);
        }
    };
}
