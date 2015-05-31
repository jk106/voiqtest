package com.voiq.voiqtest.api;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.voiq.voiqtest.eventbus.LogInRequest;
import com.voiq.voiqtest.eventbus.LogInSuccess;
import com.voiq.voiqtest.eventbus.NetworkError;
import com.voiq.voiqtest.eventbus.RegisterRequest;
import com.voiq.voiqtest.eventbus.RegisterSuccess;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by juanchaparro on 31/05/15.
 */
public class ApiService {

    private VoiqEndpoints mEndpoints;
    private Bus mBus;

    public ApiService(VoiqEndpoints endpoints, Bus bus)
    {
        mEndpoints=endpoints;
        mBus=bus;
    }

    @Subscribe
    public void onLogInRequest(LogInRequest event)
    {
        mEndpoints.LogIn(event.getEmail(), event.getPassword(), new Callback<LogInResponse>() {
            @Override
            public void success(LogInResponse logInResponse, Response response) {
                LogInSuccess lis= new LogInSuccess();
                lis.setResponse(logInResponse);
                mBus.post(lis);
            }

            @Override
            public void failure(RetrofitError error) {
                NetworkError ne= new NetworkError();
                ne.setError(error);
                mBus.post(ne);
            }
        });
    }

    @Subscribe
    public void onRegisterRequest(RegisterRequest event)
    {
        mEndpoints.CreateUser(event.getUsrPicName(), event.getUserId(), event.getCampaignId(),
                event.getUndercover(), event.getFirstName(), event.getLastName(),
                event.getEmail(), event.getBirth(), event.getZipCode(), event.getPhone(),
                event.getAndroidDevice(), event.getPassword1(), event.getPassword2(),
                new Callback<RegisterResponse>() {
                    @Override
                    public void success(RegisterResponse registerResponse, Response response) {
                        RegisterSuccess rs=new RegisterSuccess();
                        rs.setResponse(registerResponse);
                        mBus.post(rs);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        NetworkError ne= new NetworkError();
                        ne.setError(error);
                        mBus.post(ne);
                    }
                });
    }
}
