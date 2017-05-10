package me.lancer.nodiseases.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.lancer.nodiseases.R;
import me.lancer.nodiseases.mvp.base.activity.BaseActivity;
import me.lancer.nodiseases.mvp.repository.RepositoryBean;
import me.lancer.nodiseases.mvp.repository.adapter.RepositoryAdapter;
import me.lancer.nodiseases.ui.adapter.SettingAdapter;
import me.lancer.nodiseases.ui.application.mApp;
import me.lancer.nodiseases.ui.application.mParams;
import me.lancer.nodiseases.util.ContentGetterSetter;

public class SettingActivity extends BaseActivity {

    private mApp app;

    private LinearLayout llNight, llPicture, llFunc, llProblem, llFeedback, llDownload, llAboutUs;
    private Button btnLoginOut;
    private SwitchCompat scNight, scPicture;
    private BottomSheetDialog listDialog;
    private AlertDialog aboutDialog;
    private ProgressDialog progressDialog;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean isNight = false;

    private List<String> funcList = new ArrayList<>(), problemList = new ArrayList<>();
    private List<RepositoryBean> reList = new ArrayList<>();
    ContentGetterSetter contentGetterSetter = new ContentGetterSetter();
    private final String root = Environment.getExternalStorageDirectory() + "/";

    private Runnable repository = new Runnable() {
        @Override
        public void run() {
            String content = contentGetterSetter.getContentFromHtml("repository", "https://raw.githubusercontent.com/1anc3r/1anc3r-s-Android-App-List/master/AppLink.md");
            if (!content.contains("获取失败!")) {
                try {
                    List<RepositoryBean> list = new ArrayList<>();
                    JSONObject jsonObj = new JSONObject(content);
                    JSONArray jsonArr = jsonObj.getJSONArray("apps");
                    for (int i = 0; i < jsonArr.length(); i++) {
                        RepositoryBean bean = new RepositoryBean();
                        JSONObject jsonItem = jsonArr.getJSONObject(i);
                        bean.setImg(jsonItem.getString("img"));
                        bean.setName(jsonItem.getString("name"));
                        bean.setDescription(jsonItem.getString("description"));
                        bean.setDownload(jsonItem.getString("download"));
                        bean.setBlog(jsonItem.getString("blog"));
                        list.add(bean);
                    }
                    Message msg = new Message();
                    msg.what = 0;
                    msg.obj = list;
                    handler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (msg.obj != null) {
                        reList = (List<RepositoryBean>) msg.obj;
                        showRepositoryDialog(reList);
                        progressDialog.dismiss();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        initData();
    }

    private void initView() {
        initToolbar(getString(R.string.settingcn));
        llNight = (LinearLayout) findViewById(R.id.ll_night);
        llNight.setOnClickListener(vOnClickListener);
        llPicture = (LinearLayout) findViewById(R.id.ll_picture);
        llPicture.setOnClickListener(vOnClickListener);
        llFunc = (LinearLayout) findViewById(R.id.ll_func);
        llFunc.setOnClickListener(vOnClickListener);
        llProblem = (LinearLayout) findViewById(R.id.ll_problem);
        llProblem.setOnClickListener(vOnClickListener);
        llFeedback = (LinearLayout) findViewById(R.id.ll_feedback);
        llFeedback.setOnClickListener(vOnClickListener);
        llDownload = (LinearLayout) findViewById(R.id.ll_download);
        llDownload.setOnClickListener(vOnClickListener);
        llAboutUs = (LinearLayout) findViewById(R.id.ll_about_us);
        llAboutUs.setOnClickListener(vOnClickListener);
        btnLoginOut = (Button) findViewById(R.id.btn_login_out);
        btnLoginOut.setOnClickListener(vOnClickListener);
        scNight = (SwitchCompat) findViewById(R.id.sc_night);
        scPicture = (SwitchCompat) findViewById(R.id.sc_picture);
        progressDialog = new ProgressDialog(SettingActivity.this);
        progressDialog.setMessage("正在加载，请稍后...");
        progressDialog.setCancelable(false);
        showAboutDialog();
    }

    private void initData() {
        app = (mApp) getApplication();
        sharedPreferences = getSharedPreferences(getString(R.string.spf_user), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        isNight = sharedPreferences.getBoolean(mParams.ISNIGHT, false);
        scNight.setChecked(isNight);
        scNight.setClickable(false);
        scPicture.setChecked(app.isPicture());
        scPicture.setClickable(false);
        funcList.add("疾病自查 : \n" +
                "\t\t\t\t可以按照身体部位和科室分类来查询疾病，也可以点击右上角搜索按钮按照疾病名称和症状查询疾病，点击结果可以浏览疾病简介、病因、预防、病状、药品、食品、检查等");
        funcList.add("医学百科\n" +
                "\t\t\t\t了解医学相关的资讯和知识，按照诸多标签进行分类，以新闻的形式呈现");
        funcList.add("附近医院\n" +
                "\t\t\t\t以我的位置为中心，搜索方圆5公里内的医院，地图和定位由高德地图提供");
        problemList.add("遇到Bug不要憋在心里∑(っ°Д°)っ\n请发送邮件至huangfangzhi0@foxmail.com");
    }

    private View.OnClickListener vOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == llNight) {
                if (!isNight) {
                    editor.putBoolean(mParams.ISNIGHT, true);
                    editor.apply();
                    scNight.setChecked(true);
                    getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    recreate();
                } else {
                    editor.putBoolean(mParams.ISNIGHT, false);
                    editor.apply();
                    scNight.setChecked(false);
                    getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    recreate();
                }
            } else if (v == llPicture) {
                if (app.isPicture()) {
                    editor.putBoolean("is_picture", false);
                    editor.apply();
                    app.setPicture(false);
                    scPicture.setChecked(false);
                } else {
                    editor.putBoolean("is_picture", true);
                    editor.apply();
                    app.setPicture(true);
                    scPicture.setChecked(true);
                }
            }else if (v == llFunc) {
                showListDialog(1, funcList);
            } else if (v == llProblem) {
                showListDialog(2, problemList);
            } else if (v == llFeedback) {

            } else if (v == llDownload) {
                new Thread(repository).start();
                progressDialog.show();
            } else if (v == llAboutUs) {
                aboutDialog.show();
            } else if (v == btnLoginOut) {
                finish();
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent().setClass(mActivity, MainActivity.class));
            finish();
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent().setClass(SettingActivity.this, MainActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showListDialog(int type, List<String> list) {
        View listDialogView = View.inflate(mActivity, R.layout.list_dialog, null);
        TextView tvType = (TextView) listDialogView.findViewById(R.id.tv_type);
        switch (type) {
            case 1:
                tvType.setText("功能介绍");
                break;
            case 2:
                tvType.setText("常见问题");
                break;
        }
        RecyclerView rvList = (RecyclerView) listDialogView.findViewById(R.id.rv_list);
        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        RecyclerView.Adapter adapter = new SettingAdapter(list);
        rvList.setAdapter(adapter);

        listDialog = new BottomSheetDialog(mActivity);
        listDialog.setContentView(listDialogView);
        listDialog.show();
    }

    private void showAboutDialog() {
        View aboutDialogView = LayoutInflater.from(mActivity).inflate(R.layout.about_dialog, null);
        TextView tvOrganization = (TextView) aboutDialogView.findViewById(R.id.tv_organization);
        tvOrganization.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("http://www.xiyoumobile.com/");
                intent.setData(content_url);
                startActivity(intent);
            }
        });
        TextView tvBlog = (TextView) aboutDialogView.findViewById(R.id.tv_blog);
        tvBlog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("http://www.1anc3r.me");
                intent.setData(content_url);
                startActivity(intent);
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setView(aboutDialogView);
        aboutDialog = builder.create();
    }

    private void showRepositoryDialog(List<RepositoryBean> list) {
        View listDialogView = View.inflate(mActivity, R.layout.list_dialog, null);
        TextView tvType = (TextView) listDialogView.findViewById(R.id.tv_type);
        tvType.setText("我的作品");
        RecyclerView rvList = (RecyclerView) listDialogView.findViewById(R.id.rv_list);
        rvList.setItemAnimator(new DefaultItemAnimator());
        rvList.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        RecyclerView.Adapter adapter = new RepositoryAdapter(this, list);
        rvList.setAdapter(adapter);
        listDialog = new BottomSheetDialog(mActivity);
        listDialog.setContentView(listDialogView);
        listDialog.show();
    }

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
                editor.commit();
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
