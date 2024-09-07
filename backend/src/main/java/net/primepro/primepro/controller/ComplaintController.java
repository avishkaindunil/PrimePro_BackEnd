package net.primepro.primepro.controller;


import jakarta.persistence.criteria.CriteriaBuilder;
import net.primepro.primepro.entity.Complaints;
import net.primepro.primepro.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;


    @PostMapping("/complaints/add")
    public String addComplaining(@RequestBody Complaints complaints){
        complaintService.addComplaint(complaints);
        return "complaint added successfully";
    }


    @DeleteMapping("/complaints/delete/{id}")
    public String deleteComplaining(@PathVariable("id") Integer id) {
        complaintService.deleteComplaint(id);
        return "Complaint deleted";
    }


    @GetMapping("complaints/get-all")
    public List<Complaints> getAllComplaints(){
        return complaintService.getComplaints();
    }

    @PostMapping("complaints/update/{id}")
    public Complaints updateComplaint(@PathVariable("id") Integer id ,@RequestBody Complaints complaints){
          return complaintService.updateComplaint(id, complaints);

    }



}
