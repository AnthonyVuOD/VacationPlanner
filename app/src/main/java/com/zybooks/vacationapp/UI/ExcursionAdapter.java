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
import com.zybooks.vacationapp.entities.Excursion;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {
    private List<Excursion> mExcursions;
    private final Context context;
    private final LayoutInflater mInflater;

    class ExcursionViewHolder extends RecyclerView.ViewHolder {
        private final TextView excursionItemView;
        private final TextView excursionItemView2;

        private ExcursionViewHolder(@NonNull View itemView) {
            super(itemView);
            excursionItemView = itemView.findViewById(R.id.textView3);
            excursionItemView2 = itemView.findViewById(R.id.textView4);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Excursion current = mExcursions.get(position);
                    // Sends info to next screen?
                    Intent intent = new Intent(context, ExcursionDetails.class);
                    intent.putExtra("id", current.getExcursionID());
                    intent.putExtra("name", current.getExcursionName());
                    intent.putExtra("date", current.getExcursionDate());
                    intent.putExtra("associatedVacationID", current.getVacationID());
                    context.startActivity(intent);
                }
            });
        }
    }
    public ExcursionAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context=context;
    }
    @Override
    public ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.excursion_list_item,parent, false);
        return new ExcursionViewHolder(itemView);
    }
    @Override
    // This Method shows the Excursion Name and associated vacationID to UI
    public void onBindViewHolder(@NotNull ExcursionViewHolder holder,int position){
        if(mExcursions!=null){
            Excursion current = mExcursions.get(position);
            String name = current.getExcursionName();
            String excursionDate = current.getExcursionDate();
            holder.excursionItemView.setText(name);
            holder.excursionItemView2.setText(excursionDate);
        }
        else{
            holder.excursionItemView.setText("No Part Name");
            holder.excursionItemView2.setText("No Vacation ID");
        }
    }
    public void setExcursions(List<Excursion> excursions){
        mExcursions=excursions;
        notifyDataSetChanged();
    }
    public int getItemCount(){
        if(mExcursions!=null){
            return mExcursions.size();
        } else {
            return 0;
        }
    }
}

//    private List<Excursion> mExcursions;
//    private final Context context;
//    private final LayoutInflater mInflater;
//
//    public class ExcursionViewHolder extends RecyclerView.ViewHolder {
//        private TextView excursionItemView;
//        private TextView excursionItemView2;
//        private ExcursionViewHolder(@NonNull View itemView) {
//            super(itemView);
//            excursionItemView= itemView.findViewById(R.id.textView3);
//            excursionItemView2= itemView.findViewById(R.id.textView4);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position = getAdapterPosition();
//                    final Excursion current = mExcursions.get(position);
//                    Intent intent = new Intent(context, ExcursionDetails.class);
//                    intent.putExtra("id",current.getExcursionID());
//                    intent.putExtra("name",current.getExcursionName());
//                    intent.putExtra("date",current.getExcursionDate());
//                    intent.putExtra("associated vacation",current.getVacationID());
//                    context.startActivity(intent);
//                }
//            });
//        }
//    }
//    public ExcursionAdapter(Context context) {
//        mInflater = LayoutInflater.from(context);
//        this.context = context;
//    }
//
//
//    @NonNull
//    @Override
//    //public ExcursionAdapter.ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//    public ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemView=mInflater.inflate(R.layout.excursion_list_item,parent,false);
//        return new ExcursionViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ExcursionAdapter.ExcursionViewHolder holder, int position) {
//        if(mExcursions!=null){
//            Excursion current = mExcursions.get(position);
//            String name = current.getExcursionName();
//            int excursionID = current.getExcursionID();
//            holder.excursionItemView.setText(name);
//            holder.excursionItemView2.setText(Integer.toString(excursionID));
//        }
//        else {
//            holder.excursionItemView.setText("No name");
//            holder.excursionItemView2.setText("No ID");
//        }
//    }
//    public void setExcursions(List<Excursion> excursions){
//        mExcursions = excursions;
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public int getItemCount() {
//        if (mExcursions!=null){
//            return mExcursions.size();
//        } else return 0;
//    }
//}
