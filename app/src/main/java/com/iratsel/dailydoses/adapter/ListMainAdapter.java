package com.iratsel.dailydoses.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iratsel.dailydoses.R;
import com.iratsel.dailydoses.model.ListMainModel;

import java.util.ArrayList;
import java.util.logging.FileHandler;

public class ListMainAdapter extends RecyclerView.Adapter<ListMainAdapter.ListMainViewHolder> {

    private ArrayList<ListMainModel> listMain;

    public ListMainAdapter(ArrayList<ListMainModel> listMain) {
        this.listMain = listMain;
    }

    @NonNull
    @Override
    public ListMainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_list_main, parent, false);
        return new ListMainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListMainViewHolder holder, int position) {
//        Glide.with(holder.imageView.getContext()).load(R.drawable.ic_splash);
        holder.imageView.setImageBitmap(listMain.get(position).getImage());
        holder.headline.setText(listMain.get(position).getHeadline());
        holder.desc.setText(listMain.get(position).getDescription());
        holder.date.setText(listMain.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return (listMain != null) ? listMain.size() : 0;
    }

    public class ListMainViewHolder extends RecyclerView.ViewHolder {

        private TextView headline, desc, date;
        private ImageView imageView;

        public ListMainViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.rc_image);
            headline = itemView.findViewById(R.id.txt_headline);
            desc = itemView.findViewById(R.id.txt_desc);
            date = itemView.findViewById(R.id.txt_date);
        }
    }
}
