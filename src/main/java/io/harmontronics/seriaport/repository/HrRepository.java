package io.harmontronics.seriaport.repository;

import io.harmontronics.seriaport.entity.Hr;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HrRepository extends JpaRepository<Hr,Long> {
}
