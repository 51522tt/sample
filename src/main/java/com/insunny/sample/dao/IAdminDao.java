package com.insunny.sample.dao;

import com.insunny.sample.domian.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IAdminDao extends JpaRepository<Admin, Integer>, JpaSpecificationExecutor<Admin> {
    Admin findAdminByUsernameAndPassword(String username,String password);
}
