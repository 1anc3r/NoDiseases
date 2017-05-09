package me.lancer.nodiseases.mvp.disease;

import java.util.List;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface IDiseasePresenter {

    void loadListSuccess(List<DiseaseBean> list);

    void loadListFailure(String log);

    void loadPlaceSuccess(List<DiseaseBean> list);

    void loadPlaceFailure(String log);

    void loadDepartmentSuccess(List<DiseaseBean> list);

    void loadDepartmentFailure(String log);

    void loadNameSuccess(DiseaseBean bean);

    void loadNameFailure(String log);

    void loadShowSuccess(DiseaseBean bean);

    void loadShowFailure(String log);
}