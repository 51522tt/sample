package com.insunny.sample.domian;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(name="new_pay_sample")
public class NewPaySample {

    //private
    //产品名称    图片（要上传可能比较难）   数量  金额  琏接   订单号
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String productName;
    private String imgPath;
    private String number;
    private Double money;
    private String url;
    private String orderNumber;
    private String location;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date lecDateTime;


    public Date getLecDateTime() {
        return lecDateTime;
    }

    public void setLecDateTime(Date lecDateTime) {
        this.lecDateTime = lecDateTime;
    }

    public void setLecDateTime(String dateTime)  {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date format = null;
        try {
            format = simpleDateFormat.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.lecDateTime = format;
    }

    public Date getLecDateTime(String sources)  {
        DateFormat df = new SimpleDateFormat();
        Date parse = null;
        try {
            parse = df.parse(sources);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public String  toString() {
        return "NewPaySample{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", imgPath='" + imgPath + '\'' +
                ", number='" + number + '\'' +
                ", money=" + money +
                ", url='" + url + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
