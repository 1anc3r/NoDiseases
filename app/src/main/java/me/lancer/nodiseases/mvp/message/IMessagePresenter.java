package me.lancer.nodiseases.mvp.message;

import java.util.List;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface IMessagePresenter {

    void loadClassifySuccess(List<MessageBean> list);

    void loadClassifyFailure(String log);

    void loadListSuccess(List<MessageBean> list);

    void loadListFailure(String log);

    void loadNewsSuccess(List<MessageBean> list);

    void loadNewsFailure(String log);

    void loadShowSuccess(MessageBean bean);

    void loadShowFailure(String log);

    void loadSearchSuccess(List<MessageBean> list);

    void loadSearchFailure(String log);
}