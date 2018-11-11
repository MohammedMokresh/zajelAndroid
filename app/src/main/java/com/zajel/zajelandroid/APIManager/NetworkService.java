package com.zajel.zajelandroid.APIManager;

import com.google.gson.JsonElement;
import com.zajel.zajelandroid.Book.AddBookModels.AddBookRequestModel;
import com.zajel.zajelandroid.Book.AddBookModels.AddBookResponseModel;
import com.zajel.zajelandroid.BookList.AddToWishList.AddToWishlistRequestBody;
import com.zajel.zajelandroid.BookList.Borrow.BorrowAndCancelBookResponseBody.BorrowBookResponseBody;
import com.zajel.zajelandroid.BookList.Borrow.BorrowBookRequestBody.BorrowBookRequestBody;
import com.zajel.zajelandroid.BookList.GenresModels.GenresList;
import com.zajel.zajelandroid.BuildConfig;
import com.zajel.zajelandroid.BookList.BooksModels.Books;
import com.zajel.zajelandroid.Login.GoogleSignInModels.GoogleUser;
import com.zajel.zajelandroid.Login.LogInRequestBody;
import com.zajel.zajelandroid.Profile.User.UpdateFirebaseTokenRequestBody;
import com.zajel.zajelandroid.Requests.AcceptRejectRequestModel.AcceptRejectRequestRequestBody;
import com.zajel.zajelandroid.Requests.RequestsModels.Requests;
import com.zajel.zajelandroid.SignUp.Models.SignUpRequestBody;
import com.zajel.zajelandroid.SignUp.Models.SignUpRespnseBody;
import com.zajel.zajelandroid.Wishlist.WishlistModels.WishListResponseBody;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public class NetworkService {

    public final static String ACCEPT = "application/json";
    public final static String CONTENT_TYPE = "application/json";
    private static final String BASE_URL = BuildConfig.BASE_URL;
    private final static String TABLE_SIGN_UP = "auth";
    private final static String TABLE_LOG_IN = "auth/sign_in";
    private final static String TABLE_BOOKS = "books";
    private final static String TABLE_GOOGLE_LOG_IN = "users/google_sign_in";
    private final static String TABLE_BOOK_ACTIVITIES = "book_activities";
    private final static String TABLE_WISHLIST = "wishlists";
    private final static String TABLE_USERS = "users";
    private final static String TABLE_GENRES = TABLE_BOOKS+"/genres";


    public  ZajelNetworkAPI getAPI() {

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
        @POST(TABLE_SIGN_UP)
        Call<SignUpRespnseBody> signUp( @Header("Content-Type") String contentType,@Header("Accept") String accept,@Body SignUpRequestBody signUpRequestBody);

        @POST(TABLE_GOOGLE_LOG_IN)
        Call<SignUpRespnseBody> googleLogIn( @Header("Content-Type") String contentType,@Header("Accept") String accept,@Body GoogleUser googleUser);

        @POST(TABLE_LOG_IN)
        Call<SignUpRespnseBody> logIn(@Header("Content-Type") String contentType,@Header("Accept") String accept,@Body LogInRequestBody logInRequestBody);


        @GET(TABLE_BOOKS)
        Call<Books> getBooks(@Header("Access-Token") String accessToken, @Header("Client") String client ,@Header("Expiry")
                String expiry ,@Header("Uid") String uid,@Header("Token-Type") String tokenType,@Header("Content-Type") String contentType,@Header("Accept") String accept, @Query("page") int page,@Query("genre") String genre);

        @POST(TABLE_BOOKS)
        Call<AddBookResponseModel> addBook(@Header("Access-Token") String accessToken, @Header("Client") String client ,@Header("Expiry")
                String expiry ,@Header("Uid") String uid,@Header("Token-Type") String tokenType,@Header("Content-Type") String contentType, @Header("Accept") String accept, @Body AddBookRequestModel addBookRequestModel);


        @PUT(TABLE_BOOKS+"/{id}")
        Call<AddBookResponseModel> updateBook(@Header("Access-Token") String accessToken, @Header("Client") String client ,@Header("Expiry")
                String expiry ,@Header("Uid") String uid,@Header("Token-Type") String tokenType,@Header("Content-Type") String contentType, @Header("Accept") String accept, @Body AddBookRequestModel addBookRequestModel, @Path("id") Integer id);


        @PUT(TABLE_BOOKS+"/{id}")
        Call<AddBookResponseModel> getBook(@Header("Access-Token") String accessToken, @Header("Client") String client ,@Header("Expiry")
                String expiry ,@Header("Uid") String uid,@Header("Token-Type") String tokenType,@Header("Content-Type") String contentType, @Header("Accept") String accept, @Path("id") Integer id);



        @GET(TABLE_BOOK_ACTIVITIES)
        Call<Requests> getSentRequests(@Header("Access-Token") String accessToken, @Header("Client") String client , @Header("Expiry")
                String expiry , @Header("Uid") String uid, @Header("Token-Type") String tokenType, @Header("Content-Type") String contentType, @Header("Accept") String accept,@Query("borrower") String borrower );

        @GET(TABLE_BOOK_ACTIVITIES)
        Call<Requests> getReceivedRequests(@Header("Access-Token") String accessToken, @Header("Client") String client ,@Header("Expiry")
                String expiry ,@Header("Uid") String uid,@Header("Token-Type") String tokenType,@Header("Content-Type") String contentType, @Header("Accept") String accept,@Query("lender") String lender);



        @GET(TABLE_WISHLIST)
        Call<WishListResponseBody> getWishlist(@Header("Access-Token") String accessToken, @Header("Client") String client , @Header("Expiry")
                String expiry , @Header("Uid") String uid, @Header("Token-Type") String tokenType, @Header("Content-Type") String contentType, @Header("Accept") String accept);



            /////
        @POST(TABLE_BOOK_ACTIVITIES)
        Call<BorrowBookResponseBody> boorowBook(@Header("Access-Token") String accessToken, @Header("Client") String client , @Header("Expiry")
                String expiry , @Header("Uid") String uid, @Header("Token-Type") String tokenType, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Body BorrowBookRequestBody borrowBookRequestBody);


        @DELETE(TABLE_BOOK_ACTIVITIES+"/{id}")
        Call<BorrowBookResponseBody> cancelBorrow(@Header("Access-Token") String accessToken, @Header("Client") String client ,@Header("Expiry")
                String expiry ,@Header("Uid") String uid,@Header("Token-Type") String tokenType,@Header("Content-Type") String contentType, @Header("Accept") String accept,@Path("id") Integer bookId);




        @POST(TABLE_WISHLIST)
        Call<BorrowBookResponseBody> addToWishList(@Header("Access-Token") String accessToken, @Header("Client") String client , @Header("Expiry")
                String expiry , @Header("Uid") String uid, @Header("Token-Type") String tokenType, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Body AddToWishlistRequestBody addToWishlistRequestBody);


        @DELETE(TABLE_WISHLIST+"/{id}")
        Call<BorrowBookResponseBody> deleteFromWishList(@Header("Access-Token") String accessToken, @Header("Client") String client , @Header("Expiry")
                String expiry , @Header("Uid") String uid, @Header("Token-Type") String tokenType, @Header("Content-Type") String contentType, @Header("Accept") String accept,@Path("id") Integer Id, @Query("book_id") Integer bookId);


        @PUT(TABLE_USERS+"/{user_id}")
        Call<JsonElement> updateFireBaseToken(@Header("Access-Token") String accessToken, @Header("Client") String client , @Header("Expiry")
                String expiry , @Header("Uid") String uid, @Header("Token-Type") String tokenType, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Path("user_id") Integer Id, @Body UpdateFirebaseTokenRequestBody updateFirebaseTokenRequestBody);




        @GET(TABLE_GENRES)
        Call<GenresList> getGenreList(@Header("Access-Token") String accessToken, @Header("Client") String client , @Header("Expiry")
                String expiry , @Header("Uid") String uid, @Header("Token-Type") String tokenType, @Header("Content-Type") String contentType, @Header("Accept") String accept);



        @PUT(TABLE_BOOK_ACTIVITIES+"/{activity_id}")
        Call<BorrowBookResponseBody> acceptRejectRequest(@Header("Access-Token") String accessToken, @Header("Client") String client , @Header("Expiry")
                String expiry , @Header("Uid") String uid, @Header("Token-Type") String tokenType, @Header("Content-Type") String contentType, @Header("Accept") String accept, @Body AcceptRejectRequestRequestBody acceptRejectRequestRequestBody,@Path("activity_id") Integer activityId);

    }



}

