package com.application.repositories;

import com.application.dataClasses.UserData;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserData,Integer>{


    UserData searchByEmail(String email);
    List<UserData> findAllByEmail(@NotNull(message = "Email cant be null") String email);
}
