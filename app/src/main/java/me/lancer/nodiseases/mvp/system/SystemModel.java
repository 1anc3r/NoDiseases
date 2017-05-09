package me.lancer.nodiseases.mvp.system;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.lancer.nodiseases.util.ContentGetterSetter;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class SystemModel {

    ISystemPresenter presenter;

    ContentGetterSetter contentGetterSetter = new ContentGetterSetter();
    String url = "http://www.tngou.net/api/";
    String dAll = url + "department/all";
    String pAll = url + "place/all";
    String img = "http://tnfs.tngou.net/image/";

    public SystemModel(ISystemPresenter presenter) {
        this.presenter = presenter;
    }

    public void all(int type) {
        String url = "";
        if (type == 1) {
            url = dAll;
        } else if (type == 0) {
            url = pAll;
        }
        String content = contentGetterSetter.getContentFromHtml("System.all", url);
        List<SystemBean> list;
        if (!content.contains("获取失败!")) {
            list = getAllFromContent(type, content);
            presenter.loadAllSuccess(list);
        } else {
            presenter.loadAllFailure(content);
            Log.e("System.all", content);
        }
    }

    private List<SystemBean> getAllFromContent(int type, String content) {
        try {
            List<SystemBean> list = new ArrayList<>();
            JSONObject jsonObj = new JSONObject(content);
            JSONArray jsonArr = jsonObj.getJSONArray("tngou");
            for (int i = 0; i < jsonArr.length(); i++) {
                SystemBean bean = new SystemBean();
                JSONObject jsonItem = jsonArr.getJSONObject(i);
                bean.setId(jsonItem.getInt("id"));
                bean.setKeywords(jsonItem.getString("keywords"));
                bean.setName(jsonItem.getString("name"));
                bean.setTitle(jsonItem.getString("title"));
                bean.setDescription(jsonItem.getString("description"));
                if (type == 1) {
                    bean.setDepartment(jsonItem.getInt("department"));
                    if (jsonItem.has("departments")) {
                        List<SystemBean> listDepartments = new ArrayList<>();
                        JSONArray jsonDepartments = jsonItem.getJSONArray("departments");
                        for (int j = 0; j < jsonDepartments.length(); j++) {
                            SystemBean itemDepartment = new SystemBean();
                            JSONObject jsonDepartment = jsonDepartments.getJSONObject(j);
                            itemDepartment.setId(jsonDepartment.getInt("id"));
                            itemDepartment.setKeywords(jsonDepartment.getString("keywords"));
                            itemDepartment.setName(jsonDepartment.getString("name"));
                            itemDepartment.setTitle(jsonDepartment.getString("title"));
                            itemDepartment.setDescription(jsonDepartment.getString("description"));
                            itemDepartment.setDepartment(jsonDepartment.getInt("department"));
                            listDepartments.add(itemDepartment);
                        }
                        bean.setList(listDepartments);
                    }
                } else if (type == 0) {
                    bean.setPlace(jsonItem.getInt("place"));
                    if (jsonItem.has("places")) {
                        List<SystemBean> listPlaces = new ArrayList<>();
                        JSONArray jsonPlaces = jsonItem.getJSONArray("places");
                        for (int j = 0; j < jsonPlaces.length(); j++) {
                            SystemBean itemPlace = new SystemBean();
                            JSONObject jsonPlace = jsonPlaces.getJSONObject(j);
                            itemPlace.setId(jsonPlace.getInt("id"));
                            itemPlace.setKeywords(jsonPlace.getString("keywords"));
                            itemPlace.setName(jsonPlace.getString("name"));
                            itemPlace.setTitle(jsonPlace.getString("title"));
                            itemPlace.setDescription(jsonPlace.getString("description"));
                            itemPlace.setPlace(jsonPlace.getInt("place"));
                            listPlaces.add(itemPlace);
                        }
                        bean.setList(listPlaces);
                    }
                }
                list.add(bean);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
