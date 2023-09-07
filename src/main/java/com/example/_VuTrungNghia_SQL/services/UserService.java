package com.example._VuTrungNghia_SQL.services;



import com.example._VuTrungNghia_SQL.entity.Book;
import com.example._VuTrungNghia_SQL.entity.ChatMessage;
import com.example._VuTrungNghia_SQL.entity.User;

//import com.example._VuTrungNghia_SQL.repository.IroleRepository;
import com.example._VuTrungNghia_SQL.repository.IuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private IuserRepository userRepository;


//    @Autowired
//    private IroleRepository roleRepository;
    public void save(User user) {
        userRepository.save(user);
    }
//        Long userId = userRepository.getUserIdByUsername(user.getUsername());
//        Long roleId = roleRepository.getRoleIdByName("USER");
//        if(roleId !=0 && userId !=0)
//        {
//            userRepository.addRoleToUser(userId,roleId);
//        }
//    public void saveWithUserRole(User user)
//    {
//        Role userRole = roleRepository.getRoleByName("USER");
//        user.setRoles(Collections.singleton(userRole));
//        save(user);
//    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
    public User getUserById(Long id){
        Optional<User> optional = userRepository.findById(id);
        return optional.orElse(null);
    }
    public List<User> getAllUser(){
        return userRepository.findAll();
    }
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
