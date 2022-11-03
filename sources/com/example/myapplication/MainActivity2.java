package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {
    private static String LOG_TAG = "UIElementsPraticeLog";
    private static final String TAG = "MainActivity2";
    Button buttonAdd;
    Button buttonsubmitlist;
    ArrayList<Cricketer> cricketerList = new ArrayList<>();
    LinearLayout layoutlist;
    List<String> teamlist = new ArrayList();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main2);
        this.layoutlist = (LinearLayout) findViewById(R.id.layout_list);
        this.buttonAdd = (Button) findViewById(R.id.button_add);
        this.buttonsubmitlist = (Button) findViewById(R.id.button_submit_list);
        this.buttonAdd.setOnClickListener(this);
        this.buttonsubmitlist.setOnClickListener(this);
        this.teamlist.add("...");
        this.teamlist.add("Ada");
        this.teamlist.add("Tidak Ada");
        this.teamlist.add("Sedang Diantar");
        this.teamlist.add("Sedang Diproses");
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, 0).show();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add /*2131230818*/:
                addView();
                return;
            case R.id.button_submit_list /*2131230819*/:
                if (checkIfValidAndRead()) {
                    Intent intent = new Intent(this, ActivityCricketers.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list", this.cricketerList);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    return;
                }
                return;
            default:
                return;
        }
    }

    private boolean checkIfValidAndRead() {
        this.cricketerList.clear();
        boolean result = true;
        int i = 0;
        while (true) {
            if (i >= this.layoutlist.getChildCount()) {
                break;
            }
            View cricketerView = this.layoutlist.getChildAt(i);
            EditText editTextName = (EditText) cricketerView.findViewById(R.id.edit_barang);
            EditText editTextName1 = (EditText) cricketerView.findViewById(R.id.edit_harga);
            AppCompatSpinner spinnerTeam = (AppCompatSpinner) cricketerView.findViewById(R.id.spinner_keterangan);
            Cricketer cricketer = new Cricketer();
            if (editTextName.getText().toString().equals("")) {
                result = false;
                break;
            }
            cricketer.setCricketerName(editTextName.getText().toString());
            if (editTextName1.getText().toString().equals("")) {
                result = false;
                break;
            }
            cricketer.setCricketerPrice(editTextName1.getText().toString());
            if (spinnerTeam.getSelectedItemPosition() == 0) {
                result = false;
                break;
            }
            cricketer.setTeamName(this.teamlist.get(spinnerTeam.getSelectedItemPosition()));
            this.cricketerList.add(cricketer);
            i++;
        }
        if (this.cricketerList.size() == 0) {
            Toast.makeText(this, "Masukkan Barang Terlebih Dahulu!", 0).show();
            return false;
        } else if (result) {
            return result;
        } else {
            Toast.makeText(this, "Masukkan Semua Dengan Benar!", 0).show();
            return result;
        }
    }

    private void addView() {
        final View cricketerView = getLayoutInflater().inflate(R.layout.row_add_cricketer, (ViewGroup) null, false);
        EditText editText = (EditText) cricketerView.findViewById(R.id.edit_barang);
        EditText editText2 = (EditText) cricketerView.findViewById(R.id.edit_harga);
        ((AppCompatSpinner) cricketerView.findViewById(R.id.spinner_keterangan)).setAdapter((SpinnerAdapter) new ArrayAdapter(this, 17367048, this.teamlist));
        ((ImageView) cricketerView.findViewById(R.id.image_remove)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity2.this.removeView(cricketerView);
            }
        });
        this.layoutlist.addView(cricketerView);
    }

    /* access modifiers changed from: private */
    public void removeView(View view) {
        this.layoutlist.removeView(view);
    }
}
