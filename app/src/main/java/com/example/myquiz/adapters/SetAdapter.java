package com.example.myquiz.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myquiz.R;
import com.example.myquiz.models.SetClass;
import com.example.myquiz.ultis.ISetOnClick;

import java.util.List;

public class SetAdapter extends RecyclerView.Adapter<SetAdapter.SetViewHolder> {
    List<SetClass> setClassList;
    ISetOnClick setOnClick;
    public SetAdapter(ISetOnClick listener){
        this.setOnClick = listener;
    }
    public void setData(List<SetClass> setClasses){
        setClassList = setClasses;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public SetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_item_layout, parent, false);
        return new SetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SetViewHolder holder, int position) {
        SetClass setClass = setClassList.get(position);
        holder.tvSetName.setText(setClass.name.toString());
        holder.cardView.setOnClickListener(view -> {
            setOnClick.setOnclick(setClass);
        });

    }

    @Override
    public int getItemCount() {
        if(setClassList!=null){
            return setClassList.size();
        }
        return 0;
    }

    public class SetViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSetName;
        private CardView cardView;
        public SetViewHolder(@NonNull View itemView) {

            super(itemView);
            tvSetName = itemView.findViewById(R.id.tv_card_set);
            cardView = itemView.findViewById(R.id.item_card_set);


        }
    }
}
