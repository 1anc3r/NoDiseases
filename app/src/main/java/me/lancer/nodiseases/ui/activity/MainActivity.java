package me.lancer.nodiseases.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import me.lancer.nodiseases.R;
import me.lancer.nodiseases.mvp.base.activity.BaseActivity;
import me.lancer.nodiseases.mvp.disease.fragment.DiseaseFragment;
import me.lancer.nodiseases.mvp.location.fragment.LocationFragment;
import me.lancer.nodiseases.mvp.message.fragment.MessageFragment;
import me.lancer.nodiseases.mvp.system.fragment.SystemFragment;
import me.lancer.nodiseases.ui.application.mApp;
import me.lancer.nodiseases.ui.application.mParams;
import me.lancer.nodiseases.ui.view.CircleImageView;

public class MainActivity extends BaseActivity {

    private mApp app = new mApp();

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private Fragment currentFragment;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private int currentIndex;
    private long exitTime;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = (mApp) this.getApplication();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        initNavigationViewHeader();
        initNavigationViewBottom();
        initFragment(savedInstanceState);
        if (app.isFirst()){
            showPictureDialog();
        }
    }

    private void initNavigationViewBottom() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void initFragment(Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        currentFragment = new SystemFragment();
        bundle.putInt(getString(R.string.index), 0);
        currentFragment.setArguments(bundle);
        switchContent(currentFragment);
    }

    public void initDrawer(Toolbar toolbar) {
        if (toolbar != null) {
            actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {
                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                }
            };
            actionBarDrawerToggle.syncState();
            drawerLayout.addDrawerListener(actionBarDrawerToggle);
        }
    }

    private void initNavigationViewHeader() {
        navigationView = (NavigationView) findViewById(R.id.nv_main);
        View view = navigationView.inflateHeaderView(R.layout.drawer_header);
        CircleImageView civHead = (CircleImageView) view.findViewById(R.id.civ_head);
        TextView tvHead = (TextView) view.findViewById(R.id.tv_head);
        navigationView.setNavigationItemSelectedListener(new NavigationItemSelected());
    }

    class NavigationItemSelected implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            Bundle bundle = new Bundle();
            drawerLayout.closeDrawers();
            switch (menuItem.getItemId()) {
                case R.id.navigation_item_1:
                    bottomNavigationView.setSelectedItemId(R.id.navigation_home);
                    currentIndex = 0;
                    menuItem.setChecked(true);
                    currentFragment = new SystemFragment();
                    bundle.putInt(getString(R.string.index), currentIndex);
                    currentFragment.setArguments(bundle);
                    switchContent(currentFragment);
                    return true;
                case R.id.navigation_item_2:
                    bottomNavigationView.setSelectedItemId(R.id.navigation_home);
                    currentIndex = 0;
                    menuItem.setChecked(true);
                    currentFragment = new DiseaseFragment();
                    bundle.putInt(getString(R.string.index), currentIndex);
                    currentFragment.setArguments(bundle);
                    switchContent(currentFragment);
                    return true;
                case R.id.navigation_item_3:
                    bottomNavigationView.setSelectedItemId(R.id.navigation_dashboard);
                    currentIndex = 0;
                    menuItem.setChecked(true);
                    currentFragment = new MessageFragment();
                    bundle.putInt(getString(R.string.index), currentIndex);
                    currentFragment.setArguments(bundle);
                    switchContent(currentFragment);
                    return true;
                case R.id.navigation_item_4:
                    bottomNavigationView.setSelectedItemId(R.id.navigation_notifications);
                    currentIndex = 0;
                    menuItem.setChecked(true);
                    currentFragment = new LocationFragment();
                    bundle.putInt(getString(R.string.index), currentIndex);
                    currentFragment.setArguments(bundle);
                    switchContent(currentFragment);
                    return true;
                case R.id.navigation_setting:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent().setClass(MainActivity.this, SettingActivity.class));
                            finish();
                        }
                    }, 180);
                    return true;
                default:
                    return true;
            }
        }
    }

    public void switchContent(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_main, fragment).commit();
        invalidateOptionsMenu();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(mParams.CURRENT_INDEX, currentIndex);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_bottom_sheet_dialog:
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                showToast(this, "再按一次退出应用");
                exitTime = System.currentTimeMillis();
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Bundle bundle = new Bundle();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    currentFragment = new SystemFragment();
                    bundle.putInt(getString(R.string.index), 0);
                    currentFragment.setArguments(bundle);
                    switchContent(currentFragment);
                    return true;
                case R.id.navigation_dashboard:
                    currentFragment = new MessageFragment();
                    bundle.putInt(getString(R.string.index), 0);
                    currentFragment.setArguments(bundle);
                    switchContent(currentFragment);
                    return true;
                case R.id.navigation_notifications:
                    currentFragment = new LocationFragment();
                    bundle.putInt(getString(R.string.index), 0);
                    currentFragment.setArguments(bundle);
                    switchContent(currentFragment);
                    return true;
            }
            return false;
        }

    };

    private void showPictureDialog() {
        AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("温馨提醒");
        builder.setMessage("本应用中某些疾病图片可能会让您感到不适，您可以选择是否加载这些图片(您也可以在设置中重新选择)");
        builder.setNegativeButton("不加载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sharedPreferences = getSharedPreferences(getString(R.string.spf_user), Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putBoolean("is_picture", false);
                editor.putBoolean("is_first", false);
                editor.apply();
                app.setPicture(false);
                app.setFirst(false);
            }
        });
        builder.setPositiveButton("加载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sharedPreferences = getSharedPreferences(getString(R.string.spf_user), Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putBoolean("is_picture", true);
                editor.putBoolean("is_first", false);
                editor.commit();
                app.setPicture(true);
                app.setFirst(false);
            }
        });
        builder.show();
    }
}
