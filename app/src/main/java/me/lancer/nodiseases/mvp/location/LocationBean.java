package me.lancer.nodiseases.mvp.location;

import java.util.List;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class LocationBean {

    private int id;             //ID编码
    private int type;           //类型
    private String name;        //名称
    private int area;           //城市
    private String address;     //地址
    private String img;         //图片
    private String message;     //简介
    private String tel;         //电话
    private String zip;         //邮编
    private String url;         //网站
    private String gobus;       //坐车方式
    private String level;       //医院等级
    private double x;            //地图x坐标
    private double y;            //地图y坐标
    private List<LocationBean> list;

    public LocationBean() {
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

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGobus() {
        return gobus;
    }

    public void setGobus(String gobus) {
        this.gobus = gobus;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
