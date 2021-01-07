package com.iratsel.dailydoses.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.iratsel.dailydoses.MainActivity;
import com.iratsel.dailydoses.R;
import com.iratsel.dailydoses.controllers.UserController;
import com.iratsel.dailydoses.utils.Database;
import com.iratsel.dailydoses.utils.DatabaseHelper;
import com.iratsel.dailydoses.utils.Tag;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SignupFragment extends Fragment {

    private UserLoginTask mAuthTask = null;
    private Button btn_signup;
    private EditText editTextEmail, editTextPass;

    SharedPreferences sharedpreferences;
    Boolean session = false;
    String id, email, name;
    Intent goLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        Database database = new DatabaseHelper(getContext());
        UserController.setDatabase(database);

        /* declare variable */
        editTextEmail = view.findViewById(R.id.input_email);
        editTextPass = view.findViewById(R.id.input_password);
        btn_signup = view.findViewById(R.id.btn_signup);

        /* intent login */
        goLogin = new Intent(getActivity(), MainActivity.class);
        goLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK); // clear activity before

        checkSession();

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                register();
            }
        });
        return view;
    }

    /* mAuthTask */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private ContentValues admin;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            admin = UserController.getInstance().getDataByEmail(mEmail);
            if (admin != null) {
                if (mPassword.equals(admin.getAsString("password"))) {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean(Tag.SESSION, true);
                    editor.putString(Tag.ID, admin.getAsString("_id"));
                    editor.putString(Tag.EMAIL, admin.getAsString("email"));
                    editor.putString(Tag.NAME, admin.getAsString("name"));
                    editor.putString(Tag.PHONE, admin.getAsString("phone"));
                    editor.commit();
                } else {
                    return false;
                }
                return true;
            }
            return false;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra(Tag.ID, id);
                intent.putExtra(Tag.EMAIL, email);
                if (admin != null) {
                    intent.putExtra(Tag.NAME, admin.getAsString("name"));
                }
                startActivity(intent);
                getActivity().finish();
            } else {
                editTextPass.setError(getString(R.string.prompt_fill_password)); //TEST
                editTextPass.requestFocus();
            }
        }
        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }

    }

    private void register() {

        String email = editTextEmail.getText().toString();
        String password = editTextPass.getText().toString();

        ContentValues content = new ContentValues();
        content.put("email", email);
        content.put("name", " "); //TEST
        content.put("password", password);
        content.put("phone", " "); //TEST
        content.put("date_added", getCurrentTime());

        int id = UserController.getInstance().register(content);
        if (id > 0) {
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private void checkSession() {
        sharedpreferences = this.getActivity().getSharedPreferences(Tag.SP, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(Tag.SESSION, false);
        id = sharedpreferences.getString(Tag.ID, null);
        email = sharedpreferences.getString(Tag.EMAIL, null);
        name = sharedpreferences.getString(Tag.NAME, null);

        if (session) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra(Tag.ID, id);
            intent.putExtra(Tag.EMAIL, email);
            intent.putExtra(Tag.NAME, name);
            getActivity().finish();
            startActivity(intent);
        }
    }

    private String getCurrentTime() {
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c);

        return formattedDate;
    }
}
