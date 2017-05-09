package me.lancer.nodiseases.mvp.disease;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.lancer.nodiseases.util.ContentGetterSetter;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class DiseaseModel {

    IDiseasePresenter presenter;

    ContentGetterSetter contentGetterSetter = new ContentGetterSetter();
    String url = "http://www.tngou.net/api/";
    String dList = url + "disease/list";
    String dPlace = url + "disease/place?id=";
    String dDepartment = url + "disease/department?id=";
    String dName = url + "disease/name?name=";
    String dShow = url + "disease/show?id=";
    String sList = url + "symptom/list";
    String sPlace = url + "symptom/place?id=";
    String sDepartment = url + "symptom/department?id=";
    String sName = url + "symptom/name?name=";
    String sShow = url + "symptom/show?id=";
    String img = "http://tnfs.tngou.net/image/";
    String pager = "&page=";

    public DiseaseModel(IDiseasePresenter presenter) {
        this.presenter = presenter;
    }

    public void list(int type) {
        String url = "";
        if (type == 0) {
            url = dList;
        } else if (type == 1) {
            url = sList;
        }
        String content = contentGetterSetter.getContentFromHtml("Disease.list", url);
        List<DiseaseBean> list;
        if (!content.contains("获取失败!")) {
            list = getListFromContent(content);
            presenter.loadListSuccess(list);
        } else {
            presenter.loadListFailure(content);
            Log.e("Disease.list", content);
        }
    }

    public void place(int type, int place, int page) {
        String url = "";
        if (type == 0) {
            url = dPlace;
        } else if (type == 1) {
            url = sPlace;
        }
        String content = contentGetterSetter.getContentFromHtml("Disease.place", url + place + pager + page);
        List<DiseaseBean> list;
        if (!content.contains("获取失败!")) {
            list = getPlaceFromContent(content);
            presenter.loadPlaceSuccess(list);
        } else {
            presenter.loadPlaceFailure(content);
            Log.e("Disease.place", content);
        }
    }

    public void department(int type, int department, int page) {
        String url = "";
        if (type == 0) {
            url = dDepartment;
        } else if (type == 1) {
            url = sDepartment;
        }
        String content = contentGetterSetter.getContentFromHtml("Disease.department", url + department + pager + page);
        List<DiseaseBean> list;
        if (!content.contains("获取失败!")) {
            list = getDepartmentFromContent(content);
            presenter.loadDepartmentSuccess(list);
        } else {
            presenter.loadDepartmentFailure(content);
            Log.e("Disease.department", content);
        }
    }

    public void name(int type, String name) {
        String url = "";
        if (type == 0) {
            url = dName;
        } else if (type == 1) {
            url = sName;
        }
        Log.e("url", url + name);
        String content = contentGetterSetter.getContentFromHtml("Disease.name", url + name);
        DiseaseBean bean;
        if (!content.contains("获取失败!")) {
            bean = getNameFromContent(content);
            presenter.loadNameSuccess(bean);
        } else {
            presenter.loadListFailure(content);
            Log.e("Disease.name", content);
        }
    }

    public void show(int type, int id) {
        String url = "";
        if (type == 0) {
            url = dShow;
        } else if (type == 1) {
            url = sShow;
        }
        String content = contentGetterSetter.getContentFromHtml("Disease.show", url + id);
        DiseaseBean bean;
        if (!content.contains("获取失败!")) {
            bean = getShowFromContent(content);
            presenter.loadShowSuccess(bean);
        } else {
            presenter.loadShowFailure(content);
            Log.e("Disease.show", content);
        }
    }

    private List<DiseaseBean> getListFromContent(String content) {
        try {
            List<DiseaseBean> list = new ArrayList<>();
            JSONObject jsonObj = new JSONObject(content);
            JSONArray jsonArr = jsonObj.getJSONArray("list");
            for (int i = 0; i < jsonArr.length(); i++) {
                DiseaseBean bean = new DiseaseBean();
                JSONObject jsonItem = jsonArr.getJSONObject(i);
                bean.setId(jsonItem.getInt("id"));
                bean.setType(0);
                bean.setKeywords(jsonItem.getString("keywords"));
                bean.setName(jsonItem.getString("name"));
//                bean.setDescription(jsonItem.getString("description"));
                bean.setImg(img + jsonItem.getString("img"));
//                bean.setMessage(jsonItem.getString("message"));
//                bean.setDisease(jsonItem.getString("disease"));
//                bean.setDiseasetext(jsonItem.getString("diseasetext"));
//                bean.setSymptom(jsonItem.getString("symptom"));
//                bean.setSymptomtext(jsonItem.getString("symptomtext"));
//                bean.setFood(jsonItem.getString("food"));
//                bean.setFoodtext(jsonItem.getString("foodtext"));
//                bean.setChecks(jsonItem.getString("checks"));
//                bean.setChecktext(jsonItem.getString("checktext"));
//                bean.setDrug(jsonItem.getString("drug"));
//                bean.setDrugtext(jsonItem.getString("drugtext"));
//                bean.setCausetext(jsonItem.getString("causetext"));
//                bean.setCaretext(jsonItem.getString("caretext"));
                bean.setDepartment(jsonItem.getString("department"));
                bean.setPlace(jsonItem.getString("place"));
                list.add(bean);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<DiseaseBean> getPlaceFromContent(String content) {
        try {
            List<DiseaseBean> list = new ArrayList<>();
            JSONObject jsonObj = new JSONObject(content);
            JSONArray jsonArr = jsonObj.getJSONArray("list");
            for (int i = 0; i < jsonArr.length(); i++) {
                DiseaseBean bean = new DiseaseBean();
                JSONObject jsonItem = jsonArr.getJSONObject(i);
                bean.setId(jsonItem.getInt("id"));
                bean.setType(0);
                bean.setKeywords(jsonItem.getString("keywords"));
                bean.setName(jsonItem.getString("name"));
//                bean.setDescription(jsonItem.getString("description"));
                bean.setImg(img + jsonItem.getString("img"));
//                bean.setMessage(jsonItem.getString("message"));
//                bean.setDisease(jsonItem.getString("disease"));
//                bean.setDiseasetext(jsonItem.getString("diseasetext"));
//                bean.setSymptom(jsonItem.getString("symptom"));
//                bean.setSymptomtext(jsonItem.getString("symptomtext"));
//                bean.setFood(jsonItem.getString("food"));
//                bean.setFoodtext(jsonItem.getString("foodtext"));
//                bean.setChecks(jsonItem.getString("checks"));
//                bean.setChecktext(jsonItem.getString("checktext"));
//                bean.setDrug(jsonItem.getString("drug"));
//                bean.setDrugtext(jsonItem.getString("drugtext"));
//                bean.setCausetext(jsonItem.getString("causetext"));
//                bean.setCaretext(jsonItem.getString("caretext"));
                bean.setDepartment(jsonItem.getString("department"));
                bean.setPlace(jsonItem.getString("place"));
                list.add(bean);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<DiseaseBean> getDepartmentFromContent(String content) {
        try {
            List<DiseaseBean> list = new ArrayList<>();
            JSONObject jsonObj = new JSONObject(content);
            JSONArray jsonArr = jsonObj.getJSONArray("list");
            for (int i = 0; i < jsonArr.length(); i++) {
                DiseaseBean bean = new DiseaseBean();
                JSONObject jsonItem = jsonArr.getJSONObject(i);
                bean.setId(jsonItem.getInt("id"));
                bean.setType(0);
                bean.setKeywords(jsonItem.getString("keywords"));
                bean.setName(jsonItem.getString("name"));
//                bean.setDescription(jsonItem.getString("description"));
                bean.setImg(img + jsonItem.getString("img"));
//                bean.setMessage(jsonItem.getString("message"));
//                bean.setDisease(jsonItem.getString("disease"));
//                bean.setDiseasetext(jsonItem.getString("diseasetext"));
//                bean.setSymptom(jsonItem.getString("symptom"));
//                bean.setSymptomtext(jsonItem.getString("symptomtext"));
//                bean.setFood(jsonItem.getString("food"));
//                bean.setFoodtext(jsonItem.getString("foodtext"));
//                bean.setChecks(jsonItem.getString("checks"));
//                bean.setChecktext(jsonItem.getString("checktext"));
//                bean.setDrug(jsonItem.getString("drug"));
//                bean.setDrugtext(jsonItem.getString("drugtext"));
//                bean.setCausetext(jsonItem.getString("causetext"));
//                bean.setCaretext(jsonItem.getString("caretext"));
                bean.setDepartment(jsonItem.getString("department"));
                bean.setPlace(jsonItem.getString("place"));
                list.add(bean);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private DiseaseBean getNameFromContent(String content) {
        try {
            DiseaseBean bean = new DiseaseBean();
            JSONObject jsonItem = new JSONObject(content);
            bean.setId(jsonItem.getInt("id"));
            bean.setType(0);
            bean.setKeywords(jsonItem.getString("keywords"));
            bean.setName(jsonItem.getString("name"));
//                bean.setDescription(jsonItem.getString("description"));
            bean.setImg(img + jsonItem.getString("img"));
//                bean.setMessage(jsonItem.getString("message"));
//                bean.setDisease(jsonItem.getString("disease"));
//                bean.setDiseasetext(jsonItem.getString("diseasetext"));
//                bean.setSymptom(jsonItem.getString("symptom"));
//                bean.setSymptomtext(jsonItem.getString("symptomtext"));
//                bean.setFood(jsonItem.getString("food"));
//                bean.setFoodtext(jsonItem.getString("foodtext"));
//                bean.setChecks(jsonItem.getString("checks"));
//                bean.setChecktext(jsonItem.getString("checktext"));
//                bean.setDrug(jsonItem.getString("drug"));
//                bean.setDrugtext(jsonItem.getString("drugtext"));
//                bean.setCausetext(jsonItem.getString("causetext"));
//                bean.setCaretext(jsonItem.getString("caretext"));
            bean.setDepartment(jsonItem.getString("department"));
            bean.setPlace(jsonItem.getString("place"));
            return bean;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private DiseaseBean getShowFromContent(String content) {
        try {
            DiseaseBean bean = new DiseaseBean();
            JSONObject jsonItem = new JSONObject(content);
            bean.setId(jsonItem.getInt("id"));
            bean.setType(0);
            bean.setKeywords(jsonItem.getString("keywords"));
            bean.setName(jsonItem.getString("name"));
            bean.setDescription(jsonItem.getString("description"));
            bean.setImg(img + jsonItem.getString("img"));
            bean.setMessage(jsonItem.getString("message").replace("。 　　", "。<br> 　　"));
            bean.setDisease(jsonItem.getString("disease"));
            bean.setDiseasetext(jsonItem.getString("diseasetext"));
            bean.setSymptom(jsonItem.getString("symptom"));
            bean.setSymptomtext(jsonItem.getString("symptomtext"));
            bean.setFood(jsonItem.getString("food"));
            bean.setFoodtext(jsonItem.getString("foodtext"));
            bean.setChecks(jsonItem.getString("checks"));
            bean.setChecktext(jsonItem.getString("checktext"));
            bean.setDrug(jsonItem.getString("drug"));
            bean.setDrugtext(jsonItem.getString("drugtext"));
            bean.setCausetext(jsonItem.getString("causetext"));
            bean.setCaretext(jsonItem.getString("caretext"));
            bean.setDepartment(jsonItem.getString("department"));
            bean.setPlace(jsonItem.getString("place"));
            return bean;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
