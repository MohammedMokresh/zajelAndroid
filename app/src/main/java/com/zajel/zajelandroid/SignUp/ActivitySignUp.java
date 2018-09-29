package com.zajel.zajelandroid.SignUp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import com.google.android.material.textfield.TextInputLayout;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.squareup.picasso.Picasso;
import com.zajel.zajelandroid.APIManager.APIManager;
import com.zajel.zajelandroid.Login.LoginActivity;
import com.zajel.zajelandroid.PreferenceManager;
import com.zajel.zajelandroid.R;
import com.zajel.zajelandroid.SignUp.Models.SignUpRequestBody;
import com.zajel.zajelandroid.SignUp.Models.SignUpRespnseBody;
import com.zajel.zajelandroid.Utils.ZajelUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Headers;

import static com.zajel.zajelandroid.Utils.ValidateUtil.*;
import static com.zajel.zajelandroid.Utils.ZajelUtils.openYearView;

public class ActivitySignUp extends AppCompatActivity implements View.OnClickListener,APIManager.SignUpResponse {
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
    File photoFile;
    String firstNameString,lastNameString,imageURL, emailString, passwordString, phoneString, dobString;
    Uri pickedImage, photoURI;

    PreferenceManager preferenceManager;
    //api stuff
    APIManager apiManager;
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
         preferenceManager =PreferenceManager.getInstance();

        apiManager = new APIManager(getApplicationContext());
        apiManager.setSignUpResponse(this);



        //  initialize the calender
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

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


        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(ActivitySignUp.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                openYearView(dpd.getDatePicker());
                dpd.setTitle("Select Date");
                dpd.show();


            }
        });


        signInLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        });


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstNameString = firstNameEdittext.getText().toString();
                passwordString = passwordEditText.getText().toString();
                emailString = emailEditText.getText().toString();
                dobString = dateEditText.getText().toString();
                phoneString = lastNameEditText.getText().toString();
                lastNameString =lastNameEditText.getText().toString();
                submitForm(firstNameString, emailString, dobString, phoneString, passwordString,lastNameString);


            }
        });

        userImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMediaChooser();
            }
        });

//        googleImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (ContextCompat.checkSelfPermission(ActivitySignUp.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//
//                    ActivityCompat.requestPermissions(ActivitySignUp.this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
//                }
//
//
//
//            }
//        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePhotoIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        Log.e("es", ex.getMessage());
                        // Error occurred while creating the File
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        photoURI = FileProvider.getUriForFile(getApplicationContext(),
                                "ccom.zajel.zajelandroid",
                                photoFile);
                        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);


//                        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT,
//                                Uri.fromFile(photoFile));
                        startActivityForResult(takePhotoIntent, REQUEST_TAKE_PHOTO);
                    }
                }
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "capturedImage";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        return image;
    }

    private void submitForm(String name, String email, String dob, String phone, String password,String lastName) {
        if (!validateusername(firstNameEdittext,firstNameTextInputLayout,ActivitySignUp.this)) {
            return;
        }
        if (!validateLastName(lastNameEditText,lastNameTextInputLayout,ActivitySignUp.this)) {
            return;
        }
        if (!validateEmail(emailEditText,emailTextInputLayout,ActivitySignUp.this)) {
            return;
        }
        if (!validatePassword(passwordEditText,passwordTextInputLayout,ActivitySignUp.this)) {
            return;
        }
        if (!validateConfirmPassword(confirmPasswordEditText,confirmPasswordTextInputLayout,ActivitySignUp.this,passwordEditText)) {
            return;
        }
        if (!validatedate(dateEditText,dateTextInputLayout,ActivitySignUp.this)) {
            return;
        }

        try {

            SignUpRequestBody signUpRequestBody= new SignUpRequestBody(email,password,password,name,lastName,dob,"",imageURL);

            apiManager.signUp(signUpRequestBody);

//            SignUpCall(name, email, dob, phone, password);
        } catch (NullPointerException e) {

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
                    imageURL=getUrlForMaxWidth(resultData.get("public_id").toString());
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


        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == RESULT_OK) {
                //File to upload to cloudinary
                Map config = new HashMap();
                config.put("cloud_name", "zajel");
                config.put("api_key", "484384578824121");
                config.put("api_secret", "IXoBI4dVMV8KX-IlBQeHaSeO0eU");
                Cloudinary cloudinary = new Cloudinary(config);
                try {
                    cloudinary.uploader().upload(photoFile.getAbsolutePath(), Cloudinary.asMap());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
                //finish();
            }
        }
    }


    public String getUrlForMaxWidth(String imageId) {
        int width = ZajelUtils.getScreenWidth(this);
        return MediaManager.get().getCloudinary().url().transformation(new Transformation().width(width)).generate(imageId);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

    }

    @Override
    public void getSignUpResponse(SignUpRespnseBody signUpRespnseBody) {

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
                    validateEmail(emailEditText,emailTextInputLayout,ActivitySignUp.this);
                    break;
                case R.id.password_EditText:
                    validatePassword(passwordEditText,passwordTextInputLayout,ActivitySignUp.this);
                    break;
                case R.id.confirm_password_EditText:
                    validateConfirmPassword(confirmPasswordEditText,confirmPasswordTextInputLayout,ActivitySignUp.this,passwordEditText);
                    break;
                case R.id.first_name_EditText:
                    validateusername(firstNameEdittext,firstNameTextInputLayout,ActivitySignUp.this);
                    break;
                case R.id.date_EditText:
                    validatedate(dateEditText,dateTextInputLayout,ActivitySignUp.this);
                    break;
                case R.id.lastname_EditText:
                    validateLastName(lastNameEditText,lastNameTextInputLayout,ActivitySignUp.this);
                    break;
//                case R.id.Address:
//                    validateaddress();
//                    break;

            }
        }

    }
}
