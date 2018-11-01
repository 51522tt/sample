package com.insunny.sample;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@MapperScan("mapper")
public class SampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class, args);
    }
    /**
    //  * 注册DruidServlet
    //  * @return
    //  */
     @Bean
     public ServletRegistrationBean druidServletRegistrationBean() {
         ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
         servletRegistrationBean.setServlet(new StatViewServlet());
         servletRegistrationBean.addUrlMappings("/druid/*");
         return servletRegistrationBean;
     }
     /**
      * 注册DruidFilter拦截
      * @return
      */
     @Bean
     public FilterRegistrationBean duridFilterRegistrationBean() {
         FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
         filterRegistrationBean.setFilter(new WebStatFilter());
         Map<String, String> initParams = new HashMap<String, String>();
         //设置忽略请求
         initParams.put("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
         filterRegistrationBean.setInitParameters(initParams);
         filterRegistrationBean.addUrlPatterns("/*");
         return filterRegistrationBean;
     }
     /**
      * 配置DruidDataSource
      * @return
      * @throws SQLException
      */
     @Bean
     public DruidDataSource druidDataSource() throws SQLException {
         DruidDataSource druidDataSource = new DruidDataSource();
         druidDataSource.setUsername("jxc");
         druidDataSource.setPassword("jxc123");
         druidDataSource.setUrl("jdbc:mysql://192.168.7.8:3306/test?useUnicode=true&characterEncoding=UTF-8&useSSL=false");
         druidDataSource.setMaxActive(100);
         druidDataSource.setFilters("stat,wall");
         druidDataSource.setInitialSize(10);
         return druidDataSource;
     }
    @Bean
    public OpenEntityManagerInViewFilter openEntityManagerInViewFilter() {
        return new OpenEntityManagerInViewFilter();
    }
}
