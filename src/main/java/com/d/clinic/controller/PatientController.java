package com.d.clinic.controller;

import com.d.clinic.entity.Patient;
import com.d.clinic.excel.KunlikExcel;
import com.d.clinic.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class PatientController {

    @Autowired
    private PatientService patientService;


    @GetMapping("/d/excel")
    public void debitor_ToExcel(HttpServletResponse response,
                                @Param("dailyExcel") String dailyExcel) throws IOException {
        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename="+dailyExcel+".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Patient> listUsers = patientService.listDailyExcel(dailyExcel);

        KunlikExcel excelExporter = new KunlikExcel(listUsers);

        excelExporter.export(response);
    }



    @RequestMapping(value = "/patientListChange")
    public String show_table(Model model) {

        String keyword = null;

        return list_patient(model, 1, keyword);
    }


    @GetMapping("/patientListChange/{pageNumber}")
    public String list_patient(Model model,
                               @PathVariable("pageNumber") int currentPage
            , @Param("keyword") String keyword) {



        try {
            Page<Patient> page = patientService.patientTable(currentPage, keyword);
            List<Patient> listmalumot = page.getContent();
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();

            model.addAttribute("totalItems", totalItems);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("listPatients", listmalumot);
            model.addAttribute("keyword", keyword);

        } catch (Exception e) {

            return "error";
        }
        return "patientTable";

    }



    @RequestMapping(value = "/patientListDeleted")
    public String show_table_deleted(Model model) {

        String keyword = null;

        return list_patient_deleted(model, 1, keyword);
    }


    @GetMapping("/patientListDeleted/{pageNumber}")
    public String list_patient_deleted(Model model,
                               @PathVariable("pageNumber") int currentPage
            , @Param("keyword") String keyword) {



        try {
            Page<Patient> page = patientService.patientTableDeleted(currentPage, keyword);
            List<Patient> listmalumot = page.getContent();
            long totalItems = page.getTotalElements();
            int totalPages = page.getTotalPages();

            model.addAttribute("totalItems", totalItems);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("listPatients", listmalumot);
            model.addAttribute("keyword", keyword);

        } catch (Exception e) {

            return "error";
        }
        return "patientTableDeleted";

    }



    @RequestMapping(value = "/patientListToday")
    public String show_table_today(Model model) {

        String keyword = null;

        return list_patient_today(model, 1, keyword);
    }


    @GetMapping("/patientListToday/{pageNumber}")
    public String list_patient_today(Model model,
                               @PathVariable("pageNumber") int currentPage
            , @Param("keyword") String keyword) {



        try {
            Page<Patient> page1 = patientService.patientTodaysTable(currentPage, keyword);
            List<Patient> listmalumot = page1.getContent();
            long totalItems = page1.getTotalElements();
            int totalPages = page1.getTotalPages();

            model.addAttribute("totalItems", totalItems);
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("listPatients", listmalumot);
            model.addAttribute("keyword", keyword);

        } catch (Exception e) {

            return "error";
        }
        return "patientTableToday";

    }


    @GetMapping("/patientList")
    public String patientListforChange(Model model){
        List<Patient> patientsList = patientService.finddLast();
       Patient patient = new Patient();

        model.addAttribute("newPatient", patient);
        model.addAttribute("listpatient", patientsList);
        return "patient-register";
    }

    @RequestMapping(value = "/saveDaily", method = RequestMethod.POST)
    public String savePatient(@ModelAttribute("newPatient") Patient patient) {

        try {
            if(patient.getId()==null) {
                patientService.addPatient(patient);
            }else {
                patientService.editPatientSave(patient);
            }
        } catch (Exception e) {
            System.out.println("Error ");
            return "error";
        }
        return "redirect:/patientList";
    }


    @RequestMapping(value = "/saveDaily2", method = RequestMethod.POST)
    public String savePatient2(@ModelAttribute("newPatient") Patient patient) {

        try {
            if(patient.getId()==null) {
                patientService.addPatient(patient);
            }else {
                patientService.editPatientSave(patient);
            }
        } catch (Exception e) {
            System.out.println("Error ");
            return "error";
        }
        return "redirect:/patientListChange";
    }

    @GetMapping("/deletePatientFromTrash/{id}")
    public String deletetFromTrash(@PathVariable(name = "id") Integer id) {
        patientService.deletePatientFromTrash(id);
        return "redirect:/patientListDeleted";
    }

    @GetMapping("/emptyFromTrash")
    public String deletetedTrash() {
        patientService.deleteTrash();
        return "redirect:/patientListDeleted";
    }


    @GetMapping("/deletePatient/{id}")
    public String deletet(@PathVariable(name = "id") Integer id) {
        patientService.deletePatient(id);
        return "redirect:/patientList";
    }



    @GetMapping("/deletePatient2/{id}")
    public String deletet2(@PathVariable(name = "id") Integer id) {
        patientService.deletePatient(id);
        return "redirect:/patientListChange";
    }

    @GetMapping("/restorePatient/{id}")
    public String restore(@PathVariable(name = "id") Integer id) {
        patientService.restorePatient(id);
        return "redirect:/patientListDeleted";
    }




    @RequestMapping("/updatePatient/{id}")
    public ModelAndView showEditPatient(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("patient-update");
        Patient patient = patientService.getPatientById(id);
        mav.addObject("editPatient", patient);

        return mav;
    }


    @RequestMapping("/updatePatient2/{id}")
    public ModelAndView showEditPatient2(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("patient-update-2");
        Patient patient = patientService.getPatientById(id);
        mav.addObject("editPatient", patient);

        return mav;
    }


    @RequestMapping("/updatePatientForCame/{id}")
    public ModelAndView EditPatientCame(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("redirect:/patientListChange");
        patientService.updatePatientForCame(id);
        return mav;
    }



}
