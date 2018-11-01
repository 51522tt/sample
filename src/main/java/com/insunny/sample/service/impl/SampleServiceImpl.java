package com.insunny.sample.service.impl;


import com.alibaba.fastjson.JSON;
import com.insunny.sample.dao.INewSampleDao;
import com.insunny.sample.dao.ISampleDao;
import com.insunny.sample.domian.new_sample;
import com.insunny.sample.domian.sample;
import com.insunny.sample.service.ISampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 陈继业 on 2018/9/19 0019.
 * Version 1.0
 */
@Service
public class SampleServiceImpl implements ISampleService {
    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private ISampleDao sampleDao;

    @Autowired
    private INewSampleDao newSampleDao;

//    @Override
//    public List<sample> getSampleBeanList() {
//        return sampleDao.findAll();
//    }

    @Override
    @Transactional
    public String catSample(sample s) {
        Map<String, Integer> map = new HashMap<>(16);
        //Object parse = JSON.parse(sample);
        try {
            newSampleDao.deleteById(s.getId());
            sampleDao.save(s);
            map.put("state", 200);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state", 5000001);
        }
        return JSON.toJSONString(map);

    }




//    @Override
//    public Page<sample> findSampleNoCriteria(Integer page, Integer size) {
//        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "id");
//        return sampleDao.findAll(pageable);
//    }
//
//    @Override
//    public Page<sample> findSampleCriteria(Integer page, Integer size, SampleQuery sampleQuery) {
//        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "id");
//        Page<sample> samplePage = sampleDao.findAll(new Specification<sample>() {
//            @Override
//            public Predicate toPredicate(Root<sample> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                Predicate p1 = criteriaBuilder.equal(root.get("name").as(String.class), sampleQuery.getName());
//                Predicate p2 = criteriaBuilder.equal(root.get("asin").as(String.class), sampleQuery.getAsin());
//                Predicate p3 = criteriaBuilder.equal(root.get("sku").as(String.class), sampleQuery.getSku());
//                Predicate p4 = criteriaBuilder.equal(root.get("dateTime").as(String.class), sampleQuery.getDateTime());
//                query.where(criteriaBuilder.and(p1, p2, p3, p4));
//                return query.getRestriction();
//            }
//        }, pageable);
//        return samplePage;
//
//
//    }
//
//    @Override
//    public Page<new_sample> findNewSampleNoCriteria(Integer page, Integer size) {
//        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "id");
//        return newSampleDao.findAll(pageable);
//    }
//
//    @Override
//    public Page<new_sample> findNewSampleCriteria(Integer page, Integer size, NewSampleQuery newSampleQuery) {
//        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "id");
//        Page<new_sample> samplePage = newSampleDao.findAll(new Specification<new_sample>() {
//            @Override
//            public Predicate toPredicate(Root<new_sample> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                Predicate p1 = criteriaBuilder.equal(root.get("name").as(String.class), newSampleQuery.getName());
//                Predicate p2 = criteriaBuilder.equal(root.get("asin").as(String.class), newSampleQuery.getAsin());
//                Predicate p3 = criteriaBuilder.equal(root.get("sku").as(String.class), newSampleQuery.getSku());
//                Predicate p4 = criteriaBuilder.equal(root.get("dateTime").as(String.class), newSampleQuery.getDateTime());
//                query.where(criteriaBuilder.and(p1, p2, p3, p4));
//                return query.getRestriction();
//            }
//        }, pageable);
//        return samplePage;
//    }
//
//
//
//


    @Override
    public Page<sample> findSampleByDateBetween(Integer page, Integer size, Date start, Date end) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "id");
        Page<sample> samplePage = sampleDao.findAll(new Specification<sample>() {
            @Override
            public Predicate toPredicate(Root<sample> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Expression<Date> exp = root.get("dateTime");
                query.where(criteriaBuilder.between(exp, start,end));
                query.groupBy(root.get("asin"));
                return query.getRestriction();
            }
        }, pageable);
        return samplePage;
    }
//    @Override
//    public Page<sample> findSampleByDateBetween(Integer page, Integer size, Date start, Date end) {
//        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "id");
//        Page<sample> samplePage = sampleDao.findAll(new Specification<sample>() {
//            @Override
//            public Predicate toPredicate(Root<sample> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                Expression<Date> exp = root.get("dateTime");
//                query.where(criteriaBuilder.between(exp, start,end));
//                query.distinct(true);
//                return query.getRestriction();
//            }
//        }, pageable);
//        return samplePage;
//    }



    @Override
    public Page<new_sample> findNewSampleByDateBetween(Integer page, Integer size, Date start, Date end) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "id");
        Page<new_sample> samplePage = newSampleDao.findAll(new Specification<new_sample>() {
            @Override
            public Predicate toPredicate(Root<new_sample> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Expression<Date> exp = root.get("dateTime");
                query.where(criteriaBuilder.between(exp, start, end));
                return query.getRestriction();
            }
        }, pageable);
        return samplePage;
    }


    @Override
    public Page<sample> findSampleIn(List names, Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "id");

        Page<sample> samplePage = sampleDao.findAll(new Specification<sample>() {
            @Override
            public Predicate toPredicate(Root<sample> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                Expression<String> exp = root.get("name");
                query.where(exp.in(names)); // 往in中添加所有id 实现in 查询
                query.groupBy(root.get("asin"));

                return query.getRestriction();
            }
        }, pageable);
        return samplePage;
    }

    @Override
    public Page<new_sample> findNewSampleIn(List names, Integer page, Integer size) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.DESC, "id");

        Page<new_sample> samplePage = newSampleDao.findAll(new Specification<new_sample>() {
            @Override
            public Predicate toPredicate(Root<new_sample> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                Expression<String> exp = root.get("name");
                list.add(exp.in(names)); // 往in中添加所有id 实现in 查询
                if (list.size() != 0) {
                    Predicate[] p = new Predicate[list.size()];
                    return cb.and(list.toArray(p));
                } else {
                    return null;
                }
            }
        }, pageable);
        return samplePage;
    }


    @Override
    public long findTotal(List accounts) {

        long count = sampleDao.count(new Specification<sample>() {

            @Override
            public Predicate toPredicate(Root<sample> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                Expression<String> exp = root.get("name");
                list.add(exp.in(accounts)); // 往in中添加所有id 实现in 查询
                if (list.size() != 0) {
                    Predicate[] p = new Predicate[list.size()];
                    return cb.and(list.toArray(p));
                } else {
                    return null;
                }
            }
        });
        return count;
    }

    @Override
    public Integer findSpeciesNumber(List accounts) {
        Pageable pageable = new PageRequest(0,50, Sort.Direction.DESC, "id");
        Page< sample> samplePage = sampleDao.findAll(new Specification<sample>() {
            @Override
            public Predicate toPredicate(Root<sample> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                Expression<String> exp = root.get("name");
                query.groupBy(root.get("asin"));
                list.add(exp.in(accounts)); // 往in中添加所有id 实现in 查询
                if (list.size() != 0) {
                    Predicate[] p = new Predicate[list.size()];
                    return cb.and(list.toArray(p));
                } else {
                    return null;
                }
            }
        },pageable);
        return Math.toIntExact(samplePage.getTotalElements());
    }

    @Override
    public List<sample> findByAsinBetweenDateTime(String asin, String start , String end) {
        Page<sample> samplePage = null;
        try {
            c.setTime(sdf.parse(end));
            c.add(Calendar.DATE,1);
            Date endDate = c.getTime();
            Date startDate = sdf.parse(start);
            Pageable pageable = new PageRequest(0,50, Sort.Direction.DESC, "id");
            samplePage = sampleDao.findAll(new Specification<sample>() {
                @Override
                public Predicate toPredicate(Root<sample> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    Expression<Date> exp = root.get("dateTime");
                    Predicate p1 = criteriaBuilder.between(exp, startDate, endDate);
                    Predicate p2 = criteriaBuilder.equal(root.get("asin"), asin);
                    query.where(criteriaBuilder.and(p1,p2));
                    return query.getRestriction();
                }
            }, pageable);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return samplePage.getContent();
    }

    @Override
    public Page<sample> findByAsinInNameBetweeen(String asin,String start,String end, List<String> accounts) {
        Pageable pageable = new PageRequest(0,50, Sort.Direction.DESC, "id");
        Page< sample> samplePage = null;

        try {
            c.setTime(sdf.parse(end));
            c.add(Calendar.DATE,1);
            Date endDate = c.getTime();
            Date startDate = sdf.parse(start);
            samplePage = sampleDao.findAll(new Specification<sample>() {
                @Override
                public Predicate toPredicate(Root<sample> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> list = new ArrayList<>();
                    Expression<String> exp = root.get("name");
                    Expression<String> asin1 = root.get("asin");
                    Expression<Date> exp1 = root.get("dateTime");
                    Predicate p1 = exp.in(accounts);// in ["","",..]
                    Predicate p2 = cb.equal(asin1, asin);//asin=asin
                    Predicate p3 = cb.between(exp1, startDate, endDate);// between startDate and endDate
                    query.where(cb.and(p1,p2,p3));
                    return query.getRestriction();
                }
            },pageable);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return samplePage;
    }

    @Override
    public void findInfo(List<String> accounts) {

    }
}

