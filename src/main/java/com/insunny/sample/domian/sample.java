package com.insunny.sample.domian;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.ibatis.annotations.One;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Created by 陈继业 on 2018/9/19 0019.
 * Version 1.0
 */
@Entity
 public class sample implements Comparable<sample> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String fnsku;
    private String asin;
    private String sku;
    private String name;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date dateTime;


    public String getFnsku() {
        return fnsku;
    }

    public void setFnsku(String fnsku) {
        this.fnsku = fnsku;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime){
        this.dateTime = dateTime;
    }
    public void setDateTime(String dateTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date format = null;
        try {
            format = simpleDateFormat.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.dateTime = format;
    }

    public Date getDateTime(String sources)  {
        DateFormat df = new SimpleDateFormat();
        Date parse = null;
        try {
            parse = df.parse(sources);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parse;
    }


    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        return "sample{" +
                "id=" + id +
                ", fnsku='" + fnsku + '\'' +
                ", asin='" + asin + '\'' +
                ", sku='" + sku + '\'' +
                ", name='" + name + '\'' +
                ", dateTime='" + dateTime + '\'' +
                '}';
    }

    @Override
    public int compareTo(sample o) {
        if (dateTime.before(o.dateTime)) {
            return 1;
        } else if (dateTime.after(o.dateTime)) {
            return -1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        sample sample = (sample) o;
        return Objects.equals(sku, sample.sku);

    }

    @Override
    public int hashCode() {
        return Objects.hash(sku);
    }
}
