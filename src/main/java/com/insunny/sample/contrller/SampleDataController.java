package com.insunny.sample.contrller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.insunny.sample.config.WebConfig;
import com.insunny.sample.dao.IAdminDao;
import com.insunny.sample.dao.INewSampleDao;
import com.insunny.sample.dao.ISampleDao;
import com.insunny.sample.domian.*;
import com.insunny.sample.service.HttpClient;
import com.insunny.sample.service.ISampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.DateUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 陈继业 on 2018/9/19 0019.
 * Version 1.0
 */
@RestController
@RequestMapping("/sample/data")
public class SampleDataController {


    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private IAdminDao iAdminDao ;

    @Autowired
    private ISampleDao sampleDao;

    @Autowired
    private INewSampleDao newSampleDao;



    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ISampleService sampleServiceImpl;
    @Autowired
    private HttpClient httpClient;

    @GetMapping("/newsamples")
    public List<new_sample> getNewSamples(){
        List<new_sample> sampleList = newSampleDao.findAll();
        System.out.println(sampleList);

        return sampleList;
    }

    @GetMapping("/samples")
    public List<sample> getNewSample(){
        List<sample> sampleList = sampleDao.findAll();
        System.out.println(sampleList);
        return sampleList;
    }

    // @GetMapping("/save/{sku}")
    // public void save(@PathVariable(name="sku") String sku){
    //     String url = "http://ouddy.com/Home/get_info_by_fnsku/"+sku;
    //     HttpMethod method = HttpMethod.GET;
    //     LinkedMultiValueMap<String, String> parms = new LinkedMultiValueMap<String, String>();
    //     String client = httpClient.client(url, method, parms);
    //     System.out.println(client.split(",")[1]);
    //     System.out.println(client.split(",")[1]);
    //     System.out.println(client.split(",")[1]);
    // }




/*
*
* "id": 368,
    "fnsku": "X000WLDWPZ",
    "asin": "B07H9YBSZ4",
    "sku": "CHA-SHOU2-UK-FBA",
    "name": "auk",
    "dateTime": "2018-10-10T05:49:00.000+0000",
    "img": "http://img.manage.com/amimages/CHA-SHOU2-UK-FBA/400_400/1.jpg",
    "_index": 0,
    "_rowKey": 3
* */
    @DeleteMapping("/newsample")
    public String cat(
                      @RequestParam(value = "id") Integer id,
                      @RequestParam(value = "fnsku") String fnsku,
                      @RequestParam(value = "asin") String asin,
                      @RequestParam(value = "sku") String sku,
                      @RequestParam(value = "name") String name,
                      @RequestParam(value = "dateTime") String dateTime) {
        sample sample = new sample();
        sample.setId(id);
        sample.setFnsku(fnsku);
        sample.setAsin(asin);
        sample.setSku(sku);
        sample.setName(name);
        System.out.println(dateTime);
        sample.setDateTime(dateTime);
        System.out.println("即将"+sample);
        return  sampleServiceImpl.catSample(sample);
    }


    @RequestMapping("/findSampleNoQuery")
    public Map<String,Object> findSampleNoQuery(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                @RequestParam(value = "start" , defaultValue = "1900-1-1 00:00:00") String start,
                                                @RequestParam(value = "end" , defaultValue = "2999-1-1 00:00:00") String end,
                                                HttpSession session) throws ParseException {

        System.out.println(start);
        System.out.println(end);
        System.out.println("当前页:"+page);
        System.out.println("每夜页数据:"+size);
        c.setTime(sdf.parse(end));
        c.add(Calendar.DATE,1);
        Date endDate = c.getTime();
        HashMap<String, Object> map = new HashMap<>(16);
        Integer level = ((Admin) session.getAttribute("user")).getLevel();
        Page<sample> datas = null;
        if(level == 1){
            datas = sampleServiceImpl.findSampleByDateBetween(page, size,sdf.parse(start),endDate);
        }else{
            datas = sampleServiceImpl.findSampleIn((List<String>) session.getAttribute("accounts"),page, size);
        }

        map.put("samples",datas.getContent());
        map.put("dataItems",datas.getTotalElements());
        List<sample> content = datas.getContent();
        for(sample c:content){
            System.out.println(c);
        }

        return  map;
    }

//    @RequestMapping(value = "/findSampleQuery",method = {RequestMethod.GET,RequestMethod.POST})
//    public Map<String,Object>  findSampleQuery( @RequestParam(value = "page", defaultValue = "0") Integer page,
//                                @RequestParam(value = "size", defaultValue = "10") Integer size, SampleQuery sampleQuery){
//        HashMap<String, Object> map = new HashMap<>(16);
//        Page<sample> datas = sampleServiceImpl.findSampleCriteria(page, size,sampleQuery);
//        map.put("samples",datas.getContent());
//        map.put("dataItems",datas.getTotalElements());
//        return  map;
//    }


    @RequestMapping("/findNewSampleNoQuery")
    public Map<String,Object> findNewSampleNoQuery(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                   @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                   @RequestParam(value = "start" , defaultValue = "1900-1-1 00:00:00") String start,
                                                   @RequestParam(value = "end" , defaultValue = "2999-1-1 00:00:00") String end,
                                                   HttpSession session) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(start);
        System.out.println(end);
        c.setTime(sdf.parse(end));
        c.add(Calendar.DATE,1);
        Date endDate = c.getTime();
        HashMap<String, Object> map = new HashMap<>(16);
        Integer level = ((Admin) session.getAttribute("user")).getLevel();
        Page<new_sample> datas = null;
        if(level == 1){
            datas = sampleServiceImpl.findNewSampleByDateBetween(page, size,sdf.parse(start),endDate);
        }else{
            datas = sampleServiceImpl.findNewSampleIn((List<String>) session.getAttribute("accounts"),page, size);
        }
        System.out.println(datas.getContent());
        map.put("samples",datas.getContent());
        map.put("dataItems",datas.getTotalElements());
        return  map;
    }

    @RequestMapping("/findInfo")
    public Map<String,Object> findoInfo(HttpSession session){
        Map<String, Object> map = new HashMap<String, Object>(16);
        List<Admin> users = iAdminDao.findAll();
        users.forEach(index -> {
            index.setPassword("*****");
            MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
            paramMap.add("username", index.getUsername());
            String data=  httpClient.client(WebConfig.SAMPLE_ACCESE_URL, HttpMethod.POST, paramMap);
            List<String> accounts = httpClient.bodyConverter(data);
            if(!accounts.isEmpty()){
                index.setAccounts(accounts.size());
                index.setSpeciesNumber(sampleServiceImpl.findSpeciesNumber(accounts));
                index.setTotal( sampleServiceImpl.findTotal(accounts));
            }
        });
        map.put("users",users);
        return map;
    }

    @RequestMapping("/findSampleByAsin")
    public Map<String,Object> findSampleByAsin(@RequestParam(value = "start" , defaultValue = "1900-1-1 00:00:00") String start,
                                              @RequestParam(value = "end" , defaultValue = "2999-1-1 00:00:00") String end,
                                              String asin,HttpSession session){
        Map<String,Object> map = new HashMap(16);
        Integer level = ((Admin) session.getAttribute("user")).getLevel();
        List<sample> samples = null;
        if(level == 1) {
            //samples = sampleDao.findByAsin(asin);
            samples = sampleServiceImpl.findByAsinBetweenDateTime(asin,start,end);
        }else {
            samples = sampleServiceImpl.findByAsinInNameBetweeen(asin,start,end, (List<String>) session.getAttribute("accounts")).getContent();
        }
        samples.forEach(sample -> {
            System.out.println(sample);
        });
        map.put("samples",samples);
        return map;
    }
//    @RequestMapping(value = "/findNewSampleQuery",method = {RequestMethod.GET,RequestMethod.POST})
//    public Map<String,Object>  findNewSampleQuery( @RequestParam(value = "page", defaultValue = "0") Integer page,
//                                              @RequestParam(value = "size", defaultValue = "10") Integer size, NewSampleQuery newSampleQuery){
//        HashMap<String, Object> map = new HashMap<>(16);
//        Page<new_sample> datas = sampleServiceImpl.findNewSampleCriteria(page, size,newSampleQuery);
//        map.put("samples",datas.getContent());
//        map.put("dataItems",datas.getTotalElements());
//        return  map;
//    }
    @PostMapping("/login")
    public Map<String,Object>  login(@RequestBody Admin admin, HttpSession session){
        System.out.println(admin);
        HashMap<String, Object> map = new HashMap<>(16);
        Admin user= iAdminDao.findAdminByUsernameAndPassword(admin.getUsername(), admin.getPassword());
        System.out.println("登录用户"+user);
        map.put("message","404");
        if(user!=null){
            MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
            paramMap.add("username", admin.getUsername());
            String data=  httpClient.client(WebConfig.SAMPLE_ACCESE_URL, HttpMethod.POST, paramMap);
            List<String> accounts = httpClient.bodyConverter(data);
            session.setMaxInactiveInterval(60*60*5);
            session.setAttribute("isLogin",WebConfig.ALREADY_LOGGED_IN);
            session.setAttribute("user",user);
            session.setAttribute("accounts",accounts);
            map.put("message","200");
        }
        return  map;
    }

    @DeleteMapping
    public void logout(HttpSession session){
        session.setAttribute("isLogin", WebConfig.NOT_LOGGED_IN);
    }

    @GetMapping("/vagueQuerySample")
    public Map<String,Object> vagueQuerySample(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "start" , defaultValue = "1900-1-1 00:00:00") String start,
            @RequestParam(value = "end" , defaultValue = "2999-1-1 00:00:00") String end,
            @RequestParam(value = "q" , defaultValue = "") String q,HttpSession session) throws ParseException {
        c.setTime(sdf.parse(end));
        c.add(Calendar.DATE,1);
        Date endDate = c.getTime();
        List<String> accounts = (List<String>) session.getAttribute("accounts");
        HashMap<String, Object> map = new HashMap<>(16);
        List<sample> samples = sampleDao.vagueQuery(accounts, q,sdf.parse(start),endDate);
        map.put("samples",samples);
        return map;
    }

    @GetMapping("/vagueQueryNewSamples")
    public Map<String,Object> vagueQueryNewSamples(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "start" , defaultValue = "1900-1-1 00:00:00") String start,
            @RequestParam(value = "end" , defaultValue = "2999-1-1 00:00:00") String end,
            @RequestParam(value = "q" , defaultValue = "") String q,HttpSession session) throws ParseException {
        c.setTime(sdf.parse(end));
        c.add(Calendar.DATE,1);
        Date endDate = c.getTime();
        List<String> accounts = (List<String>) session.getAttribute("accounts");
        HashMap<String, Object> map = new HashMap<>(16);
        List<sample> samples = newSampleDao.vagueQuery(accounts, q,sdf.parse(start),endDate);
        map.put("samples",samples);
        return map;
    }

}
