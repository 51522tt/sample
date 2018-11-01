package com.insunny.sample.contrller;

import com.insunny.sample.dao.IAdminDao;
import com.insunny.sample.dao.ISampleDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 陈继业 on 2018/9/19 0019.
 * Version 1.0
 */
@Controller
@RequestMapping("/sample")
public class SampleController {


    @Autowired
    private IAdminDao iAdminDao ;
    @Autowired
    private ISampleDao dao;



    @GetMapping
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView();

        mav.setViewName("index");
        return mav;
    }

    @GetMapping("login")
    public String login(){
        return "login";
    }

//    @PostMapping("login")
//    public String login(Admin Admin){
//        System.out.println(Admin);
//        return "index";
//    }


}
