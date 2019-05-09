package com.zhou.service;

import com.zhou.entity.Role;
import com.zhou.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getRolesOfHr(String hrId){
        return roleRepository.findRolesOfUser(hrId);
    }

    public List<Role> getRolesOfMenu(String mid){
        return roleRepository.findRolesOfMenu(mid);
    }

}
