package com.iratsel.dailydoses;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.iratsel.dailydoses.adapter.TabLoginAdapter;
import com.iratsel.dailydoses.fragment.LoginFragment;
import com.iratsel.dailydoses.fragment.SignupFragment;

public class LoginActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }*/

        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        /* set tab adapter of viewpager */
        TabLoginAdapter tabAdapter = new TabLoginAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new LoginFragment(), getString(R.string.login));
        tabAdapter.addFragment(new SignupFragment(), getString(R.string.Signup));
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}