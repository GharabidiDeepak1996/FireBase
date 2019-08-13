package com.example.firebasecloudmessage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.StorageReference;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    EditText ed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
                                                                                                //notification channel
        ed=findViewById( R.id.edittextbox );
if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
    NotificationChannel Channel=new NotificationChannel( "channel_id","channel_id", NotificationManager.IMPORTANCE_DEFAULT );
    NotificationManager manager=getSystemService( NotificationManager.class );

    manager.createNotificationChannel( Channel );
    }
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult().getToken();             //here token ganerated
                        ed.setText( token );
                        Log.d( TAG, "onComplete: "+token );
                        // Log and toast
                       // String msg = getString(R.string.msg_token_fmt, token);
                        String msg="sucess";
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this,msg , Toast.LENGTH_SHORT).show();
                    }
                });

}
}
