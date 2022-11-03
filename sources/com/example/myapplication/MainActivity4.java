package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity4 extends AppCompatActivity {
    private TextView tv_toko;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main4);
        this.tv_toko = (TextView) findViewById(R.id.tv_toko);
        this.tv_toko.setText(getIntent().getStringExtra("keytoko"));
    }

    public void submit(View view) {
        startActivity(new Intent(this, MainActivity2.class));
    }

    public void Hitung(View view) {
        startActivity(new Intent(this, MainActivity5.class));
    }

    public void catatan(View view) {
        startActivity(new Intent(this, MainActivity11.class));
    }
}
