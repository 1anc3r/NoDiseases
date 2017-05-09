package me.lancer.nodiseases.mvp.location;

import java.util.List;

import me.lancer.nodiseases.mvp.base.IBasePresenter;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class LocationPresenter implements IBasePresenter<ILocationView>, ILocationPresenter {

    private ILocationView view;
    private LocaltionModel model;

    public LocationPresenter(ILocationView view) {
        attachView(view);
        model = new LocaltionModel(this);
    }

    @Override
    public void attachView(ILocationView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    public void loadList(int type){
        if (view != null) {
            view.showLoad();
            model.list(type);
        }
    }

    @Override
    public void loadListSuccess(List<LocationBean> list) {
        if (view != null) {
            view.showList(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadListFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }

    public void loadLocation(int type, double x, double y){
        if (view != null) {
            view.showLoad();
            model.location(type, x, y);
        }
    }

    @Override
    public void loadLocationSuccess(List<LocationBean> list) {
        if (view != null) {
            view.showLocation(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadLocationFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }

    public void loadShow(int type, int id){
        if (view != null) {
            view.showLoad();
            model.show(type, id);
        }
    }

    @Override
    public void loadShowSuccess(LocationBean bean) {
        if (view != null) {
            view.showShow(bean);
            view.hideLoad();
        }
    }

    @Override
    public void loadShowFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }

    public void loadName(int type, String name){
        if (view != null) {
            view.showLoad();
            model.name(type, name);
        }
    }

    @Override
    public void loadNameSuccess(LocationBean bean) {
        if (view != null) {
            view.showName(bean);
            view.hideLoad();
        }
    }

    @Override
    public void loadNameFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }
}
