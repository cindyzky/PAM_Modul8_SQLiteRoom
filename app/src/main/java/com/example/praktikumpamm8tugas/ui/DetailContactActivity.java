package com.example.praktikumpamm8tugas.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.praktikumpamm8tugas.R;
import com.example.praktikumpamm8tugas.database.Contact;

public class DetailContactActivity extends AppCompatActivity {
    TextView tvName, tvNumber, tvInstagram, tvGroup, back_link;
    TextView btnCall,btnMessage, btnInstagram;
    LinearLayout layWhatsapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_contact);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        back_link = findViewById(R.id.back_link);
        tvName = findViewById(R.id.tv_name);
        tvNumber = findViewById(R.id.tv_number);
        tvInstagram = findViewById(R.id.tv_instagram);
        tvGroup = findViewById(R.id.tv_group);
        btnCall = findViewById(R.id.btn_call);
        btnMessage = findViewById(R.id.btn_message);
        btnInstagram = findViewById(R.id.btn_instagram);
        layWhatsapp = findViewById(R.id.layout_whatsapp);

        // get intent extra
        Contact contact = getIntent().getParcelableExtra("contact");
        if (contact != null){
            tvName.setText(contact.getName());
            tvNumber.setText(contact.getNumber());
            tvInstagram.setText(contact.getInstagram());
            tvGroup.setText(contact.getGroup());

            btnCall.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+contact.getNumber()));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                    return;
                }
                startActivity(intent);
            });

            btnMessage.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("sms:"+contact.getNumber()));
                startActivity(intent);
            });

            layWhatsapp.setOnClickListener(v -> {
                Intent sendIntent = new Intent("android.intent.action.MAIN");
                sendIntent.setAction(Intent.ACTION_VIEW);
                sendIntent.setPackage("com.whatsapp");
                String url = "https://api.whatsapp.com/send?phone="+contact.getNumber()+"&text=";
                sendIntent.setData(Uri.parse(url));
                startActivity(sendIntent);
            });

            btnInstagram.setOnClickListener(v -> {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/" + contact.getInstagram())));
            });
        }

        back_link.setOnClickListener(v-> {
            setResult(RESULT_OK, null);
            finish();
        });
    }
}