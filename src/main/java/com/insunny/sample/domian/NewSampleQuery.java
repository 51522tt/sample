package com.insunny.sample.domian;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 陈继业 on 2018/9/19 0019.
 * Version 1.0
 */
 public class NewSampleQuery {
    private String asin;
    private String fnsku;
    private String sku;
    private String name;
    private String dateTime;
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getFnsku() {
        return fnsku;
    }

    public void setFnsku(String fnsku) {
        this.fnsku = fnsku;
    }
    public void setDateTime(Date dateTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        String format = simpleDateFormat.format(dateTime);
        this.dateTime = format;
    }

    public void setDateTime(String dateTime) throws ParseException {
        this.dateTime = dateTime;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }



    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "NewSampleQuery{" +
                "asin='" + asin + '\'' +
                ", fnsku='" + fnsku + '\'' +
                ", sku='" + sku + '\'' +
                ", name='" + name + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
