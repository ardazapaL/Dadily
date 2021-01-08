package com.iratsel.dailydoses;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.iratsel.dailydoses.controllers.DairyController;
import com.iratsel.dailydoses.utils.Database;
import com.iratsel.dailydoses.utils.DatabaseHelper;
import com.iratsel.dailydoses.utils.Tag;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddDairyActivity extends AppCompatActivity {

    private TextInputEditText text_date, text_headline, text_desc;
    private Button btn_add, btn_load;
    private Database database;
    private ImageView imageView;

    SharedPreferences sharedPreferences;
    Bitmap bitmap = null;
    byte img[];

    private static int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dairy);

        /* Database */
        database = new DatabaseHelper(this);
        DairyController.setDatabase(database);
        sharedPreferences = getSharedPreferences(Tag.SP, Context.MODE_PRIVATE);

        text_date = findViewById(R.id.field_date);
        text_headline = findViewById(R.id.field_headline);
        text_desc = findViewById(R.id.field_desc);
        btn_add = findViewById(R.id.btn_add);
        btn_load = findViewById(R.id.btn_load);
        imageView = findViewById(R.id.img_preview);

        /* start editing section */
        text_date.setText(getCurrentDate());

        /* listener load image
        *  and go to activity result */
        btn_load.setOnClickListener(v -> {
            // intent get image from gallery
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(gallery, PICK_IMAGE);
        });

        /* listener button add */
        btn_add.setOnClickListener(v -> {
            Intent goMainActivity = new Intent(AddDairyActivity.this, MainActivity.class);
            goMainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            add();
            startActivity(goMainActivity);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }*/
        if(resultCode== Activity.RESULT_OK && data !=null) {
            Uri selectedImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                img = bos.toByteArray();
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        text_date.setText(getCurrentDate());
    }

    private String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);

        return formattedDate;
    }

    private boolean add() {
        String email = sharedPreferences.getString(Tag.EMAIL, null);
        String date = text_date.getText().toString();
        String headline = text_headline.getText().toString();
        String desc = text_desc.getText().toString();

//        Bitmap bitmap = BitmapFactory.decodeByteArray(imageView, 0, imageView.l)
        ContentValues contentValues = new ContentValues();
        contentValues.put("image", img);
        contentValues.put("email", email);
        contentValues.put("date", date);
        contentValues.put("headline", headline);
        contentValues.put("description", desc);

        long result = DairyController.getInstance().add(contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
}