package me.lancer.nodiseases.mvp.message;

import java.util.List;

import me.lancer.nodiseases.mvp.base.IBaseView;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface IMessageView extends IBaseView {

    void showClassify(List<MessageBean> list);

    void showList(List<MessageBean> list);

    void showNews(List<MessageBean> list);

    void showShow(MessageBean bean);

    void showSearch(List<MessageBean> list);
}
