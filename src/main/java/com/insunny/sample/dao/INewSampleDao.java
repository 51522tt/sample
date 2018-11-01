package com.insunny.sample.dao;

import com.insunny.sample.domian.new_sample;
import com.insunny.sample.domian.sample;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by 陈继业 on 2018/9/19 0019.
 * Version 1.0
 */
public interface INewSampleDao extends JpaRepository<new_sample, Integer>,JpaSpecificationExecutor<new_sample> {
    new_sample findByAsin(String asin);
    List<new_sample> findByFnsku(String fnsku);
    void deleteByAsin(String asin);
    @Query(value = "SELECT * FROM new_sample WHERE name in(?1) and CONCAT(asin,',',date_time,',',name,',',sku,',',fnsku) like CONCAT('%',?2,'%') and date_time between ?3 and ?4 group by asin", nativeQuery = true)
    List<sample> vagueQuery(@Param("accounts")List accounts, String q, Date start, Date end);

    //List<new_sample> findAllByNameBetween(List<String> list);
}
