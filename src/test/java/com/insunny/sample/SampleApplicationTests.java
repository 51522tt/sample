package com.insunny.sample;

import com.insunny.sample.config.WebConfig;
import com.insunny.sample.dao.IAdminDao;
import com.insunny.sample.dao.INewSampleDao;
import com.insunny.sample.dao.ISampleDao;
import com.insunny.sample.domian.Admin;
import com.insunny.sample.domian.new_sample;
import com.insunny.sample.domian.sample;
import com.insunny.sample.service.HttpClient;
import com.insunny.sample.service.ISampleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleApplicationTests {

    @Autowired
    private IAdminDao iAdminDao;

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

    @Test
    public void contextLoads() {
        Admin admin = new Admin();
        admin.setUsername("周静");
        HashMap<String, Object> map = new HashMap<>(16);
        Admin user = iAdminDao.findAdminByUsernameAndPassword(admin.getUsername(), admin.getPassword());
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
        paramMap.add("username", admin.getUsername());
        String data = httpClient.client(WebConfig.SAMPLE_ACCESE_URL, HttpMethod.POST, paramMap);
        List<String> accounts = httpClient.bodyConverter(data);
        System.out.println("账号"+accounts);
        Page<new_sample> newSampleIn = sampleServiceImpl.findNewSampleIn(accounts, 0, 10);
        System.out.println("所属"+newSampleIn.getContent());
    }

    @Test
    public void a() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date start = sdf.parse("2017-10-5 02:11:00");
            Date end = sdf.parse("2020-10-9 02:20:00");

        Page<sample> test = sampleServiceImpl.findSampleByDateBetween(1, 20, start, end);

            System.out.println(test.getContent());
        System.out.println(test.getTotalElements());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void b() {
        List<String > aa = new ArrayList<>();
        aa.add("gog");
        aa.add("cyde");
        aa.add("2");
        long speciesNumber = sampleServiceImpl.findTotal(aa);
        System.out.println(speciesNumber);

    }
    @Test
    public void c() {
        List<String > aa = new ArrayList<>();
        aa.add("gog");
        aa.add("cyde");
        aa.add("auk");
        System.out.println(aa);
       sampleServiceImpl.findSpeciesNumber(aa);

    }
    @Test
    public void d() {
        List<sample> bySku = sampleDao.findBySku("1");
        bySku.forEach(sample -> {
            System.out.println(sample);
        });
    }
    EntityManagerFactory emf = null;

    @Before
    public void before() {

    }

    @Test
    public void e() {
        List<sample> samples = sampleDao.findSamplesDistinctByAsin();
        samples.forEach(index -> {
            System.out.println(index);
        });


    }
    @Test
    public void f() {
        List<String > aa = new ArrayList<>();
        aa.add("gog");
        aa.add("cyde");
            aa.add("auk");
        Integer integer = sampleServiceImpl.findSpeciesNumber(aa);
        System.out.println(integer);
    }

    @Test
    public void g() {
        List<String > aa = new ArrayList<>();
        aa.add("aa");
        aa.add("cc");
        aa.add("ee");
        List<sample> aaa = sampleServiceImpl.findByAsinBetweenDateTime("aaa", "2018-10-9 00:00:00", "2018-10-9 23:10:10");
        aaa.forEach(index ->{
            System.out.println(index
            );
        });
    }

    @Test
    public void h() {
//        List<String > aa = new ArrayList<>();
//        List<String > bb = new ArrayList<>();
//        aa.add("cyde");
//
//        bb.add("date_time");
//        bb.add("fnsku");
//        List<sample> aaa = sampleDao.vagueQuery(aa,"sdf");
//        aaa.forEach(index ->{
//            System.out.println(index );
//        });
    }



}
