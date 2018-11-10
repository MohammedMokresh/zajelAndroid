package com.zajel.zajelandroid.Dialogs;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.zajel.zajelandroid.PreferenceManager;
import com.zajel.zajelandroid.R;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class BooksCategoryBottomSheetFragment extends BottomSheetDialogFragment implements CompoundButton.OnCheckedChangeListener {

    RadioGroup radioGroup;
    private ArrayList<RadioButton> checkBoxArrayList = new ArrayList<>();
    private LinearLayout chekboxesLinearLayout;
    private ArrayList<String> options = new ArrayList<>();
    private boolean dontChangeTheSharedPref;

    PreferenceManager preferenceManager;
    @SuppressLint({"RestrictedApi", "InflateParams"})
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        //Set the custom view
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_books_category_bottom_sheet, null);
        dialog.setContentView(view);

        ArrayList<String> dataFromInstance = getArguments().getStringArrayList("dataFromInstance");
        ArrayList<String> dataFromID = getArguments().getStringArrayList("dataFromId");

        preferenceManager= PreferenceManager.getInstance();
        radioGroup = view.findViewById(R.id.radioGroup);
        TextView titleTextView = view.findViewById(R.id.title_textView);

        chekboxesLinearLayout = view.findViewById(R.id.chekboxes_linearLayout);
        if (dataFromInstance != null && dataFromID != null) {
            CheckboxdataIntoString(dataFromInstance, dataFromID);
        }

        for (RadioButton current : checkBoxArrayList) {
            current.setOnCheckedChangeListener(this);
        }
        lookForWhatIsChecked();
    }

    private void CheckboxdataIntoString(ArrayList<String> data, ArrayList<String> id_data) {

        for (int i = 0; i < data.size() && i < id_data.size(); i++) {

            RadioButton checkBox = new RadioButton(getContext());
            checkBox = (RadioButton) ((LayoutInflater)getContext().getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.filters_radiobutton,null);

            checkBox.setText(data.get(i));
            checkBox.setTag(id_data.get(i));

            Resources rProfile = getContext().getResources();
            int pxProfile = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    20,
                    rProfile.getDisplayMetrics()
            );


            checkBoxArrayList.add(checkBox);

            LinearLayout.LayoutParams checkParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            checkParams.setMargins(pxProfile+3, 10, pxProfile, 10);
            radioGroup.addView(checkBox, checkParams);

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        boolean isCheckedd = true;
        if (dontChangeTheSharedPref) {
                    if (isCheckedd) {
                        if (buttonView.isChecked()) {
                            preferenceManager.setChoosenCategory(buttonView.getText().toString());
                            isCheckedd = false;
                            broadcastIntent();
                            dismiss();
                        }
                    }

        }
    }

    private void lookForWhatIsChecked() {
        dontChangeTheSharedPref = false;
        for (int i = 0; i < checkBoxArrayList.size(); i++) {
            if (checkBoxArrayList.get(i).getText().toString().equals(preferenceManager.getChoosenCategory())) {
                checkBoxArrayList.get(i).setChecked(true);
            }
        }
        dontChangeTheSharedPref = true;


    }
    public void broadcastIntent() {
        Intent intent = new Intent();
        intent.setAction("com.zajel.zajelandroid.chooseCategory");
        // We should use LocalBroadcastManager when we want INTRA app
        // communication
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }

}
