package com.iratsel.dailydoses.model;

import android.graphics.Bitmap;

public class GridItemModel {
    private Bitmap image;

    public GridItemModel(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
