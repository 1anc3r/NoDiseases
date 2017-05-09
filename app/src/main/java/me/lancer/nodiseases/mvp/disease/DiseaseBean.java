package me.lancer.nodiseases.mvp.disease;

/**
 * Created by HuangFangzhi on 2017/3/13.
 */

public class DiseaseBean {

    private int id;             //ID编码
    private int type;           //类型
    private String keywords;    //关键词
    private String name;        //名称
    private String description; //简介
    private String img;         //图片
    private String message;     //内容
    private String disease;     //相关疾病
    private String diseasetext; //疾病描述
    private String symptom;     //相关病状
    private String symptomtext; //病状描述
    private String food;        //相关食物
    private String foodtext;    //食物描述
    private String checks;      //相关检查
    private String checktext;   //检查描述
    private String drug;        //相关药品
    private String drugtext;    //药品描述
    private String detailtext;  //诊断详情
    private String causetext;   //病因
    private String caretext;    //预防
    private String department;  //相关科室
    private String place;       //检查身体

    public DiseaseBean() {
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

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getDiseasetext() {
        return diseasetext;
    }

    public void setDiseasetext(String diseasetext) {
        this.diseasetext = diseasetext;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getSymptomtext() {
        return symptomtext;
    }

    public void setSymptomtext(String symptomtext) {
        this.symptomtext = symptomtext;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getFoodtext() {
        return foodtext;
    }

    public void setFoodtext(String foodtext) {
        this.foodtext = foodtext;
    }

    public String getChecks() {
        return checks;
    }

    public void setChecks(String checks) {
        this.checks = checks;
    }

    public String getChecktext() {
        return checktext;
    }

    public void setChecktext(String checktext) {
        this.checktext = checktext;
    }

    public String getDrug() {
        return drug;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }

    public String getDrugtext() {
        return drugtext;
    }

    public void setDrugtext(String drugtext) {
        this.drugtext = drugtext;
    }

    public String getDetailtext() {
        return detailtext;
    }

    public void setDetailtext(String detailtext) {
        this.detailtext = detailtext;
    }

    public String getCausetext() {
        return causetext;
    }

    public void setCausetext(String causetext) {
        this.causetext = causetext;
    }

    public String getCaretext() {
        return caretext;
    }

    public void setCaretext(String caretext) {
        this.caretext = caretext;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
