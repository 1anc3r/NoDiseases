package me.lancer.nodiseases.mvp.message.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import net.steamcrafted.loadtoast.LoadToast;

import java.util.List;

import me.lancer.nodiseases.R;
import me.lancer.nodiseases.mvp.base.activity.PresenterActivity;
import me.lancer.nodiseases.mvp.message.IMessageView;
import me.lancer.nodiseases.mvp.message.MessageBean;
import me.lancer.nodiseases.mvp.message.MessagePresenter;
import me.lancer.nodiseases.ui.application.mParams;
import me.lancer.nodiseases.ui.view.htmltextview.HtmlHttpImageGetter;
import me.lancer.nodiseases.ui.view.htmltextview.HtmlTextView;
import me.lancer.nodiseases.util.LruImageCache;

public class MessageDetailActivity extends PresenterActivity<MessagePresenter> implements IMessageView {

    private CollapsingToolbarLayout layout;
    private NetworkImageView ivImg;
    private HtmlTextView htvContent;
    private LoadToast loadToast;

    private RequestQueue mQueue;
    private int id, type;
    private String title, img;

    private  Handler handler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    Log.e("log", (String) msg.obj);
                    loadToast.error();
                    break;
                case 3:
                    if (msg.obj != null) {
                        loadToast.success();
                        MessageBean mb = (MessageBean) msg.obj;
                        layout.setTitle(mb.getTitle());
                        if (mb.getMessage()!=null) {
                            htvContent.setHtml(mb.getMessage(), new HtmlHttpImageGetter(htvContent));
                        }
                    }
                    break;
            }
        }
    };

    private Runnable loadShow = new Runnable() {
        @Override
        public void run() {
            presenter.loadShow(type, id);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large);
        initData();
        initView();
    }

    private void initData() {
        mQueue = Volley.newRequestQueue(this);
        id = getIntent().getIntExtra("id", 0);
        title = getIntent().getStringExtra("title");
        img = getIntent().getStringExtra("img");
        type = getIntent().getIntExtra("type", 0);
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.t_large);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        layout = (CollapsingToolbarLayout) findViewById(R.id.ctl_large);
        layout.setTitle(title);
        ivImg = (NetworkImageView) findViewById(R.id.iv_img);
        ViewCompat.setTransitionName(ivImg, mParams.TRANSITION_PIC);
        LruImageCache cache = LruImageCache.instance();
        ImageLoader loader = new ImageLoader(mQueue, cache);
        ivImg.setErrorImageResId(R.mipmap.ic_pictures_no);
        ivImg.setImageUrl(img, loader);
        htvContent = (HtmlTextView) findViewById(R.id.htv_content);
        loadToast = new LoadToast(this);
        loadToast.setTranslationY(160);
        loadToast.setText("玩命加载中...");
        loadToast.show();
        new Thread(loadShow).start();
    }

    public static void startActivity(Activity activity, int id, int type, String title, String img, NetworkImageView networkImageView) {
        Intent intent = new Intent();
        intent.setClass(activity, MessageDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("type", type);
        intent.putExtra("title", title);
        intent.putExtra("img", img);
        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity, networkImageView, mParams.TRANSITION_PIC);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    protected void onDestroy() {
        ivImg.destroyDrawingCache();
        htvContent.destroyDrawingCache();
        super.onDestroy();
    }

    @Override
    protected MessagePresenter onCreatePresenter() {
        return new MessagePresenter(this);
    }

    @Override
    public void showClassify(List<MessageBean> list) {
        
    }

    @Override
    public void showList(List<MessageBean> list) {

    }

    @Override
    public void showNews(List<MessageBean> list) {

    }

    @Override
    public void showShow(MessageBean bean) {
        Message msg = new Message();
        msg.what = 3;
        msg.obj = bean;
        handler.sendMessage(msg);
    }

    @Override
    public void showSearch(List<MessageBean> list) {

    }

    @Override
    public void showMsg(String log) {
        Message msg = new Message();
        msg.what = 2;
        msg.obj = log;
        handler.sendMessage(msg);
    }

    @Override
    public void showLoad() {
        Message msg = new Message();
        msg.what = 1;
        handler.sendMessage(msg);
    }

    @Override
    public void hideLoad() {
        Message msg = new Message();
        msg.what = 0;
        handler.sendMessage(msg);
    }
}
