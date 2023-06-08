package com.example._VuTrungNghia_SQL.repository;


import com.example._VuTrungNghia_SQL.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IroleRepository extends JpaRepository<Role,Long> {

    @Query("SELECT r.id FROM Role r WHERE r.name = ?1")
    Long getRoleIdByName(String roleName);
    @Query("SELECT r FROM Role r WHERE r.name =?1")
    Role getRoleByName(String name );
}
