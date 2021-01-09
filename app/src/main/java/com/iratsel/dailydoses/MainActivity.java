package com.iratsel.dailydoses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
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
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.iratsel.dailydoses.adapter.TabAdapter;
import com.iratsel.dailydoses.controllers.UserController;
import com.iratsel.dailydoses.fragment.AddDairyFragment;
import com.iratsel.dailydoses.fragment.ListMainFragment;
import com.iratsel.dailydoses.fragment.MemoryFragment;
import com.iratsel.dailydoses.model.ListMainModel;
import com.iratsel.dailydoses.utils.Database;
import com.iratsel.dailydoses.utils.DatabaseHelper;
import com.iratsel.dailydoses.utils.Tag;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageButton btn_logout;
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
        sharedpreferences = getSharedPreferences(Tag.SP, Context.MODE_PRIVATE);

        viewPager = findViewById(R.id.view_pager_main);
        tabLayout = findViewById(R.id.tab_layout_main);
        btn_logout = findViewById(R.id.btn_logout);
        fab = findViewById(R.id.fab_add);

        /* tab icons */
        int[] tabIcons = {
                R.drawable.ic_baseline_view_list_24,
                R.drawable.ic_baseline_collections_24};

        /* add fragment to tab adapter */
        tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new ListMainFragment(), null);
        tabAdapter.addFragment(new MemoryFragment(), null);

        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);

        /* set icon tab layout */
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);

        btn_logout.setOnClickListener(v -> {
            logout();
        });
        fab.setOnClickListener(v -> {
            Intent goAddDairy = new Intent(MainActivity.this, AddDairyActivity.class);
            startActivity(goAddDairy);
            Log.d("PRESSED", "FAB IS PRESSED");
        });
    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        AddDairyFragment editNameDialogFragment = AddDairyFragment.newInstance(getString(R.string.prompt_add_dairy));
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }

    private void logout() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        sharedpreferences.edit().clear().commit();
        finish();
        startActivity(intent);
        Toast.makeText(this, "You Logged Out", Toast.LENGTH_SHORT).show();
    }
}