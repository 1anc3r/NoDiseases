package me.lancer.nodiseases.mvp.system;

import java.util.List;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class SystemBean {

    private int id;             //ID编码
    private int type;           //类型
    private String name;        //名称
    private String title;       //标题
    private String keywords;    //关键词
    private String description; //简介
    private int department;     //相关科室
    private int place;          //检查身体
    private List<SystemBean> list;

    public SystemBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public List<SystemBean> getList() {
        return list;
    }

    public void setList(List<SystemBean> list) {
        this.list = list;
    }
}
