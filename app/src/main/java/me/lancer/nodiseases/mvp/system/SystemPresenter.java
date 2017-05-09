package me.lancer.nodiseases.mvp.system;

import java.util.List;

import me.lancer.nodiseases.mvp.base.IBasePresenter;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class SystemPresenter implements IBasePresenter<ISystemView>, ISystemPresenter {

    private ISystemView view;
    private SystemModel model;

    public SystemPresenter(ISystemView view) {
        attachView(view);
        model = new SystemModel(this);
    }

    @Override
    public void attachView(ISystemView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    public void loadAll(int type){
        if (view != null) {
            view.showLoad();
            model.all(type);
        }
    }

    @Override
    public void loadAllSuccess(List<SystemBean> list) {
        if (view != null) {
            view.showAll(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadAllFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }
}
