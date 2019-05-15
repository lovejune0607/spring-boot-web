package io.harmontronics.seriaport.repository;

import io.harmontronics.seriaport.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long>, JpaSpecificationExecutor<Role> {

    @Query(value = "select r.* from role r, hr_role ur where ur.hrid = ?1 and ur.rid = r.id", nativeQuery = true)
    public List<Role> findRolesOfUser(String hrid);

    @Query(value = "select r.* from role r,menu_role mr where mr.mid=?1 and mr.rid = r.id",nativeQuery = true)
    public List<Role> findRolesOfMenu(String mid);


}
