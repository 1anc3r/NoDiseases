package me.lancer.nodiseases.mvp.message;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class MessageBean {

    private int id;             //ID编码
    private int type;             //类别
    private String name;        //标题
    private String title;       //标题
    private String img;         //图片
    private String infoclass;   //分类
    private String loreclass;   //分类
    private String keywords;    //关键词
    private String description; //简介
    private String message;     //内容

    public MessageBean() {
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getInfoclass() {
        return infoclass;
    }

    public void setInfoclass(String infoclass) {
        this.infoclass = infoclass;
    }

    public String getLoreclass() {
        return loreclass;
    }

    public void setLoreclass(String loreclass) {
        this.loreclass = loreclass;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
