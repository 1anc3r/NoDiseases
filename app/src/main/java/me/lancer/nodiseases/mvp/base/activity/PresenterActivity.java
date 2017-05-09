package me.lancer.nodiseases.mvp.base.activity;

import android.os.Bundle;

import me.lancer.nodiseases.mvp.base.IBasePresenter;

/**
 * Created by HuangFangzhi on 2016/12/13.
 */

public abstract class PresenterActivity<P extends IBasePresenter> extends BaseActivity {

    protected P presenter;

    protected abstract P onCreatePresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        presenter = onCreatePresenter();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }
}
