package com.iratsel.dailydoses.fragment;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.iratsel.dailydoses.LoginActivity;
import com.iratsel.dailydoses.MainActivity;
import com.iratsel.dailydoses.R;
import com.iratsel.dailydoses.controllers.UserController;
import com.iratsel.dailydoses.utils.Database;
import com.iratsel.dailydoses.utils.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LoginFragment extends Fragment {

    private UserLoginTask mAuthTask = null;
    private EditText editTextEmail, editTextPass;
    private Button buttonLogin;

    /* TAG */
    public final static String FIREBASE = "FIREBASE";
    public final static String TAG_ID = "id";
    public final static String TAG_EMAIL = "email";
    public final static String TAG_NAME = "name";
    public final static String TAG_PHONE = "phone";
    public final static String TAG_PASSWORD = "password";
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    SharedPreferences sharedpreferences;
    Boolean session = false;
    String id, email, name;
    Intent goLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Database database = new DatabaseHelper(getContext());
        UserController.setDatabase(database);

        /* declare variable */
        editTextEmail = view.findViewById(R.id.input_email);
        editTextPass = view.findViewById(R.id.input_password);
        buttonLogin = view.findViewById(R.id.btn_login);

        /* intent login */
        goLogin = new Intent(getActivity(), MainActivity.class);
        goLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK); // clear activity before

        checkSession();

        /* listener */
        editTextPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                    login();
                    return true;
                }
                return false;
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
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
                    editor.putBoolean(session_status, true);
                    editor.putString(TAG_ID, admin.getAsString("_id"));
                    editor.putString(TAG_EMAIL, admin.getAsString("email"));
                    editor.putString(TAG_NAME, admin.getAsString("name"));
                    editor.putString(TAG_PHONE, admin.getAsString("phone"));
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
                intent.putExtra(TAG_ID, id);
                intent.putExtra(TAG_EMAIL, email);
                if (admin != null) {
                    intent.putExtra(TAG_NAME, admin.getAsString("name"));
                }
                getActivity().finish();
                startActivity(intent);
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

    private void checkSession() {

        sharedpreferences = this.getActivity().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        id = sharedpreferences.getString(TAG_ID, null);
        email = sharedpreferences.getString(TAG_EMAIL, null);
        name = sharedpreferences.getString(TAG_NAME, null);

        if (session) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra(TAG_ID, id);
            intent.putExtra(TAG_EMAIL, email);
            intent.putExtra(TAG_NAME, name);
            startActivity(intent);
        }
    }

    private void login() {

        String email = editTextEmail.getText().toString();
        String password = editTextPass.getText().toString();

        /* tester account */
        /*if (email.equals("admin") &&
                password.equals("admin")) {
            startActivity(goLogin);
            return;
        }*/

        /* if email text is empty */
        if (email.isEmpty()) {
            editTextEmail.setError(getString(R.string.empty_field));
            return;
        }
        /* if pass text is empty */
        if (password.isEmpty()) {
            editTextPass.setError(getString(R.string.empty_field));
            return;
        }

        mAuthTask = new UserLoginTask(email, password);
        mAuthTask.execute((Void) null);
    }

}
