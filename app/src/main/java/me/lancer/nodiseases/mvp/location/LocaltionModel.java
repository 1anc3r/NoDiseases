package me.lancer.nodiseases.mvp.location;

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

public class LocaltionModel {

    ILocationPresenter presenter;

    ContentGetterSetter contentGetterSetter = new ContentGetterSetter();
    String url = "http://www.tngou.net/api/";
    String hList = url + "store/list";
    String sList = url + "store/list";
    String hLocation = url + "hospital/location?";
    String sLocation = url + "store/location?";
    String hShow = url + "hospital/show?id=";
    String sShow = url + "store/show?id=";
    String hName = url + "hospital/name?name=";
    String sName = url + "store/name?name=";
    String img = "http://tnfs.tngou.net/image/";

    public LocaltionModel(ILocationPresenter presenter) {
        this.presenter = presenter;
    }

    public void list(int type) {
        String url = "";
        if (type == 0) {
            url = hList;
        } else if (type == 1) {
            url = sList;
        }
        String content = contentGetterSetter.getContentFromHtml("Location.list", url);
        List<LocationBean> list;
        if (!content.contains("获取失败!")) {
            list = getListFromContent(type, content);
            presenter.loadListSuccess(list);
        } else {
            presenter.loadListFailure(content);
            Log.e("Location.list", content);
        }
    }

    public void location(int type, double x, double y) {
        String url = "";
        if (type == 0) {
            url = hLocation;
        } else if (type == 1) {
            url = sLocation;
        }
        url = url + "x=" + x + "&y=" + y;
        String content = contentGetterSetter.getContentFromHtml("Location.location", url);
        List<LocationBean> list;
        if (!content.contains("获取失败!")) {
            list = getLocationFromContent(type, content);
            presenter.loadLocationSuccess(list);
        } else {
            presenter.loadLocationFailure(content);
            Log.e("Location.location", content);
        }
    }

    public void show(int type, int id) {
        String url = "";
        if (type == 0) {
            url = hShow;
        } else if (type == 1) {
            url = sShow;
        }
        String content = contentGetterSetter.getContentFromHtml("Location.show", url + id);
        LocationBean bean;
        if (!content.contains("获取失败!")) {
            bean = getShowFromContent(type, content);
            presenter.loadShowSuccess(bean);
        } else {
            presenter.loadShowFailure(content);
            Log.e("Location.show", content);
        }
    }

    public void name(int type, String name) {
        String url = "";
        if (type == 0) {
            url = hName;
        } else if (type == 1) {
            url = sName;
        }
        String content = contentGetterSetter.getContentFromHtml("Location.name", url + name);
        LocationBean bean;
        if (!content.contains("获取失败!")) {
            bean = getNameFromContent(type, content);
            presenter.loadNameSuccess(bean);
        } else {
            presenter.loadNameFailure(content);
            Log.e("Location.name", content);
        }
    }

    private List<LocationBean> getListFromContent(int type, String content) {
        try {
            List<LocationBean> list = new ArrayList<>();
            JSONObject jsonObj = new JSONObject(content);
            JSONArray jsonArr = jsonObj.getJSONArray("tngou");
            for (int i = 0; i < jsonArr.length(); i++) {
                LocationBean bean = new LocationBean();
                JSONObject jsonItem = jsonArr.getJSONObject(i);
                bean.setId(jsonItem.getInt("id"));
                bean.setX(jsonItem.getDouble("x"));
                bean.setY(jsonItem.getDouble("y"));
                bean.setType(0);
                bean.setName(jsonItem.getString("name"));
                bean.setAddress(jsonItem.getString("address"));
                bean.setImg(img + jsonItem.getString("img"));
//                bean.setMessage(jsonItem.getString("message"));
                bean.setTel(jsonItem.getString("tel"));
                bean.setZip(jsonItem.getString("zipcode"));
                bean.setUrl(jsonItem.getString("url"));
                if (type == 0) {
                    bean.setGobus(jsonItem.getString("gobus"));
                    bean.setLevel(jsonItem.getString("level"));
                } else if (type == 1) {
                    bean.setLevel(jsonItem.getString("type"));
                }
                list.add(bean);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<LocationBean> getLocationFromContent(int type, String content) {
        try {
            List<LocationBean> list = new ArrayList<>();
            JSONObject jsonObj = new JSONObject(content);
            JSONArray jsonArr = jsonObj.getJSONArray("tngou");
            for (int i = 0; i < jsonArr.length(); i++) {
                LocationBean bean = new LocationBean();
                JSONObject jsonItem = jsonArr.getJSONObject(i);
                bean.setId(jsonItem.getInt("id"));
                bean.setX(jsonItem.getDouble("x"));
                bean.setY(jsonItem.getDouble("y"));
                bean.setType(0);
                bean.setName(jsonItem.getString("name"));
                bean.setAddress(jsonItem.getString("address"));
                list.add(bean);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private LocationBean getShowFromContent(int type, String content) {
        try {
            LocationBean bean = new LocationBean();
            JSONObject jsonItem = new JSONObject(content);
            bean.setId(jsonItem.getInt("id"));
            bean.setX(jsonItem.getDouble("x"));
            bean.setY(jsonItem.getDouble("y"));
            bean.setType(0);
            bean.setName(jsonItem.getString("name"));
            bean.setAddress(jsonItem.getString("address"));
            bean.setImg(img + jsonItem.getString("img"));
            bean.setMessage(jsonItem.getString("message"));
            bean.setTel(jsonItem.getString("tel"));
            bean.setZip(jsonItem.getString("zipcode"));
            bean.setUrl(jsonItem.getString("url"));
            if (type == 0) {
                bean.setGobus(jsonItem.getString("gobus"));
                bean.setLevel(jsonItem.getString("level"));
            } else if (type == 1) {
                bean.setLevel(jsonItem.getString("type"));
            }
            return bean;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private LocationBean getNameFromContent(int type, String content) {
        try {
            LocationBean bean = new LocationBean();
            JSONObject jsonItem = new JSONObject(content);
            bean.setId(jsonItem.getInt("id"));
            bean.setX(jsonItem.getDouble("x"));
            bean.setY(jsonItem.getDouble("y"));
            bean.setType(0);
            bean.setName(jsonItem.getString("name"));
            bean.setAddress(jsonItem.getString("address"));
            bean.setImg(img + jsonItem.getString("img"));
            bean.setMessage(jsonItem.getString("message"));
            bean.setTel(jsonItem.getString("tel"));
            bean.setZip(jsonItem.getString("zipcode"));
            bean.setUrl(jsonItem.getString("url"));
            if (type == 0) {
                bean.setGobus(jsonItem.getString("gobus"));
                bean.setLevel(jsonItem.getString("level"));
            } else if (type == 1) {
                bean.setLevel(jsonItem.getString("type"));
            }
            return bean;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
