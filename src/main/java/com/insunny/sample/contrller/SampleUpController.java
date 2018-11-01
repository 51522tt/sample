package com.insunny.sample.contrller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.insunny.sample.dao.IAdminDao;
import com.insunny.sample.dao.INewSampleDao;
import com.insunny.sample.dao.ISampleDao;
import com.insunny.sample.domian.new_sample;
import com.insunny.sample.domian.sample;
import com.insunny.sample.service.HttpClient;
import com.insunny.sample.service.ISampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.DateUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/sku")
public class SampleUpController {

    @Autowired
    private INewSampleDao newSampleDao;

    @Autowired
    private ISampleDao sampleDao;

    @Autowired
    private RestTemplate restTemplate;

    /***********HTTP GET method*************/
    @GetMapping("/{sku}")
    public String getJson(@PathVariable(name="sku") String sku) throws IllegalAccessException, InvocationTargetException, InstantiationException, ParseException {
        String url = "http://ouddy.com/Home/get_info_by_fnsku/"+sku;
        ResponseEntity<String> results = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        String json = results.getBody();

        Map parse = (Map) JSONObject.parse(json);
        new_sample sample = getSample(parse);
        sample.setFnsku(sku);
        sample.setDateTime(new Date());
        Map<String, Integer> map = new HashMap<>(16);
        try{
            if(!checkResku(sample)){
                newSampleDao.save(sample);
                map.put("state",200);
            }else{
                map.put("state",300);
            }
        }catch(DataIntegrityViolationException e ){
            e.printStackTrace();
            map.put("state",500);
        }
        return json;
    }

    private boolean checkResku(new_sample ns){
        if(ns.getSku() == null || ns.getSku()==""){
            return true;
        }
        List<new_sample> nss = newSampleDao.findByFnsku(ns.getFnsku());
        List<sample> ss =sampleDao.findByFnsku(ns.getFnsku());
        Calendar c = Calendar.getInstance();
        Integer year = DateUtils.year(new Date());
        Integer month = DateUtils.month(new Date());
        Integer day = DateUtils.day(new Date());
        c.set(year,month,day);
        Date today = c.getTime();
        for(new_sample sample:nss){
            c.set(DateUtils.year(sample.getDateTime()),DateUtils.month(sample.getDateTime()),DateUtils.day(sample.getDateTime()));
            Date time =c.getTime();
            System.out.println(time + "  "+ today);
            System.out.println("添加日期"+time.compareTo(today));
            if(time.compareTo(today) == 0){
                return true;
            }
        }
        for (sample sample:ss){
            c.set(DateUtils.year(sample.getDateTime()),DateUtils.month(sample.getDateTime()),DateUtils.day(sample.getDateTime()));
            Date time =c.getTime();
            System.out.println(time + "  "+ today);
            System.out.println("添加日期"+time.compareTo(today));
            if(time.compareTo(today) == 0){
                return true;
            }
        }
        return false;
    }


    public new_sample getSample(Map map) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Class<new_sample> sampleClass = new_sample.class;
        new_sample sample = sampleClass.newInstance();
        Set set = map.keySet();
        Iterator ite = set.iterator();
        Method[] methods = sampleClass.getMethods();
        while(ite.hasNext()){
            String key = (String) ite.next();
            String value = (String) map.get(key);
            for(Method m:methods){
                String name = m.getName();
                if(name.contains("set")&&name.toUpperCase().contains(key.toUpperCase())){
                    m.invoke(sample, value);
                }
            }
        }
        return sample;
    }


    @PostMapping
    public String dataJSON(new_sample sample){
        sample.setDateTime(new Date());
        Map<String, Integer> map = new HashMap<>(16);
        try{
            newSampleDao.save(sample);
            map.put("state",200);
        }catch(DataIntegrityViolationException e ){
            e.printStackTrace();
            map.put("state",500);

        }
        return JSON.toJSONString(map);
    }




}
