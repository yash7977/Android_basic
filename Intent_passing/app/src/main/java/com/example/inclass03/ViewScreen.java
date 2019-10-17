package com.example.inclass03;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewScreen extends AppCompatActivity {


    TextView FirstLastName;
    TextView genderDisplay;
    ImageView GenderImage;
    Button edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_screen);


        FirstLastName = findViewById(R.id.FirstLastName);
        genderDisplay = findViewById(R.id.genderDisplay);
        GenderImage = findViewById(R.id.GenderImage);
        edit = findViewById(R.id.Edit);
        final UserData user = (UserData) getIntent().getSerializableExtra("serializableObject");

        FirstLastName.setText(user.firstname +" "+user.lastname);
        genderDisplay.setText(user.gender);

        switch (user.gender){
            case ("Male"):
                GenderImage.setImageDrawable(getDrawable(R.drawable.male));
                break;
            case ("Female"):
                GenderImage.setImageDrawable(getDrawable(R.drawable.female));
                break;
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ViewScreen.this,MainActivity.class);
                intent1.putExtra("userEditData",user);
                setResult(ViewScreen.RESULT_OK,intent1);
                finish();
            }
        });


    }
}
