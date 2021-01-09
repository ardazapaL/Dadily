package com.iratsel.dailydoses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iratsel.dailydoses.model.ListMainModel;
import com.iratsel.dailydoses.utils.DatabaseHelper;
import com.iratsel.dailydoses.utils.Tag;
import com.iratsel.dailydoses.utils.Utility;

import java.util.ArrayList;

public class SingleDairyActivity extends AppCompatActivity {

    private EditText txt_headline, txt_date, txt_desc;
    private ImageView edit_img;
    private Button btn_update, btn_delete;
    private String headline, date, desc;

    private int post_id;
    private byte[] blob;
    private DatabaseHelper myDb;
    private Cursor res;
    private SharedPreferences sharedPreferences;

    ArrayList<ListMainModel> listMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_dairy);

        txt_headline = findViewById(R.id.txt_headline);
        txt_date = findViewById(R.id.txt_date);
        txt_desc = findViewById(R.id.txt_desc);
        edit_img = findViewById(R.id.edit_img);
        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);

        sharedPreferences = getSharedPreferences(Tag.SP, Context.MODE_PRIVATE);
        myDb = new DatabaseHelper(this);

        String email = sharedPreferences.getString("email", null);
        post_id = getIntent().getIntExtra("post_id", 0);
        res = myDb.getSingleDairy(post_id);

        while (res.moveToNext()) {
            blob = res.getBlob(2);
            date = res.getString(3);
            headline = res.getString(4);
            desc = res.getString(5);
        }
        res.close();

        /* set data from cursor */
        edit_img.setImageBitmap(Utility.getPhoto(blob));
        txt_headline.setText(headline);
        txt_date.setText(date);
        txt_desc.setText(desc);

        /* listener button */
        btn_update.setOnClickListener(v -> {
            // UPDATE DATA
            updateData();
        });

        btn_delete.setOnClickListener(v -> {
            // DELETE DATA
            deleteData();
        });
    }

    private void updateData() {
        String mHeadline = txt_headline.getText().toString();
        String mDate = txt_date.getText().toString();
        String mDesc = txt_desc.getText().toString();

        ContentValues content = new ContentValues();
        content.put("_id", post_id);
        content.put("headline", mHeadline);
        content.put("date", mDate);
        content.put("description", mDesc);

        boolean update = myDb.update("dairy", content);
        if(update == true){
            Toast.makeText(SingleDairyActivity.this,"Data Updated"
                    ,Toast.LENGTH_LONG).show();
            Intent intent = new Intent(SingleDairyActivity.this
                    , MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else
            Toast.makeText(SingleDairyActivity.this,"Data Failed to Update",Toast.LENGTH_LONG).show();
    }

    private void deleteData() {
        Boolean deletedRows =
                myDb.delete("dairy", post_id);
        if (deletedRows == true){
            Toast.makeText(SingleDairyActivity.this,
                    "Data Deleted", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(SingleDairyActivity.this
                    , MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else
            Toast.makeText(SingleDairyActivity.this,"Data Failed to Deleted!",Toast.LENGTH_LONG).show();
    }
}