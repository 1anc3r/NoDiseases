package me.lancer.nodiseases.mvp.system;

import java.util.List;

import me.lancer.nodiseases.mvp.base.IBaseView;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface ISystemView extends IBaseView {

    void showAll(List<SystemBean> list);
}
