package com.d.clinic.repository;

import com.d.clinic.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Integer> {



    @Query(value = "SELECT * FROM patients p WHERE p.name LIKE %?1%"+
                        " or p.surname LIKE %?1%"+
                        " or p.address LIKE %?1%"+
                        " OR CONCAT(p.date, '') LIKE %?1% and deleted=0", nativeQuery = true)
    Page<Patient> search(String keyword, Pageable pageable);


    @Query(value = "SELECT * FROM patients p WHERE p.name LIKE %?1%"+
            " or p.surname LIKE %?1%"+
            " or p.address LIKE %?1%"+
            " OR CONCAT(p.date, '') LIKE %?1% and deleted=1", nativeQuery = true)
    Page<Patient> searchDeleted(String keyword, Pageable pageable);

    @Query(value="select *from patients where deleted=1",nativeQuery = true)
    Page<Patient> findpatientsDeleted(Pageable pageable);


    @Query(value="select * from patients where deleted=0 ORDER BY id DESC LIMIT 1",nativeQuery = true)
    List<Patient> findLastInfo();


    @Query(value = "select * from patients where today_date=curdate()  and  deleted=0",nativeQuery=true)
    Page<Patient> findDailyPatients(Pageable pageable);

    @Query(value = "SELECT * FROM patients p WHERE p.today_date LIKE %?1% and deleted=0", nativeQuery = true)
    Page<Patient> searchToday(String keyword, Pageable pageable);

    @Query(value = "select * from patients where deleted=0",nativeQuery=true)
    Page<Patient> findAllNotDeleted(Pageable pageable);


    @Modifying
    @Transactional
    @Query(value = "update patients p set p.came_number=p.came_number+1 where p.id = ?1  and deleted=0",nativeQuery = true)
    void updateCameNumber(Integer id);

    @Modifying
    @Transactional
    @Query(value = "update patients p set p.deleted=1 where p.id = ?1",nativeQuery = true)
    void removepatient(Integer id);

    @Modifying
    @Transactional
    @Query(value = "update patients p set p.deleted=0 where p.id = ?1",nativeQuery = true)
    void restorepatient(Integer id);

    @Modifying
    @Transactional
    @Query(value = "delete from patients p where p.deleted=1",nativeQuery = true)
    void emptyTrash();


    @Query(value = "select * from patients p where p.today_date = ?1  and deleted=0",nativeQuery = true)
    List<Patient> findBydate(String dateExcel);


}
