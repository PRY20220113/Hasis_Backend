package com.upc.hasis_backend.repository;

import com.upc.hasis_backend.model.Doctor;
import com.upc.hasis_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    @Query("select u from User u where u.dni = ?1")
    public User findByDni(String dni);


}