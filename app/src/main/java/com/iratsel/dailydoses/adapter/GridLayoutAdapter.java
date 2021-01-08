package com.iratsel.dailydoses.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iratsel.dailydoses.R;
import com.iratsel.dailydoses.model.GridItemModel;

import java.util.ArrayList;

public class GridLayoutAdapter extends RecyclerView.Adapter<GridLayoutAdapter.GridHolder> {

    private Context context;
    private ArrayList<GridItemModel> gridItemModels;

    public GridLayoutAdapter(ArrayList<GridItemModel> gridItemModels) {
        this.gridItemModels = gridItemModels;
    }

    @Override
    public GridHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_memory,
                parent, false);
        GridHolder gridHolder = new GridHolder(layoutView);
        return gridHolder;
    }

    @Override
    public void onBindViewHolder(GridHolder holder, int position) {
        holder.imageView.setImageBitmap(gridItemModels.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return gridItemModels.size();
    }

    class GridHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public GridHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_grid);
        }
    }
}


