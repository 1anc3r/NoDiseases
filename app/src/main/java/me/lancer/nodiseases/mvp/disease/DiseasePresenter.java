package me.lancer.nodiseases.mvp.disease;

import java.util.List;

import me.lancer.nodiseases.mvp.base.IBasePresenter;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class DiseasePresenter implements IBasePresenter<IDiseaseView>, IDiseasePresenter {

    private IDiseaseView view;
    private DiseaseModel model;

    public DiseasePresenter(IDiseaseView view) {
        attachView(view);
        model = new DiseaseModel(this);
    }

    @Override
    public void attachView(IDiseaseView view) {
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
    public void loadListSuccess(List<DiseaseBean> list) {
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

    public void loadPlace(int type, int place, int page){
        if (view != null) {
            view.showLoad();
            model.place(type, place, page);
        }
    }

    @Override
    public void loadPlaceSuccess(List<DiseaseBean> list) {
        if (view != null) {
            view.showPlace(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadPlaceFailure(String log) {
        if (log != null && log.length() > 0 && view != null) {
            view.showMsg(log);
            view.hideLoad();
        }
    }

    public void loadDepartment(int type, int department, int page){
        if (view != null) {
            view.showLoad();
            model.department(type, department, page);
        }
    }

    @Override
    public void loadDepartmentSuccess(List<DiseaseBean> list) {
        if (view != null) {
            view.showDepartment(list);
            view.hideLoad();
        }
    }

    @Override
    public void loadDepartmentFailure(String log) {
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
    public void loadNameSuccess(DiseaseBean bean) {
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

    public void loadShow(int type, int id){
        if (view != null) {
            view.showLoad();
            model.show(type, id);
        }
    }

    @Override
    public void loadShowSuccess(DiseaseBean bean) {
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
}
