package com.zajel.zajelandroid.Book;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cloudinary.Transformation;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;
import com.zajel.zajelandroid.APIManager.APIManager;
import com.zajel.zajelandroid.Book.AddBookModels.AddBookRequestModel;
import com.zajel.zajelandroid.Book.AddBookModels.AddBookResponseModel;
import com.zajel.zajelandroid.Book.AddBookModels.RequestBook;
import com.zajel.zajelandroid.PreferenceManager;
import com.zajel.zajelandroid.R;
import com.zajel.zajelandroid.SignUp.ActivitySignUp;
import com.zajel.zajelandroid.SignUp.Models.SignUpRequestBody;
import com.zajel.zajelandroid.Utils.NothingSelectedSpinnerAdapter;
import com.zajel.zajelandroid.Utils.ZajelUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zajel.zajelandroid.Utils.ValidateUtil.validateConfirmPassword;
import static com.zajel.zajelandroid.Utils.ValidateUtil.validateEmail;
import static com.zajel.zajelandroid.Utils.ValidateUtil.validateEmptyField;
import static com.zajel.zajelandroid.Utils.ValidateUtil.validateLastName;
import static com.zajel.zajelandroid.Utils.ValidateUtil.validatePassword;
import static com.zajel.zajelandroid.Utils.ValidateUtil.validatedate;
import static com.zajel.zajelandroid.Utils.ValidateUtil.validateusername;
import static com.zajel.zajelandroid.Utils.ZajelUtils.openYearView;

public class AddUpdateBookActivity extends AppCompatActivity implements View.OnClickListener,APIManager.AddBookResponse {

    @BindView(R.id.back_ImageButton)
    ImageButton backImageButton;
    @BindView(R.id.bookImageView)
    AppCompatImageView bookImageView;
    @BindView(R.id.title_EditText)
    AppCompatEditText titleEditText;
    @BindView(R.id.title_TextInputLayout)
    TextInputLayout titleTextInputLayout;

    @BindView(R.id.author_EditText)
    AppCompatEditText authorEditText;
    @BindView(R.id.author_TextInputLayout)
    TextInputLayout authorTextInputLayout;

    @BindView(R.id.description_EditText)
    AppCompatEditText descriptionEditText;
    @BindView(R.id.description_TextInputLayout)
    TextInputLayout descriptionTextInputLayout;

    @BindView(R.id.publish_year_EditText)
    AppCompatEditText publishYearEditText;
    @BindView(R.id.publish_year_TextInputLayout)
    TextInputLayout publishYearTextInputLayout;

    @BindView(R.id.page_count_EditText)
    AppCompatEditText pageCountEditText;
    @BindView(R.id.page_count_TextInputLayout)
    TextInputLayout pageCountTextInputLayout;

    @BindView(R.id.genre_Spinner)
    AppCompatSpinner genreSpinner;
    @BindView(R.id.language_Spinner)
    AppCompatSpinner languageSpinner;
    @BindView(R.id.add_Button)
    AppCompatButton addBookButton;


    Calendar myCalendar = Calendar.getInstance();
     DatePickerDialog.OnDateSetListener date;
    PreferenceManager preferenceManager;
    //api stuff
    APIManager apiManager;
    private static final int CHOOSE_IMAGE_REQUEST_CODE = 1000;

    String imageURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_book);
        ButterKnife.bind(this);

        titleEditText.addTextChangedListener(new MyTextWatcher(titleEditText));
        authorEditText.addTextChangedListener(new MyTextWatcher(authorEditText));
        publishYearEditText.addTextChangedListener(new MyTextWatcher(publishYearEditText));
        pageCountEditText.addTextChangedListener(new MyTextWatcher(pageCountEditText));
        descriptionEditText.addTextChangedListener(new MyTextWatcher(descriptionEditText));
        preferenceManager = PreferenceManager.getInstance();
        apiManager = new APIManager(getApplicationContext());
        apiManager.setAddBookResponse(this);

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

        publishYearEditText.setOnClickListener(this);
        bookImageView.setOnClickListener(this);
        addBookButton.setOnClickListener(this);
        backImageButton.setOnClickListener(this);


        String[] languages = {"English", "Arabic", "Malay", "Chinese", "Korean"};
        ArrayAdapter<String> languagesAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item_layout, languages);
        languagesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(new NothingSelectedSpinnerAdapter(languagesAdapter, R.layout.nothing_selected_spinner_layout, getApplicationContext()));


        String[] genre = {"Negotiable", "Minimal", "Practical", "Moderate", "Substantial", "High"};
        ArrayAdapter<String> genreAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item_layout, genre);
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(new NothingSelectedSpinnerAdapter(genreAdapter, R.layout.nothing_selected_spinner_layout, getApplicationContext()));

    }

    private void updateLabel() {
        String myFormat = "yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        publishYearEditText.setText(sdf.format(myCalendar.getTime()));
    }

    private void submitForm(Integer id) {
        if (!validateEmptyField(titleEditText, titleTextInputLayout, AddUpdateBookActivity.this, getString(R.string.title_empty))) {
            return;
        }
        if (!validateEmptyField(authorEditText, authorTextInputLayout, AddUpdateBookActivity.this, getString(R.string.author_empty))) {
            return;
        }
        if (!validateEmptyField(publishYearEditText, publishYearTextInputLayout, AddUpdateBookActivity.this, getString(R.string.publish_year_empty))) {
            return;
        }
        if (!validateEmptyField(pageCountEditText, pageCountTextInputLayout, AddUpdateBookActivity.this, getString(R.string.page_count_empty))) {
            return;
        }
        if (!validateEmptyField(descriptionEditText, descriptionTextInputLayout, AddUpdateBookActivity.this, getString(R.string.description_empty))) {
            return;
        }

        try {
            AddBookRequestModel addBookRequestModel = new AddBookRequestModel(new RequestBook(titleEditText.getText().toString(),
                    authorEditText.getText().toString(),publishYearEditText.getText().toString(),languageSpinner.getSelectedItem().toString()
                    ,Integer.parseInt(pageCountEditText.getText().toString()),imageURL,genreSpinner.getSelectedItem().toString(),1,"available"));
            apiManager.updateBook(addBookRequestModel,id);



        } catch (NullPointerException e) {

        }
    }

    private void submitForm() {
        if (!validateEmptyField(titleEditText, titleTextInputLayout, AddUpdateBookActivity.this, getString(R.string.title_empty))) {
            return;
        }
        if (!validateEmptyField(authorEditText, authorTextInputLayout, AddUpdateBookActivity.this, getString(R.string.author_empty))) {
            return;
        }
        if (!validateEmptyField(publishYearEditText, publishYearTextInputLayout, AddUpdateBookActivity.this, getString(R.string.publish_year_empty))) {
            return;
        }
        if (!validateEmptyField(pageCountEditText, pageCountTextInputLayout, AddUpdateBookActivity.this, getString(R.string.page_count_empty))) {
            return;
        }
        if (!validateEmptyField(descriptionEditText, descriptionTextInputLayout, AddUpdateBookActivity.this, getString(R.string.description_empty))) {
            return;
        }

        try {
            AddBookRequestModel addBookRequestModel = new AddBookRequestModel(new RequestBook(titleEditText.getText().toString(),
                    authorEditText.getText().toString(),publishYearEditText.getText().toString(),languageSpinner.getSelectedItem().toString()
            ,Integer.parseInt(pageCountEditText.getText().toString()),imageURL,genreSpinner.getSelectedItem().toString(),1,"available"));
            apiManager.addBook(addBookRequestModel);



        } catch (NullPointerException e) {

        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.publish_year_EditText:
                DatePickerDialog dpd = new DatePickerDialog(AddUpdateBookActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                openYearView(dpd.getDatePicker());
                dpd.setTitle("Select Date");
                dpd.show();
                break;

            case R.id.bookImageView:
                openMediaChooser();

                break;

            case R.id.add_Button:
                submitForm();
                break;
//            case R.id.update_Button:
//                submitForm(1);
//                break;


            case R.id.back_ImageButton:

                break;
        }
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
                    Picasso.get().load(getUrlForMaxWidth(resultData.get("public_id").toString())).into(bookImageView);
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
    public void addBookResponse(AddBookResponseModel addBookResponseModel) {
        Log.e("test",addBookResponseModel.getResponseBook().getAuthor());
    }

    @Override
    public void errorOccureAddBook() {


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
                case R.id.title_EditText:
                    validateEmptyField(titleEditText, titleTextInputLayout, AddUpdateBookActivity.this, getString(R.string.title_empty));
                    break;
                case R.id.author_EditText:
                    validateEmptyField(authorEditText, authorTextInputLayout, AddUpdateBookActivity.this, getString(R.string.author_empty));
                    break;
                case R.id.publish_year_EditText:
                    validateEmptyField(publishYearEditText, publishYearTextInputLayout, AddUpdateBookActivity.this, getString(R.string.publish_year_empty));
                    break;
                case R.id.page_count_EditText:
                    validateEmptyField(pageCountEditText, pageCountTextInputLayout, AddUpdateBookActivity.this, getString(R.string.page_count_empty));
                    break;
                case R.id.description_EditText:
                    validateEmptyField(descriptionEditText, descriptionTextInputLayout, AddUpdateBookActivity.this, getString(R.string.description_empty));
                    break;

            }
        }

    }

}
