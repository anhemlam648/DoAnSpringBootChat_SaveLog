package com.example._VuTrungNghia_SQL.services;


import com.example._VuTrungNghia_SQL.entity.User;
import com.example._VuTrungNghia_SQL.repository.IuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private IuserRepository iuserRepository;

    public void save(User user){
        iuserRepository.save(user);
    }
}
