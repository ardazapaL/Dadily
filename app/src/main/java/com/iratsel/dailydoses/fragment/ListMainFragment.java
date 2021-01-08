package com.iratsel.dailydoses.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.iratsel.dailydoses.MainActivity;
import com.iratsel.dailydoses.R;
import com.iratsel.dailydoses.adapter.ListMainAdapter;
import com.iratsel.dailydoses.controllers.UserController;
import com.iratsel.dailydoses.model.ListMainModel;
import com.iratsel.dailydoses.utils.Database;
import com.iratsel.dailydoses.utils.DatabaseHelper;

import java.util.ArrayList;

public class ListMainFragment extends Fragment {

    private ArrayList<ListMainModel> listMain;
    private RecyclerView recyclerView;
    private DatabaseHelper myDb;
    private ListMainAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Cursor res;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_list, container, false);

        Database database = new DatabaseHelper(getContext());
        UserController.setDatabase(database);

        myDb = new DatabaseHelper(getActivity());
        listMain = new ArrayList<>();

        //addData();
        res = myDb.getAllDairy();
        if(res.getCount() == 0) {
            // show message
            showMessage("Error","Nothing Found");
        } else {
            fetchData();
        }
        /* initialize */
        recyclerView = view.findViewById(R.id.main_recycler);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        /* recycler view */
        adapter = new ListMainAdapter(listMain);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // SetOnRefreshListener on SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                adapter.notifyDataSetChanged();
                fetchData();
                adapter = new ListMainAdapter(listMain);
                recyclerView.setAdapter(adapter);
            }
        });
        return view;
    }

    public void fetchData() {
        while(res.moveToNext()){
            String date = res.getString(1);
            String headline = res.getString(2);
            String desc = res.getString(3);

            listMain.add(new ListMainModel(date, headline, desc));
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        adapter = new ListMainAdapter(listMain);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    public void updateListView() {
        adapter = new ListMainAdapter(listMain);
        adapter.notifyDataSetChanged();
    }

    /* ALERT FUNCTION */
    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());  builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
