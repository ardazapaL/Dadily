package com.iratsel.dailydoses.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.iratsel.dailydoses.LoginActivity;
import com.iratsel.dailydoses.R;
import com.iratsel.dailydoses.utils.Tag;

public class MemoryFragment extends Fragment {

    private Button btn_logout;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memory, container, false);

        sharedPreferences = this.getActivity().getSharedPreferences(Tag.SP, Context.MODE_PRIVATE);

        btn_logout = view.findViewById(R.id.btn_logout);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logout();
            }
        });
        return view;
    }

    private void logout() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        sharedPreferences.edit().clear().commit();
        getActivity().finish();
        startActivity(intent);
        Log.d("LOGOUT", "Logout berhasil" + sharedPreferences.getString("name", null));
    }
}
