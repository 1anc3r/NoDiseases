package me.lancer.nodiseases.mvp.location;

import java.util.List;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface ILocationPresenter {

    void loadListSuccess(List<LocationBean> list);

    void loadListFailure(String log);

    void loadLocationSuccess(List<LocationBean> list);

    void loadLocationFailure(String log);

    void loadShowSuccess(LocationBean bean);

    void loadShowFailure(String log);

    void loadNameSuccess(LocationBean bean);

    void loadNameFailure(String log);
}