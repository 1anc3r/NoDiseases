package me.lancer.nodiseases.mvp.system;

import java.util.List;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface ISystemPresenter {

    void loadAllSuccess(List<SystemBean> list);

    void loadAllFailure(String log);
}