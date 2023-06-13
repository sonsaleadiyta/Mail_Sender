package com.aditya.mail_sender;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private EditText mail_address,subject,message;
private TextView show;
private Button attatch;
private Button send;
    private static final int REQUEST_CODE_ATTACHMENT = 1;
    private Uri attachmentUri;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mail_address=findViewById(R.id.mail_address);
        subject=findViewById(R.id.mail_address);
        message=findViewById(R.id.message);
        show=findViewById(R.id.show);
        send=findViewById(R.id.send);
        attatch=findViewById(R.id.send);

    }
    public void sendmail(View view)
    {
        String add=mail_address.getText().toString().trim();

        if(add.isEmpty())
        {
            Toast.makeText(this, "invalid mail ", Toast.LENGTH_SHORT).show();
        }
        else
        {

            String address[]=add.split(",");
            String msg=message.getText().toString().trim();
            String sub=subject.getText().toString().trim();
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_EMAIL, address);
            intent.putExtra(Intent.EXTRA_SUBJECT, sub);
            intent.putExtra(Intent.EXTRA_TEXT, msg);
            if (attachmentUri != null)
            {
                intent.putExtra(Intent.EXTRA_STREAM, attachmentUri);
            }
            if (intent.resolveActivity(getPackageManager()) != null)
            {
                intent.setType("message/rfc822");
                startActivity(intent);
            }
        }


    }

   public void openFilePicker(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, REQUEST_CODE_ATTACHMENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ATTACHMENT && resultCode == RESULT_OK && data != null) {
            attachmentUri = data.getData();
            show.setText("Attachment: " + attachmentUri.getLastPathSegment());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



}

