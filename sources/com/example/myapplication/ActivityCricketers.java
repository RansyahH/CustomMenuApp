package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ActivityCricketers extends AppCompatActivity {
    ArrayList<Cricketer> cricketersList = new ArrayList<>();
    RecyclerView recyclerCricketers;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_cricketers);
        this.recyclerCricketers = (RecyclerView) findViewById(R.id.recycler_cricketers);
        this.recyclerCricketers.setLayoutManager(new LinearLayoutManager(this, 1, false));
        ArrayList<Cricketer> arrayList = (ArrayList) getIntent().getExtras().getSerializable("list");
        this.cricketersList = arrayList;
        this.recyclerCricketers.setAdapter(new CricketerAdapter(arrayList));
    }
}
