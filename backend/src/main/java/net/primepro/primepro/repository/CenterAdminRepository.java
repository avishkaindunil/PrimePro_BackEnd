package net.primepro.primepro.repository;


import net.primepro.primepro.entity.CenterAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CenterAdminRepository extends JpaRepository<CenterAdmin,Integer> {
    Optional<CenterAdmin> findOneByEmailAndPassword(String email, String password);
    CenterAdmin findByEmail(String email);

    Optional<CenterAdmin> findByEmailAndPassword(String email, String encodedPassword);

    @Query(value = "select center_name from center_admin where id = ?",nativeQuery = true)
    String findCenterName(int centerId);
}
