package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CricketerAdapter extends RecyclerView.Adapter<CricketerView> {
    ArrayList<Cricketer> cricketersList = new ArrayList<>();

    public CricketerAdapter(ArrayList<Cricketer> cricketersList2) {
        this.cricketersList = cricketersList2;
    }

    public CricketerView onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CricketerView(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cricketer, parent, false));
    }

    public void onBindViewHolder(CricketerView holder, int position) {
        Cricketer cricketer = this.cricketersList.get(position);
        holder.textCricketerName.setText(cricketer.getCricketerName());
        holder.textCricketerPrice.setText(cricketer.getCricketerPrice());
        holder.textTeamName.setText(cricketer.getTeamName());
    }

    public int getItemCount() {
        return this.cricketersList.size();
    }

    public class CricketerView extends RecyclerView.ViewHolder {
        TextView textCricketerName;
        TextView textCricketerPrice;
        TextView textTeamName;

        public CricketerView(View itemView) {
            super(itemView);
            this.textCricketerName = (TextView) itemView.findViewById(R.id.text_cricketer_name);
            this.textCricketerPrice = (TextView) itemView.findViewById(R.id.text_team_price);
            this.textTeamName = (TextView) itemView.findViewById(R.id.text_team_name);
        }
    }
}
