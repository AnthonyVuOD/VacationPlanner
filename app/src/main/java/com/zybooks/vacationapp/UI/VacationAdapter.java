package com.zybooks.vacationapp.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zybooks.vacationapp.R;
import com.zybooks.vacationapp.entities.Vacation;

import java.util.List;

public class VacationAdapter extends RecyclerView.Adapter<VacationAdapter.VacationViewHolder> {
    private List<Vacation> mVacations;
    private final Context context;
    private final LayoutInflater mInflater;

    public VacationAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class VacationViewHolder extends RecyclerView.ViewHolder {
        private final TextView vacationItemView;
        public VacationViewHolder(@NonNull View itemView) {
            super(itemView);
            //sent to vacation_list_item
            vacationItemView = itemView.findViewById(R.id.textView2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position= getAdapterPosition();
                    final Vacation current=mVacations.get(position);
                    Intent intent = new Intent(context,VacationDetails.class);
                    intent.putExtra("id", current.getVacationID());
                    intent.putExtra("name", current.getVacationName());
                    intent.putExtra("location",current.getVacationLocation());
                    intent.putExtra("startDate", current.getVacationStartDate());
                    intent.putExtra("endDate", current.getVacationEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }
    @NonNull
    @Override
    //inflate layout
    public VacationAdapter.VacationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.vacation_list_item, parent,false);
        return new VacationViewHolder(itemView);
    }

    //What is displayed on the view
    @Override
    public void onBindViewHolder(@NonNull VacationAdapter.VacationViewHolder holder, int position) {
        if(mVacations!=null){
            //get current vacation (from click?)
            Vacation current = mVacations.get(position);
            //get vacation name
            String name = current.getVacationName();
            // set vacation name to textView2 on vacation_list_item
            holder.vacationItemView.setText(name);
        } else {
            holder.vacationItemView.setText("No Vacations Planned!");
        }
    }

    @Override
    public int getItemCount() {
        if (mVacations!=null){
            return mVacations.size();
        } else {
            return 0;
        }

    }
    public void setVacations(List<Vacation> vacations){
        mVacations = vacations;
        notifyDataSetChanged();
    }
}
