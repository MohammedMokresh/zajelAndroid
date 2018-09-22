package com.zajel.zajelandroid.APIManager;


import com.google.gson.JsonElement;
import com.zajel.zajelandroid.BuildConfig;
import com.zajel.zajelandroid.Login.LogInRequestBody;
import com.zajel.zajelandroid.SignUp.Models.SignUpRequestBody;
import com.zajel.zajelandroid.SignUp.Models.SignUpRespnseBody;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public class NetworkService {

    public final static String ACCEPT = "application/json";
    public final static String CONTENT_TYPE = "application/json";
    private static final String BASE_URL = BuildConfig.BASE_URL;
    private final static String TABLE_SIGN_UP = "auth";
    private final static String TABLE_LOG_IN = "auth/sign_in";
    private final static String TABLE_GOOGLE_LOG_IN = "auth/google_oauth2?auth_origin_url="+BuildConfig.BASE_URL;

    public ZajelNetworkAPI getAPI() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS);

        okHttpClient.addInterceptor(logging);  // <-- this is the important line!
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build())
                .build();
        return retrofit.create(ZajelNetworkAPI.class);
    }

    public interface ZajelNetworkAPI {

        //        @GET(TABLE_COURSES)
//        Call<CourseList> getCoursesFromSearch(@Body RealmCourse realmCourse);
//        @GET(TABLE_COURSES)
//        Call<CourseList> getCoursesFromSearch(@Query("city") String city, @Query("study_mode") String study_mode,
//                                              @Query("course_type") String course_type, @Query("degree") String degree,
//                                              @Query("university") String university);
//
        @POST(TABLE_SIGN_UP)
        Call<SignUpRespnseBody> signUp(@Body SignUpRequestBody signUpRequestBody);

        @POST(TABLE_LOG_IN)
        Call<SignUpRespnseBody> logIn(@Body LogInRequestBody logInRequestBody);

        @GET(TABLE_GOOGLE_LOG_IN)
        Call<JsonElement> googleLogIn();



    }



}

