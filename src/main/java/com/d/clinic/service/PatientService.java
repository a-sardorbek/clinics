package com.d.clinic.service;

import com.d.clinic.entity.Patient;
import com.d.clinic.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public void addPatient(Patient patient){
        patient.setDeleted(Boolean.FALSE);
        patient.setCameNumber(1);
        LocalDate localDate = LocalDate.now();
        patient.setTodayDate(String.valueOf(localDate));
        patientRepository.save(patient);
    }

    //--------------------------------------------------------------------------------

    public List<Patient> listDailyExcel(String dateExcel) {

        return patientRepository.findBydate(dateExcel);
    }

    //--------------------------------------------------------------------------------

    public void editPatientSave(Patient patient){
        patient.setDeleted(Boolean.FALSE);
        patientRepository.save(patient);
    }

    //--------------------------------------------------------------------------------


    public Page<Patient> patientTable(int pageNumber, String keyword){

          Sort sort = Sort.by("id").descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, 20,sort);

        if(keyword != null) {
            return patientRepository.search(keyword, pageable);
        }

        return patientRepository.findAllNotDeleted(pageable);
    }

      //--------------------------------------------------------------------------
    public Page<Patient> patientTodaysTable(int pageNumber, String keyword){

         Sort sort = Sort.by("id").descending();

        Pageable pageable = PageRequest.of(pageNumber - 1,500,sort);

        if(keyword != null) {
            return patientRepository.searchToday(keyword, pageable);
        }

        return patientRepository.findDailyPatients(pageable);
    }


    //--------------------------------------------------------------------------------


    public Page<Patient> patientTableDeleted(int pageNumber, String keyword){

        Sort sort = Sort.by("id").descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, 20,sort);

        if(keyword != null) {
            return patientRepository.searchDeleted(keyword, pageable);
        }

        return patientRepository.findpatientsDeleted(pageable);
    }



    //------------------------------------------------------------------------------


    public void updatePatientForCame(Integer id){

        patientRepository.updateCameNumber(id);

    }


    //------------------------------------------------------------------------------

    public List<Patient> findAllPatients(){
        return patientRepository.findAll();
    }

    //--------------------------------------------------------------------------------

    public List<Patient> finddLast(){
        return patientRepository.findLastInfo();
    }

    //--------------------------------------------------------------------------------

    public void deletePatient(Integer id){
        patientRepository.removepatient(id);
    }

    //--------------------------------------------------------------------------------

    public void deletePatientFromTrash(Integer id){
        patientRepository.deleteById(id);
    }

    //--------------------------------------------------------------------------------

    public void deleteTrash(){
        patientRepository.emptyTrash();
    }

    //--------------------------------------------------------------------------------

    public void restorePatient(Integer id) {
        patientRepository.restorepatient(id);
    }

    //--------------------------------------------------------------------------------


    public Patient getPatientById(Integer id) {
        Optional< Patient > optional = patientRepository.findById(id);
        Patient patient = null;
        if (optional.isPresent()) {
            patient = optional.get();
        } else {
            throw new RuntimeException(" Patient not found for id :: " + id);
        }
        return patient;
    }



}
