/*
Group No: 16
Names: Yash Bonde
       Digvijay Gole
       Ankit Kejriwal
 */

package com.example.inclass03;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {


    ImageView genderImage;
    RadioGroup gender;
    RadioButton FemaleRadioBtn;
    RadioButton MaleRadioBtn;
    EditText firstName;
    EditText lastName;
    Button save;
    String genderValue = "";
    String fname = "";
    String lname = "";
    int selectedId = -1;
    public  static  final int REQ_CODE = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        genderImage = findViewById(R.id.genderImage);
        gender = findViewById(R.id.gender);
        FemaleRadioBtn = findViewById(R.id.FemaleRadioBtn);
        MaleRadioBtn = findViewById(R.id.MaleRadiobtn);
        save = findViewById(R.id.save);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);

        genderImage.setImageDrawable(getDrawable(R.mipmap.ic_launcher_round));

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.FemaleRadioBtn:
                        genderImage.setImageDrawable(getDrawable(R.drawable.female));
                        genderValue = "Female";
                        FemaleRadioBtn.setError(null);
                        MaleRadioBtn.setError(null);
                        break;
                    case R.id.MaleRadiobtn:
                        genderImage.setImageDrawable(getDrawable(R.drawable.male));
                        genderValue = "Male";
                        FemaleRadioBtn.setError(null);
                        MaleRadioBtn.setError(null);
                        break;
                    default:
                        break;
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname = firstName.getText().toString();
                lname = lastName.getText().toString();

                selectedId = gender.getCheckedRadioButtonId();

                if (fname.length() <= 0) {
                    firstName.setError("Enter First Name");
                } else if (lname.length() <= 0) {
                    lastName.setError("Enter Last Name");
                } else if (selectedId == -1) {
                    FemaleRadioBtn.setError("Select One");
                    MaleRadioBtn.setError("Select One");

                } else {

                    UserData userData = new UserData(fname, lname, genderValue);
                    Intent intent = new Intent(MainActivity.this, ViewScreen.class);
                    intent.putExtra("serializableObject", userData);
                    startActivityForResult(intent,REQ_CODE);
                }


            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        final UserData userEditData = (UserData) getIntent().getSerializableExtra("userEditData");

        if (requestCode == REQ_CODE && resultCode == RESULT_OK) {
            if (userEditData != null) {
                switch (userEditData.gender) {
                    case ("Female"):
                        genderImage.setImageDrawable(getDrawable(R.drawable.female));
                        genderValue = "Female";
                        gender.clearCheck();
                        FemaleRadioBtn.setChecked(true);
                        break;
                    case ("Male"):
                        genderImage.setImageDrawable(getDrawable(R.drawable.male));
                        genderValue = "Male";
                        gender.clearCheck();
                        MaleRadioBtn.setChecked(true);
                        break;
                    default:
                        break;
                }
                firstName.setText(userEditData.firstname);
                lastName.setText(userEditData.lastname);
            }
        }
    }
}