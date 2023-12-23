package com.example.projetmobile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class CallActivity extends AppCompatActivity {

    private static final int REQUEST_CALL_PERMISSION = 1;
    private String personPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        Intent intent = getIntent();
        String personName = intent.getStringExtra("personName");
        personPhone = intent.getStringExtra("personPhone");

        TextView nomTextView = findViewById(R.id.nom);
        TextView telephoneTextView = findViewById(R.id.telephone);

        nomTextView.setText(personName);
        telephoneTextView.setText(personPhone);

        ImageButton callButton = findViewById(R.id.call);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initiatePhoneCall();
            }
        });

        ImageButton backButton = findViewById(R.id.imageButton3);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initiatePhoneCall() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

            Intent callIntent = new Intent(Intent.ACTION_CALL);

            callIntent.setData(Uri.parse("tel:" + personPhone));

            startActivity(callIntent);
        } else {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_CALL_PERMISSION
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CALL_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initiatePhoneCall();
            } else {
                Toast.makeText(this, "Permission denied. Cannot make a phone call.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
