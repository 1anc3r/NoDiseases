package me.lancer.nodiseases.mvp.message;

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

public class MessageModel {

    IMessagePresenter presenter;

    ContentGetterSetter contentGetterSetter = new ContentGetterSetter();
    String url = "http://www.tngou.net/api/";
    String iClass = url + "info/classify";
    String iList = url + "info/list?id=";
    String iNews = url + "info/news?id=";
    String iShow = url + "info/show?id=";
    String lClass = url + "lore/classify";
    String lList = url + "lore/list?id=";
    String lNews = url + "lore/news?id=";
    String lShow = url + "lore/show?id=";
    String search = url + "search";
    String img = "http://tnfs.tngou.net/image/";

    public MessageModel(IMessagePresenter presenter) {
        this.presenter = presenter;
    }

    public void classify(int type) {
        String url = "";
        if (type == 0) {
            url = iClass;
        } else if (type == 1) {
            url = lClass;
        }
        String content = contentGetterSetter.getContentFromHtml("Message.classify", url);
        List<MessageBean> list;
        if (!content.contains("获取失败!")) {
            list = getClassifyFromContent(type, content);
            presenter.loadClassifySuccess(list);
        } else {
            presenter.loadClassifyFailure(content);
            Log.e("Message.classify", content);
        }
    }

    public void list(int type, int id) {
        String url = "";
        if (type == 0) {
            url = iList;
        } else if (type == 1) {
            url = lList;
        }
        Log.e("url", url+id);
        String content = contentGetterSetter.getContentFromHtml("Message.list", url + id);
        List<MessageBean> list;
        if (!content.contains("获取失败!")) {
            list = getListFromContent(type, content);
            presenter.loadListSuccess(list);
        } else {
            presenter.loadListFailure(content);
            Log.e("Message.list", content);
        }
    }

    public void news(int type, int id) {
        String url = "";
        if (type == 0) {
            url = iNews;
        } else if (type == 1) {
            url = lNews;
        }
        String content = contentGetterSetter.getContentFromHtml("Message.news", url + id);
        List<MessageBean> list;
        if (!content.contains("获取失败!")) {
            list = getNewsFromContent(type, content);
            presenter.loadNewsSuccess(list);
        } else {
            presenter.loadNewsFailure(content);
            Log.e("Message.news", content);
        }
    }

    public void show(int type, int id) {
        String url = "";
        if (type == 0) {
            url = iShow;
        } else if (type == 1) {
            url = lShow;
        }
        Log.e("show: ", url+id);
        String content = contentGetterSetter.getContentFromHtml("Message.show", url + id);
        MessageBean bean;
        if (!content.contains("获取失败!")) {
            bean = getShowFromContent(type, content);
            presenter.loadShowSuccess(bean);
        } else {
            presenter.loadShowFailure(content);
            Log.e("Message.show", content);
        }
    }

    public void search(int type, String keywords) {
        String url = "";
        if (type == 0) {
            url = search + "?name=info&keyword=";
        } else if (type == 1) {
            url = search + "?name=lore&keyword=";
        }
        String content = contentGetterSetter.getContentFromHtml("Message.search", url + keywords);
        List<MessageBean> list;
        if (!content.contains("获取失败!")) {
            list = getSearchFromContent(type, content);
            presenter.loadSearchSuccess(list);
        } else {
            presenter.loadSearchFailure(content);
            Log.e("Message.search", content);
        }
    }

    private List<MessageBean> getClassifyFromContent(int type, String content) {
        try {
            List<MessageBean> list = new ArrayList<>();
            JSONObject jsonObj = new JSONObject(content);
            JSONArray jsonArr = jsonObj.getJSONArray("tngou");
            for (int i = 0; i < jsonArr.length(); i++) {
                MessageBean bean = new MessageBean();
                JSONObject jsonItem = jsonArr.getJSONObject(i);
                bean.setType(0);
                bean.setTitle(jsonItem.getString("title"));
                if (type == 0) {
                    switch (bean.getTitle()) {
                        case "社会热点":
                            bean.setId(11);
                            break;
                        case "食品新闻":
                            bean.setId(5);
                            break;
                        case "疾病快讯":
                            bean.setId(7);
                            break;
                        case "药品新闻":
                            bean.setId(4);
                            break;
                        case "生活贴士":
                            bean.setId(3);
                            break;
                        case "医疗新闻":
                            bean.setId(2);
                            break;
                        case "企业要闻":
                            bean.setId(1);
                            break;
                    }
                } else if (type == 1) {
                    switch (bean.getTitle()) {
                        case "减肥瘦身":
                            bean.setId(11);
                            break;
                        case "私密生活":
                            bean.setId(7);
                            break;
                        case "女性保养":
                            bean.setId(5);
                            break;
                        case "男性健康":
                            bean.setId(4);
                            break;
                        case "孕婴手册":
                            bean.setId(6);
                            break;
                        case "夫妻情感":
                            bean.setId(13);
                            break;
                        case "育儿宝典":
                            bean.setId(8);
                            break;
                        case "健康饮食":
                            bean.setId(3);
                            break;
                        case "医疗护理":
                            bean.setId(12);
                            break;
                        case "老人健康":
                            bean.setId(1);
                            break;
                        case "孩子健康":
                            bean.setId(2);
                            break;
                        case "四季养生":
                            bean.setId(10);
                            break;
                        case "心里健康":
                            bean.setId(9);
                            break;
                    }
                }
                bean.setKeywords(jsonItem.getString("keywords"));
                bean.setDescription(jsonItem.getString("description"));
                list.add(bean);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<MessageBean> getListFromContent(int type, String content) {
        try {
            List<MessageBean> list = new ArrayList<>();
            JSONObject jsonObj = new JSONObject(content);
            JSONArray jsonArr = jsonObj.getJSONArray("tngou");
            for (int i = 0; i < jsonArr.length(); i++) {
                MessageBean bean = new MessageBean();
                JSONObject jsonItem = jsonArr.getJSONObject(i);
                bean.setId(jsonItem.getInt("id"));
                bean.setType(0);
                bean.setTitle(jsonItem.getString("title"));
                bean.setImg(img + jsonItem.getString("img"));
                bean.setKeywords(jsonItem.getString("keywords"));
                bean.setDescription(jsonItem.getString("description"));
                if (type == 0) {
                    bean.setInfoclass(jsonItem.getString("infoclass"));
                } else if (type == 1) {
                    bean.setLoreclass(jsonItem.getString("loreclass"));
                }
                list.add(bean);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<MessageBean> getNewsFromContent(int type, String content) {
        try {
            List<MessageBean> list = new ArrayList<>();
            JSONObject jsonObj = new JSONObject(content);
            JSONArray jsonArr = jsonObj.getJSONArray("tngou");
            for (int i = 0; i < jsonArr.length(); i++) {
                MessageBean bean = new MessageBean();
                JSONObject jsonItem = jsonArr.getJSONObject(i);
                bean.setId(jsonItem.getInt("id"));
                bean.setType(0);
                bean.setTitle(jsonItem.getString("title"));
                bean.setImg(img + jsonItem.getString("img"));
                bean.setKeywords(jsonItem.getString("keywords"));
                bean.setMessage(jsonItem.getString("message"));
                bean.setDescription(jsonItem.getString("description"));
                if (type == 0) {
                    bean.setInfoclass(jsonItem.getString("infoclass"));
                } else if (type == 1) {
                    bean.setLoreclass(jsonItem.getString("loreclass"));
                }
                list.add(bean);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private MessageBean getShowFromContent(int type, String content) {
        try {
            MessageBean bean = new MessageBean();
            JSONObject jsonObj = new JSONObject(content);
            bean.setId(jsonObj.getInt("id"));
            bean.setType(0);
            bean.setTitle(jsonObj.getString("title"));
            bean.setImg(img + jsonObj.getString("img"));
            bean.setKeywords(jsonObj.getString("keywords"));
            bean.setMessage(jsonObj.getString("message").split(".jpg\"></p>")[1]);
            bean.setDescription(jsonObj.getString("description"));
            if (type == 0) {
                bean.setInfoclass(jsonObj.getString("infoclass"));
            } else if (type == 1) {
                bean.setLoreclass(jsonObj.getString("loreclass"));
            }
            return bean;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<MessageBean> getSearchFromContent(int type, String content) {
        try {
            List<MessageBean> list = new ArrayList<>();
            JSONObject jsonObj = new JSONObject(content);
            JSONArray jsonArr = jsonObj.getJSONArray("tngou");
            for (int i = 0; i < jsonArr.length(); i++) {
                MessageBean bean = new MessageBean();
                JSONObject jsonItem = jsonArr.getJSONObject(i);
                bean.setId(jsonItem.getInt("id"));
                bean.setType(0);
                bean.setTitle(jsonItem.getString("title"));
                bean.setImg(img + jsonItem.getString("img"));
                bean.setKeywords(jsonItem.getString("keywords"));
                bean.setMessage(jsonItem.getString("message"));
                bean.setDescription(jsonItem.getString("description"));
                if (type == 0) {
                    bean.setInfoclass(jsonItem.getString("infoclass"));
                } else if (type == 1) {
                    bean.setLoreclass(jsonItem.getString("loreclass"));
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
