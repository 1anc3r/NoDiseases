package me.lancer.nodiseases.mvp.disease;

import java.util.List;

import me.lancer.nodiseases.mvp.base.IBaseView;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public interface IDiseaseView extends IBaseView {

    void showList(List<DiseaseBean> list);

    void showPlace(List<DiseaseBean> list);

    void showDepartment(List<DiseaseBean> list);

    void showName(DiseaseBean bean);

    void showShow(DiseaseBean bean);
}
