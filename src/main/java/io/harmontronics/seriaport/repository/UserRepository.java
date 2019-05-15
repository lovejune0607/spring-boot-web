package io.harmontronics.seriaport.repository;

import io.harmontronics.seriaport.entity.User1;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  UserRepository extends JpaRepository<User1,Integer> {


}
