package io.harmontronics.seriaport.repository;

import io.harmontronics.seriaport.entity.WatchMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<WatchMessage,Long> {

}
