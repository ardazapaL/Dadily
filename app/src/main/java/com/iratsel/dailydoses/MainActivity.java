package com.iratsel.dailydoses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.iratsel.dailydoses.adapter.ListMainAdapter;
import com.iratsel.dailydoses.adapter.TabAdapter;
import com.iratsel.dailydoses.controllers.UserController;
import com.iratsel.dailydoses.fragment.EditNameDialogFragment;
import com.iratsel.dailydoses.fragment.ListMainFragment;
import com.iratsel.dailydoses.fragment.LoginFragment;
import com.iratsel.dailydoses.fragment.MemoryFragment;
import com.iratsel.dailydoses.fragment.SignupFragment;
import com.iratsel.dailydoses.model.ListMainModel;
import com.iratsel.dailydoses.utils.Database;
import com.iratsel.dailydoses.utils.DatabaseHelper;
import com.iratsel.dailydoses.utils.Tag;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btn_logout;
    private ArrayList<ListMainModel> listMain;
    private TabAdapter tabAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fab;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        Database database = new DatabaseHelper(this);
        UserController.setDatabase(database);

        viewPager = findViewById(R.id.view_pager_main);
        tabLayout = findViewById(R.id.tab_layout_main);
        fab = findViewById(R.id.fab_add);

        tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new ListMainFragment(), getString(R.string.prompt_dairy));
        tabAdapter.addFragment(new MemoryFragment(), getString(R.string.prompt_memory));
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog();
                Log.d("PRESSED", "FAB IS PRESSED");
            }
        });

        sharedpreferences = getSharedPreferences(Tag.SP, Context.MODE_PRIVATE);
    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        EditNameDialogFragment editNameDialogFragment = EditNameDialogFragment.newInstance("Some Title");
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }

    private void addData() {
        listMain = new ArrayList<>();
        listMain.add(new ListMainModel("Date","Headline", "Description"));
        listMain.add(new ListMainModel("Date","Headline", "Description"));
        listMain.add(new ListMainModel("Date","Headline", "Description"));
        listMain.add(new ListMainModel("Date","Headline", "Description"));
        listMain.add(new ListMainModel("Date","Headline", "Description"));
    }

    private void logout() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        sharedpreferences.edit().clear().commit();
        finish();
        startActivity(intent);
        Log.d("LOGOUT", "Logout berhasil" + sharedpreferences.getString("name", null));
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}