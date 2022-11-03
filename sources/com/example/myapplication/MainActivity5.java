package com.example.myapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity5 extends AppCompatActivity {
    Button b1;
    private ArrayList<String> data = new ArrayList<>();
    private ArrayList<String> data1 = new ArrayList<>();
    private ArrayList<String> data2 = new ArrayList<>();
    private ArrayList<String> data3 = new ArrayList<>();
    EditText ed1;
    EditText ed2;
    EditText ed3;
    EditText ed4;
    EditText ed5;
    EditText ed6;
    private TableLayout table;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main5);
        this.ed1 = (EditText) findViewById(R.id.ed1);
        this.ed2 = (EditText) findViewById(R.id.ed2);
        this.ed3 = (EditText) findViewById(R.id.ed3);
        this.ed4 = (EditText) findViewById(R.id.txtsub);
        this.ed5 = (EditText) findViewById(R.id.txtpay);
        this.ed6 = (EditText) findViewById(R.id.txtbal);
        this.b1 = (Button) findViewById(R.id.btn1);
        this.ed5.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                MainActivity5.this.ed6.setText(String.valueOf(Integer.parseInt(MainActivity5.this.ed5.getText().toString()) - Integer.parseInt(MainActivity5.this.ed4.getText().toString())));
            }
        });
        this.b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity5.this.add();
            }
        });
    }

    public void add() {
        String prodname = this.ed1.getText().toString();
        if (prodname.length() != 0) {
            int price = Integer.parseInt(this.ed2.getText().toString());
            int qty = Integer.parseInt(this.ed3.getText().toString());
            this.data.add(prodname);
            this.data1.add(String.valueOf(price));
            this.data2.add(String.valueOf(qty));
            this.data3.add(String.valueOf(price * qty));
            TableLayout table2 = (TableLayout) findViewById(R.id.tbl);
            TableRow row = new TableRow(this);
            TextView t1 = new TextView(this);
            TextView t2 = new TextView(this);
            TextView t3 = new TextView(this);
            TextView t4 = new TextView(this);
            int sum = 0;
            int i = 0;
            while (i < this.data.size()) {
                String prodname2 = prodname;
                String total = this.data3.get(i);
                t1.setText(this.data.get(i));
                t2.setText(this.data1.get(i));
                t3.setText(this.data2.get(i));
                t4.setText(total);
                String str = total;
                sum += Integer.parseInt(this.data3.get(i).toString());
                i++;
                prodname = prodname2;
            }
            row.addView(t1);
            row.addView(t2);
            row.addView(t3);
            row.addView(t4);
            table2.addView(row);
            this.ed4.setText(String.valueOf(sum));
            this.ed1.setText("");
            this.ed2.setText("");
            this.ed3.setText("");
            this.ed1.requestFocus();
            Toast.makeText(this, "Data Telah Diinput", 0).show();
            return;
        }
        Toast.makeText(this, "Lengkapi Data Terlebih Dahulu !", 0).show();
    }
}
