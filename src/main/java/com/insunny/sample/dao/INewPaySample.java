package com.insunny.sample.dao;

import com.insunny.sample.domian.NewPaySample;
import com.insunny.sample.domian.sample;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface INewPaySample extends JpaRepository<NewPaySample, Integer>, JpaSpecificationExecutor<NewPaySample> {

//    @Query(value = "SELECT * FROM new_pay_sample WHERE name in(?1) and CONCAT(asin,',',date_time,',',name,',',sku,',',fnsku) like CONCAT('%',?2,'%') and date_time between ?3 and ?4 group by asin", nativeQuery = true)
//    List<sample> vagueQuery(@Param("accounts")List accounts, String q, Date start, Date end);
}
