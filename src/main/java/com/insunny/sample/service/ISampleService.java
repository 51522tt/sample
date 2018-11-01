package com.insunny.sample.service;

import com.insunny.sample.domian.new_sample;
import com.insunny.sample.domian.sample;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

/**
 * Created by 陈继业 on 2018/9/19 0019.
 * Version 1.0
 */

public interface ISampleService {

    Page<sample> findSampleByDateBetween (Integer page, Integer size, Date start, Date end);
    Page<new_sample> findNewSampleByDateBetween(Integer page, Integer size, Date start, Date end);
//    List<sample> getSampleBeanList();
//
    String catSample(sample s);
//
//    Page<sample> findSampleNoCriteria(Integer page, Integer size);
//    Page<sample> findSampleCriteria(Integer page, Integer size, SampleQuery sampleQuery);
//
//    Page<new_sample> findNewSampleNoCriteria(Integer page, Integer size);
//    Page<new_sample> findNewSampleCriteria(Integer page, Integer size, NewSampleQuery newSampleQuery);
//
    List<sample> findByAsinBetweenDateTime(String asin, String start , String end);

    Page<sample> findByAsinInNameBetweeen(String asin,String start,String end,List<String> accounts);

    Page<new_sample> findNewSampleIn(List<String> names, Integer page, Integer size);

    Page<sample> findSampleIn(List<String> names, Integer page, Integer size);

    void findInfo(List<String> accounts);

    Integer findSpeciesNumber(List<String> accounts);

    long findTotal(List<String> accounts);

}
