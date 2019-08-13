package com.example.authenticationdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyActivity extends AppCompatActivity {
    private String verificationId;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    EditText editText;
    private static final String TAG = "VerifyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_verify );

        progressBar = findViewById( R.id.progressbar );
        editText = findViewById( R.id.editTextCode );

        Intent intent = getIntent();
        String number = intent.getStringExtra( "phoneNumber" );
        sendTheNumber( number );
    }

    private void sendTheNumber(String number) {
        progressBar.setVisibility( View.VISIBLE );
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,             // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            Log.d( TAG, "onCodeSent: " + s );
            super.onCodeSent( s, forceResendingToken );
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            // we get the code to verification the code
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                editText.setText( code );
                verifyCode( code );
                Log.d( TAG, "onVerificationCompleted: " + phoneAuthCredential );

            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.d( TAG, "onVerificationFailed: " + e );
            Toast.makeText( VerifyActivity.this, e.getMessage(), Toast.LENGTH_LONG ).show();
        }
    };
    public void verify(View view) {
        String code = editText.getText().toString().trim();

        if (code.isEmpty() || code.length() < 6) {

            editText.setError("Enter code...");
            editText.requestFocus();
            return;
        }
        verifyCode(code);
    }

    private void verifyCode(String code) {
        //the user enters the verification code that Firebase sent to the user's phone
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(VerifyActivity.this, ProfileActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            // FirebaseUser user = task.getResult().getUser();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
}
