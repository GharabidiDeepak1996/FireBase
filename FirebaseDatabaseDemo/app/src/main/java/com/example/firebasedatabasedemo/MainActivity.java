package com.example.firebasedatabasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText Ename, Epass, Ephone, Eemail, Eusername;
    DatabaseReference myRef;
    Modelclass modelclass;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        Ename = findViewById( R.id.name );
        Ephone = findViewById( R.id.phone );
        Eemail = findViewById( R.id.email );
        Eusername = findViewById( R.id.username );
        Epass = findViewById( R.id.password );

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("message");  //table name is message
        myRef = database.getReference( "message" );  //table name is message
        modelclass=new Modelclass(  );
    }
private void getValues(){
    modelclass.setName( Ename.getText().toString() );
    modelclass.setEmail( Eemail.getText().toString() );
    modelclass.setPassword( Epass.getText().toString() );
    modelclass.setUserName( Eusername.getText().toString() );
    modelclass.setPhone( Epass.getText().toString() );
}
    public void insert(View view) {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
              /*  String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);*/
                getValues();
              myRef.child( "message1" ).setValue( modelclass );
              Toast.makeText( MainActivity.this,"Data is inserte",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}


