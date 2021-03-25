package com.priyanka.urbanfresh.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.priyanka.urbanfresh.R;
import com.priyanka.urbanfresh.model.datamodel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.myviewholder> {
    List<datamodel> dataholder;

   Context mContext;

    public ExploreAdapter( Context context,List<datamodel> dataholder) {
        this.dataholder = dataholder;
        mContext = context;
    }

    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_row_layout,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
     //   holder.imageCategory.setImageResource(dataholder.get(position).getImage());
        holder.nameCategory.setText(dataholder.get(position).getName());
        Glide.with(mContext).load(dataholder.get(position).getImage()).into(holder.imageCategory);
       // Glide.with(mContext).load(Banner).into(mImageView);
       // Glide.with(mContext).load(Banner).into(mImageView);
    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }

    class myviewholder extends RecyclerView.ViewHolder {

            ImageView imageCategory;
            TextView nameCategory;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
imageCategory=itemView.findViewById(R.id.category);
nameCategory=itemView.findViewById(R.id.namecategory);


        }
    }
}
