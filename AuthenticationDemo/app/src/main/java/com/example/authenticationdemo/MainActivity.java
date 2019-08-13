package com.example.authenticationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button button;
    EditText editTextMobile;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );


        editTextMobile = findViewById( R.id.editTextPhone );
        button = findViewById( R.id.buttonContinue );
        spinner = findViewById( R.id.spinnerCountries );
        spinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,CountryData.countryNames));
    }

    public void submit(View view) {
        String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];

        String number = (editTextMobile.getText().toString().trim()); //
        if (number.isEmpty() || number.length() < 10) {
            editTextMobile.setError( "Enter a valid mobile" );
            editTextMobile.requestFocus();
            return;
        }
        String phoneNumber = "+" + code + number;

        Intent intent = new Intent( MainActivity.this, VerifyActivity.class );
        intent.putExtra( "phoneNumber", phoneNumber );
        startActivity( intent );
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        }
    }
}
