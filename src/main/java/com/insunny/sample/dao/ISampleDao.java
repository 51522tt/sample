package com.insunny.sample.dao;

import com.insunny.sample.domian.new_sample;
import com.insunny.sample.domian.sample;
import org.apache.ibatis.annotations.Param;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 陈继业 on 2018/9/19 0019.
 * Version 1.0
 */
public interface ISampleDao extends JpaRepository<sample, Integer>, JpaSpecificationExecutor<sample> {


    List<sample> findByFnsku(String fnsku);

    List<sample> findBySku(String sku);

    List<sample> findByAsin(String asin);

    @Query(value = "select count(1) from (select asin from  sample where name in (:accounts) group by asin) a", nativeQuery = true)
    Integer conutInAccount(@Param("accounts") List accounts);

    @Query(value = "SELECT * FROM sample WHERE name in(?1) and CONCAT(asin,',',date_time,',',name,',',sku,',',fnsku) like CONCAT('%',?2,'%') and date_time between ?3 and ?4 group by asin", nativeQuery = true)
    List<sample> vagueQuery(@Param("accounts")List accounts, String q, Date start, Date end);
    //order by id desc limit 0,10

    @Query(value = "select  * from  sample group by asin", nativeQuery = true)
    List<sample> findSamplesDistinctByAsin();



}
