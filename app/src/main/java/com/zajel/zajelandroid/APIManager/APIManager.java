package com.zajel.zajelandroid.APIManager;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Log;

import com.google.gson.JsonElement;
import com.zajel.zajelandroid.Home.BooksModels.Books;
import com.zajel.zajelandroid.Login.LogInRequestBody;
import com.zajel.zajelandroid.SignUp.Models.SignUpRequestBody;
import com.zajel.zajelandroid.SignUp.Models.SignUpRespnseBody;

import java.net.HttpURLConnection;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class APIManager {

    private final NetworkService networkService;
    private final Context context;


    public APIManager(Context context) {
        this.networkService = new NetworkService();
        this.context = context;
    }


    /**
     *
     *
     * Sign up Call
     *
     *
     */
    private SignUpResponse signUpResponse;

    public void setSignUpResponse(SignUpResponse signUpResponse) {
        this.signUpResponse = signUpResponse;
    }
    public interface SignUpResponse {
        void getSignUpResponse(SignUpRespnseBody signUpRespnseBody);
        void  getSignUpHeaders(Headers headers);
        void errorOccureSignUp();
    }

    public void signUp(SignUpRequestBody signUpRequestBody) {
        networkService.getAPI().signUp(signUpRequestBody).enqueue(new Callback<SignUpRespnseBody>() {
            @Override
            public void onResponse(@NonNull Call<SignUpRespnseBody> call, @NonNull Response<SignUpRespnseBody> response) {
                if (response.body() != null && response.code() == HttpURLConnection.HTTP_OK) {

                    SignUpRespnseBody signUpRespnseBody = response.body();
                    Headers headers =  response.headers();
                    signUpResponse.getSignUpHeaders(headers);
                    signUpResponse.getSignUpResponse(signUpRespnseBody);

                } else {
                    Log.e("error","error");
                    signUpResponse.errorOccureSignUp();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SignUpRespnseBody> call, @NonNull Throwable t) {
                try {
                    signUpResponse.errorOccureSignUp();
                    Log.e("error",t.getMessage());
                    throw new InterruptedException("Error occurred due to network problem");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }




    /**
     *
     *
     * Log in Call
     *
     *
     */
    private LogInResponse logInResponse;

    public void setLoginResponse(LogInResponse loginResponse) {
        this.logInResponse = loginResponse;
    }
    public interface LogInResponse {
        void getLoginResponse(SignUpRespnseBody signUpRespnseBody);
        void  getLoginHeaders(Headers headers);
        void errorOccureLogin();
    }

    public void logIn(LogInRequestBody logInRequestBody) {
        networkService.getAPI().logIn(logInRequestBody).enqueue(new Callback<SignUpRespnseBody>() {
            @Override
            public void onResponse(@NonNull Call<SignUpRespnseBody> call, @NonNull Response<SignUpRespnseBody> response) {
                if (response.body() != null && response.code() == HttpURLConnection.HTTP_OK) {

                    SignUpRespnseBody signUpRespnseBody = response.body();
                    Headers headers =  response.headers();
                    logInResponse.getLoginHeaders(headers);
                    logInResponse.getLoginResponse(signUpRespnseBody);

                } else {
                    logInResponse.errorOccureLogin();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SignUpRespnseBody> call, @NonNull Throwable t) {
                try {
                    logInResponse.errorOccureLogin();
                    throw new InterruptedException("Error occurred due to network problem");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }





    /**
     *
     *
     * google log in
     *
     *
     */
    private GoogleLogInResponse googleLogInResponse;

    public void setGoogleLogInResponse(GoogleLogInResponse googleLogInResponse) {
        this.googleLogInResponse = googleLogInResponse;
    }
    public interface GoogleLogInResponse {
        void getLoginResponse(JsonElement jsonElement);

        void errorOccureLogin();
    }

    public void googleLogIn() {
        networkService.getAPI().googleLogIn().enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(@NonNull Call<JsonElement> call, @NonNull Response<JsonElement> response) {
                if (response.body() != null && response.code() == HttpURLConnection.HTTP_OK) {

                    JsonElement jsonElement = response.body();
                    Headers headers =  response.headers();

                    googleLogInResponse.getLoginResponse(jsonElement);

                } else {
                    googleLogInResponse.errorOccureLogin();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonElement> call, @NonNull Throwable t) {
                try {
                    Log.e("errr", (t.getMessage()));
                    googleLogInResponse.errorOccureLogin();
                    throw new InterruptedException("Error occurred due to network problem");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }



}


