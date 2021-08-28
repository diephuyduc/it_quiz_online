package com.example.myquiz.adapters;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myquiz.R;
import com.example.myquiz.models.Category;
import com.example.myquiz.ultis.ICatOnclick;

import java.util.List;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.CatViewHolder> {
    List<Category> categoryList;
    ICatOnclick catOnclick;
    Context context;
    public CatAdapter(ICatOnclick listener, Context t){
        this.catOnclick = listener;
        this.context =t;
    }
    public void setData(List<Category> categories){
        categoryList = categories;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item_layout,parent, false);
        return new CatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, int position) {
        Category category = categoryList.get(position);
        if(category!=null){
            holder.catName.setText(category.name.toString());
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    catOnclick.catItemOnlcik(category);
                }
            });
            if(!category.img.equals("")){
                Glide.with(context).load(category.img).into(holder.imageView);
            }
        }

    }

    @Override
    public int getItemCount() {
        if(categoryList==null){
            return 0;
        }
        return categoryList.size();
    }

    public class CatViewHolder extends RecyclerView.ViewHolder{
      private TextView catName;
      private CardView cardView;
      private ImageView imageView;
      public CatViewHolder(@NonNull View itemView) {
          super(itemView);
          catName = itemView.findViewById(R.id.tv_item_cat);
          cardView = itemView.findViewById(R.id.item_card_cat);
          imageView = itemView.findViewById(R.id.img_cat);
      }
  }
}
