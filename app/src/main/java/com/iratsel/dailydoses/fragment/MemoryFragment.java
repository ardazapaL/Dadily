package com.iratsel.dailydoses.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.iratsel.dailydoses.LoginActivity;
import com.iratsel.dailydoses.R;
import com.iratsel.dailydoses.adapter.GridLayoutAdapter;
import com.iratsel.dailydoses.controllers.DairyController;
import com.iratsel.dailydoses.model.GridItemModel;
import com.iratsel.dailydoses.utils.Database;
import com.iratsel.dailydoses.utils.DatabaseHelper;
import com.iratsel.dailydoses.utils.Tag;
import com.iratsel.dailydoses.utils.Utility;

import java.util.ArrayList;

public class MemoryFragment extends Fragment {

    private GridLayoutAdapter adapter;
    private ArrayList<GridItemModel> gridList;
    private Cursor res;

    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memory, container, false);

        Database database = new DatabaseHelper(getContext());
        DairyController.setDatabase(database);

        sharedPreferences = this.getActivity().getSharedPreferences(Tag.SP, Context.MODE_PRIVATE);
        gridList = new ArrayList<>();

        Button btn_logout = view.findViewById(R.id.btn_logout);
        RecyclerView recyclerView = view.findViewById(R.id.memory_recycler);

        /* set recyclerview */
        adapter = new GridLayoutAdapter(gridList);
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        String email = sharedPreferences.getString("email", null);
        DatabaseHelper myDb = new DatabaseHelper(getActivity());
        res = myDb.getAllDairy(email);
        fetchData();
        btn_logout.setOnClickListener(v -> logout());
        return view;
    }

    private void fetchData() {
        while(res.moveToNext()){
            byte[] blob = res.getBlob(2);

            gridList.add(new GridItemModel(Utility.getPhoto(blob)));
        }
        res.close();
    }

    private void logout() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        sharedPreferences.edit().clear().commit();
        getActivity().finish();
        startActivity(intent);
        Log.d("LOGOUT", "Logout berhasil" + sharedPreferences.getString("name", null));
    }
}
