package com.zajel.zajelandroid.SignUp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cloudinary.Transformation;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;
import com.zajel.zajelandroid.APIManager.APIManager;
import com.zajel.zajelandroid.Dialogs.DialogUtil;
import com.zajel.zajelandroid.Login.LoginActivity;
import com.zajel.zajelandroid.MainActivity;
import com.zajel.zajelandroid.PreferenceManager;
import com.zajel.zajelandroid.R;
import com.zajel.zajelandroid.SignUp.Models.SignUpRequestBody;
import com.zajel.zajelandroid.SignUp.Models.SignUpRespnseBody;
import com.zajel.zajelandroid.Utils.ZajelUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Headers;

import static com.zajel.zajelandroid.Utils.ValidateUtil.*;
import static com.zajel.zajelandroid.Utils.ZajelUtils.openYearView;

public class ActivitySignUp extends AppCompatActivity implements View.OnClickListener, APIManager.SignUpResponse {
    private static final int CHOOSE_IMAGE_REQUEST_CODE = 1000;
    private static final int REQUEST_TAKE_PHOTO = 2000;
    @BindView(R.id.user_profile_picture)
    CircleImageView userImageView;
    @BindView(R.id.email_TextInputLayout)
    TextInputLayout emailTextInputLayout;
    @BindView(R.id.password_TextInputLayout)
    TextInputLayout passwordTextInputLayout;
    @BindView(R.id.confirm_password_TextInputLayout)
    TextInputLayout confirmPasswordTextInputLayout;
    @BindView(R.id.first_name_TextInputLayout)
    TextInputLayout firstNameTextInputLayout;
    @BindView(R.id.lastname_TextInputLayout)
    TextInputLayout lastNameTextInputLayout;
    @BindView(R.id.date_TextInputLayout)
    TextInputLayout dateTextInputLayout;
    @BindView(R.id.first_name_EditText)
    EditText firstNameEdittext;
    @BindView(R.id.lastname_EditText)
    EditText lastNameEditText;
    @BindView(R.id.date_EditText)
    EditText dateEditText;
    @BindView(R.id.email_EditText)
    EditText emailEditText;
    @BindView(R.id.password_EditText)
    EditText passwordEditText;
    @BindView(R.id.confirm_password_EditText)
    EditText confirmPasswordEditText;
    @BindView(R.id.sign_in_LinearLayout)
    LinearLayout signInLinearLayout;
    @BindView(R.id.signup_Button)
    Button signUpButton;
    @BindView(R.id.facebook_ImageView)
    ImageView facebookImageView;
    @BindView(R.id.google_ImageView)
    ImageView googleImageView;

    Calendar myCalendar = Calendar.getInstance();
    String firstNameString, lastNameString, imageURL, emailString, passwordString, phoneString, dobString;

    PreferenceManager preferenceManager;
    //api stuff
    APIManager apiManager;
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        emailEditText.addTextChangedListener(new MyTextWatcher(emailEditText));
        passwordEditText.addTextChangedListener(new MyTextWatcher(passwordEditText));
        confirmPasswordEditText.addTextChangedListener(new MyTextWatcher(confirmPasswordEditText));
        firstNameEdittext.addTextChangedListener(new MyTextWatcher(firstNameEdittext));
        dateEditText.addTextChangedListener(new MyTextWatcher(dateEditText));
        lastNameEditText.addTextChangedListener(new MyTextWatcher(lastNameEditText));
        preferenceManager = PreferenceManager.getInstance();

        apiManager = new APIManager(getApplicationContext());
        apiManager.setSignUpResponse(this);

        //  initialize the calender
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dateEditText.setOnClickListener(this);
        signInLinearLayout.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
        userImageView.setOnClickListener(this);


    }

    private void submitForm(String name, String email, String dob, String phone, String password, String lastName) {
        if (!validateusername(firstNameEdittext, firstNameTextInputLayout, ActivitySignUp.this)) {
            return;
        }
        if (!validateLastName(lastNameEditText, lastNameTextInputLayout, ActivitySignUp.this)) {
            return;
        }
        if (!validateEmail(emailEditText, emailTextInputLayout, ActivitySignUp.this)) {
            return;
        }
        if (!validatePassword(passwordEditText, passwordTextInputLayout, ActivitySignUp.this)) {
            return;
        }
        if (!validateConfirmPassword(confirmPasswordEditText, confirmPasswordTextInputLayout, ActivitySignUp.this, passwordEditText)) {
            return;
        }
        if (!validatedate(dateEditText, dateTextInputLayout, ActivitySignUp.this)) {
            return;
        }

        try {
            DialogUtil.showProgressDialog("Loading...", getSupportFragmentManager());
            SignUpRequestBody signUpRequestBody = new SignUpRequestBody(email, password, password, name, lastName, dob, "", imageURL);

            apiManager.signUp(signUpRequestBody);
        } catch (NullPointerException ignore) {

        }
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dateEditText.setText(sdf.format(myCalendar.getTime()));
    }


    private void openMediaChooser() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_IMAGE_REQUEST_CODE);
    }

    private void showSnackBar(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == CHOOSE_IMAGE_REQUEST_CODE && data != null && data.getData() != null) {
            MediaManager.get().upload(data.getData()).callback(new UploadCallback() {

                @Override
                public void onStart(String requestId) {
                    showSnackBar("Upload started...");
                }

                @Override
                public void onProgress(String requestId, long bytes, long totalBytes) {
                }

                @Override
                public void onSuccess(String requestId, Map resultData) {
                    imageURL = getUrlForMaxWidth(resultData.get("public_id").toString());
                    Picasso.with(ActivitySignUp.this).load(getUrlForMaxWidth(resultData.get("public_id").toString())).into(userImageView);
                    showSnackBar("Upload complete!");
                }

                @Override
                public void onError(String requestId, ErrorInfo error) {
                    showSnackBar("Upload error: " + error.getDescription());
                }

                @Override
                public void onReschedule(String requestId, ErrorInfo error) {
                    showSnackBar("Upload rescheduled.");
                }
            }).dispatch();
        }


    }

    public String getUrlForMaxWidth(String imageId) {
        int width = ZajelUtils.getScreenWidth(this);
        return MediaManager.get().getCloudinary().url().transformation(new Transformation().width(width)).generate(imageId);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.date_EditText:
                DatePickerDialog dpd = new DatePickerDialog(ActivitySignUp.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                openYearView(dpd.getDatePicker());
                dpd.setTitle("Select Date");
                dpd.show();
                break;


            case R.id.sign_in_LinearLayout:
                Intent i = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(i);
                finish();
                break;

            case R.id.signup_Button:
                firstNameString = firstNameEdittext.getText().toString();
                passwordString = passwordEditText.getText().toString();
                emailString = emailEditText.getText().toString();
                dobString = dateEditText.getText().toString();
                phoneString = lastNameEditText.getText().toString();
                lastNameString = lastNameEditText.getText().toString();
                submitForm(firstNameString, emailString, dobString, phoneString, passwordString, lastNameString);


                break;

            case R.id.user_profile_picture:
                openMediaChooser();
                break;
        }

    }

    @Override
    public void getSignUpResponse(SignUpRespnseBody signUpRespnseBody) {
        DialogUtil.removeProgressDialog();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void getSignUpHeaders(Headers headers) {

        preferenceManager.setAccessToken(headers.get("Access-Token"));
        preferenceManager.setClient(headers.get("Client"));
        preferenceManager.setExpiry(headers.get("Expiry"));
        preferenceManager.setUid(headers.get("Uid"));
        preferenceManager.setTokenType(headers.get("Token-Type"));
    }

    @Override
    public void errorOccureSignUp() {
        DialogUtil.removeProgressDialog();
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.email_EditText:
                    validateEmail(emailEditText, emailTextInputLayout, ActivitySignUp.this);
                    break;
                case R.id.password_EditText:
                    validatePassword(passwordEditText, passwordTextInputLayout, ActivitySignUp.this);
                    break;
                case R.id.confirm_password_EditText:
                    validateConfirmPassword(confirmPasswordEditText, confirmPasswordTextInputLayout, ActivitySignUp.this, passwordEditText);
                    break;
                case R.id.first_name_EditText:
                    validateusername(firstNameEdittext, firstNameTextInputLayout, ActivitySignUp.this);
                    break;
                case R.id.date_EditText:
                    validatedate(dateEditText, dateTextInputLayout, ActivitySignUp.this);
                    break;
                case R.id.lastname_EditText:
                    validateLastName(lastNameEditText, lastNameTextInputLayout, ActivitySignUp.this);
                    break;

            }
        }

    }
}
