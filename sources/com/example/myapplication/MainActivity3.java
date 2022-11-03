package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity3 extends AppCompatActivity {
    private Button masuk;
    /* access modifiers changed from: private */
    public EditText txttoko;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main3);
        this.txttoko = (EditText) findViewById(R.id.txttoko);
        Button button = (Button) findViewById(R.id.masuk);
        this.masuk = button;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String nama = MainActivity3.this.txttoko.getText().toString();
                if (nama.length() != 0) {
                    Intent intent = new Intent(MainActivity3.this, MainActivity4.class);
                    intent.putExtra("keytoko", nama);
                    MainActivity3.this.startActivity(intent);
                    Toast.makeText(MainActivity3.this, "Data Berhasil Diinput", 0).show();
                    return;
                }
                Toast.makeText(MainActivity3.this, "Masukkan Nama Tokomu Terlebih Dahulu Yaa !", 0).show();
            }
        });
    }
}
