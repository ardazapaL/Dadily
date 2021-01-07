package com.iratsel.dailydoses.fragment;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.iratsel.dailydoses.R;
import com.iratsel.dailydoses.controllers.DairyController;
import com.iratsel.dailydoses.controllers.UserController;
import com.iratsel.dailydoses.utils.Database;
import com.iratsel.dailydoses.utils.DatabaseContents;
import com.iratsel.dailydoses.utils.DatabaseHelper;

public class EditNameDialogFragment extends DialogFragment {

    private EditText editTextdate, editTextheadline, editTextdesc;
    private Button btn_submit;
    private Database database;

    public EditNameDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static EditNameDialogFragment newInstance(String title) {
        EditNameDialogFragment frag = new EditNameDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container);

        Database database = new DatabaseHelper(getContext());
        UserController.setDatabase(database);
        DairyController.setDatabase(database);

        // Get field from view
        editTextdate = view.findViewById(R.id.field_date);
        editTextheadline = view.findViewById(R.id.field_headline);
        editTextdesc = view.findViewById(R.id.field_desc);
        btn_submit = view.findViewById(R.id.btn_submit);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);

        // Show soft keyboard automatically and request focus to field
        editTextdate.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add();
            }
        });
    }

    private boolean add() {
        String date = editTextdate.getText().toString();
        String headline = editTextheadline.getText().toString();
        String desc = editTextdesc.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);
        contentValues.put("headline", headline);
        contentValues.put("description", desc);

        long result = DairyController.getInstance().add(contentValues);
        if(result == -1) return false;
        else
            return true;
    }
}