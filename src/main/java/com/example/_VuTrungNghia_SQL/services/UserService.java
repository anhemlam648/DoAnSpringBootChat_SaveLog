package com.example._VuTrungNghia_SQL.services;


import com.example._VuTrungNghia_SQL.entity.Role;
import com.example._VuTrungNghia_SQL.entity.User;
import com.example._VuTrungNghia_SQL.repository.IroleRepository;
import com.example._VuTrungNghia_SQL.repository.IuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

    @Autowired
    private IuserRepository userRepository;

    @Autowired
    private IroleRepository roleRepository;
    public void save(User user)
    {
        userRepository.save(user);
        Long userId = userRepository.getUserIdByUsername(user.getUsername());
        Long roleId = roleRepository.getRoleIdByName("USER");
        if(roleId !=0 && userId !=0)
        {
            userRepository.addRoleToUser(userId,roleId);
        }
    }
    public void saveWithUserRole(User user)
    {
        Role userRole = roleRepository.getRoleByName("USER");
        user.setRoles(Collections.singleton(userRole));
        save(user);
    }


}
