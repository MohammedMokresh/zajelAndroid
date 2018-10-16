package com.zajel.zajelandroid.APIManager;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Log;

import com.zajel.zajelandroid.Book.AddBookModels.AddBookRequestModel;
import com.zajel.zajelandroid.Book.AddBookModels.AddBookResponseModel;
import com.zajel.zajelandroid.Login.GoogleSignInModels.GoogleUser;
import com.zajel.zajelandroid.Login.LogInRequestBody;
import com.zajel.zajelandroid.PreferenceManager;
import com.zajel.zajelandroid.Requests.RequestsModels.Requests;
import com.zajel.zajelandroid.SignUp.Models.SignUpRequestBody;
import com.zajel.zajelandroid.SignUp.Models.SignUpRespnseBody;
import com.zajel.zajelandroid.Wishlist.WishlistModels.WishListResponseBody;

import java.net.HttpURLConnection;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class APIManager {

    private final NetworkService networkService;
    private final Context context;
    PreferenceManager preferenceManager;

    public APIManager(Context context) {
        preferenceManager= PreferenceManager.getInstance();
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
        networkService.getAPI().signUp(NetworkService.CONTENT_TYPE,NetworkService.ACCEPT,signUpRequestBody).enqueue(new Callback<SignUpRespnseBody>() {
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
        networkService.getAPI().logIn(NetworkService.CONTENT_TYPE,NetworkService.ACCEPT,logInRequestBody).enqueue(new Callback<SignUpRespnseBody>() {
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
        void getGoogleLoginResponse(SignUpRespnseBody jsonElement);
        void  getGoogleLoginHeaders(Headers headers);
        void errorOccureGoogleLogin();
    }

    public void googleLogIn(GoogleUser googleUser) {
        networkService.getAPI().googleLogIn(NetworkService.CONTENT_TYPE,NetworkService.ACCEPT,googleUser).enqueue(new Callback<SignUpRespnseBody>() {
            @Override
            public void onResponse(@NonNull Call<SignUpRespnseBody> call, @NonNull Response<SignUpRespnseBody> response) {
                if (response.body() != null && response.code() == HttpURLConnection.HTTP_OK) {

                    SignUpRespnseBody jsonElement = response.body();
                    Headers headers =  response.headers();
                    googleLogInResponse.getGoogleLoginHeaders(headers);
                    googleLogInResponse.getGoogleLoginResponse(jsonElement);

                } else {
                    googleLogInResponse.errorOccureGoogleLogin();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SignUpRespnseBody> call, @NonNull Throwable t) {
                try {
                    googleLogInResponse.errorOccureGoogleLogin();
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
     * add book
     *
     *
     */
    private AddBookResponse addBookResponse;

    public void setAddBookResponse(AddBookResponse addBookResponse) {
        this.addBookResponse = addBookResponse;
    }
    public interface AddBookResponse {
        void addBookResponse(AddBookResponseModel addBookResponseModel);
        void errorOccureAddBook();
    }

    public void addBook(final AddBookRequestModel addBookRequestModel) {
        networkService.getAPI().addBook(preferenceManager.getAccessToken(),preferenceManager.getClient()
                ,preferenceManager.getExpiry(),preferenceManager.getUid(),preferenceManager.getTokenType(),NetworkService.CONTENT_TYPE,NetworkService.ACCEPT,addBookRequestModel).enqueue(new Callback<AddBookResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AddBookResponseModel> call, @NonNull Response<AddBookResponseModel> response) {
                if (response.body() != null && response.code() == HttpURLConnection.HTTP_OK) {

                    AddBookResponseModel addBookResponseModel = response.body();
                    addBookResponse.addBookResponse(addBookResponseModel);

                } else {
                    addBookResponse.errorOccureAddBook();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AddBookResponseModel> call, @NonNull Throwable t) {
                try {
                    addBookResponse.errorOccureAddBook();
                    throw new InterruptedException("Error occurred due to network problem");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void updateBook(final AddBookRequestModel addBookRequestModel,Integer id) {
        networkService.getAPI().updateBook(preferenceManager.getAccessToken(),preferenceManager.getClient()
                ,preferenceManager.getExpiry(),preferenceManager.getUid(),preferenceManager.getTokenType(),NetworkService.CONTENT_TYPE,NetworkService.ACCEPT,addBookRequestModel,id).enqueue(new Callback<AddBookResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AddBookResponseModel> call, @NonNull Response<AddBookResponseModel> response) {
                if (response.body() != null && response.code() == HttpURLConnection.HTTP_OK) {

                    AddBookResponseModel addBookResponseModel = response.body();
                    addBookResponse.addBookResponse(addBookResponseModel);

                } else {
                    addBookResponse.errorOccureAddBook();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AddBookResponseModel> call, @NonNull Throwable t) {
                try {
                    addBookResponse.errorOccureAddBook();
                    throw new InterruptedException("Error occurred due to network problem");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getBook(Integer id) {
        networkService.getAPI().getBook(preferenceManager.getAccessToken(),preferenceManager.getClient()
                ,preferenceManager.getExpiry(),preferenceManager.getUid(),preferenceManager.getTokenType(),NetworkService.CONTENT_TYPE,NetworkService.ACCEPT,id).enqueue(new Callback<AddBookResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<AddBookResponseModel> call, @NonNull Response<AddBookResponseModel> response) {
                if (response.body() != null && response.code() == HttpURLConnection.HTTP_OK) {

                    AddBookResponseModel addBookResponseModel = response.body();
                    addBookResponse.addBookResponse(addBookResponseModel);

                } else {
                    addBookResponse.errorOccureAddBook();
                }
            }

            @Override
            public void onFailure(@NonNull Call<AddBookResponseModel> call, @NonNull Throwable t) {
                try {
                    addBookResponse.errorOccureAddBook();
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
     * requests
     *
     *
     */
    private RequestsResponse requestsResponse;

    public void setRequestsResponse(RequestsResponse requestsResponse) {
        this.requestsResponse = requestsResponse;
    }
    public interface RequestsResponse {
        void getRequestResponse(Requests requests);
        void errorOccureRequest();
    }

    public void getSendRequest(Integer  borrowerId) {
        networkService.getAPI().getSentRequests(preferenceManager.getAccessToken(),preferenceManager.getClient()
                ,preferenceManager.getExpiry(),preferenceManager.getUid(),preferenceManager.getTokenType(),NetworkService.CONTENT_TYPE,NetworkService.ACCEPT,borrowerId).enqueue(new Callback<Requests>() {
            @Override
            public void onResponse(@NonNull Call<Requests> call, @NonNull Response<Requests> response) {
                if (response.body() != null && response.code() == HttpURLConnection.HTTP_OK) {

                    Requests requests = response.body();

                    requestsResponse.getRequestResponse(requests);

                } else {
                    requestsResponse.errorOccureRequest();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Requests> call, @NonNull Throwable t) {
                try {
                    requestsResponse.errorOccureRequest();
                    throw new InterruptedException("Error occurred due to network problem");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void getReceiveRequest(Integer  lenderId) {
        networkService.getAPI().getReceivedRequests(preferenceManager.getAccessToken(),preferenceManager.getClient()
                ,preferenceManager.getExpiry(),preferenceManager.getUid(),preferenceManager.getTokenType(),NetworkService.CONTENT_TYPE,NetworkService.ACCEPT,lenderId).enqueue(new Callback<Requests>() {
            @Override
            public void onResponse(@NonNull Call<Requests> call, @NonNull Response<Requests> response) {
                if (response.body() != null && response.code() == HttpURLConnection.HTTP_OK) {

                    Requests requests = response.body();

                    requestsResponse.getRequestResponse(requests);

                } else {
                    requestsResponse.errorOccureRequest();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Requests> call, @NonNull Throwable t) {
                try {
                    requestsResponse.errorOccureRequest();
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
     * WISHLIST
     *
     *
     */
    private WishlistResponse wishlistResponse;

    public void setWishlistResponse(WishlistResponse wishlistResponse) {
        this.wishlistResponse = wishlistResponse;
    }
    public interface WishlistResponse {
        void getWishlist(WishListResponseBody wishListResponseBody);
        void errorOccureWishlist();
    }

    public void getWishlist(Integer  userId) {
        networkService.getAPI().getWishlist(preferenceManager.getAccessToken(),preferenceManager.getClient()
                ,preferenceManager.getExpiry(),preferenceManager.getUid(),preferenceManager.getTokenType(),NetworkService.CONTENT_TYPE,NetworkService.ACCEPT,userId).enqueue(new Callback<WishListResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<WishListResponseBody> call, @NonNull Response<WishListResponseBody> response) {
                if (response.body() != null && response.code() == HttpURLConnection.HTTP_OK) {

                    WishListResponseBody requests = response.body();

                    wishlistResponse.getWishlist(requests);

                } else {
                    wishlistResponse.errorOccureWishlist();
                }
            }

            @Override
            public void onFailure(@NonNull Call<WishListResponseBody> call, @NonNull Throwable t) {
                try {
                    wishlistResponse.errorOccureWishlist();
                    throw new InterruptedException("Error occurred due to network problem");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


}


