package me.lancer.nodiseases.mvp.location;

import java.util.List;

import me.lancer.nodiseases.mvp.base.IBaseView;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface ILocationView extends IBaseView {

    void showList(List<LocationBean> list);

    void showLocation(List<LocationBean> list);

    void showShow(LocationBean bean);

    void showName(LocationBean bean);
}
