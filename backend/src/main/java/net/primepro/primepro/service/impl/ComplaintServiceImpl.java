package net.primepro.primepro.service.impl;


import lombok.AllArgsConstructor;
import net.primepro.primepro.dto.ReqRes;
import net.primepro.primepro.entity.Complaints;
import net.primepro.primepro.repository.ComplaintRepo;
import net.primepro.primepro.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    private ComplaintRepo complaintRepo;

    @Override
    public Complaints addComplaint(Complaints complaints) {
        Complaints newComplaint = new Complaints();

        newComplaint.setComplaintId(complaints.getComplaintId());
        newComplaint.setUserID(complaints.getUserID());
        newComplaint.setComplaint(complaints.getComplaint());
        newComplaint.setMobile(complaints.getMobile());

        Complaints savedComplaint = complaintRepo.save(newComplaint);

        return  savedComplaint;
    }

    @Override
    public void deleteComplaint(Integer integer) {
        complaintRepo.deleteById(integer);

    }

    @Override
    public List<Complaints> getComplaints() {
        return complaintRepo.findAll();
    }

    @Override
    public Complaints updateComplaint(Integer integer, Complaints complaints) {

//            Complaints  newComplaint = new Complaints();

        complaints.setUserID(complaints.getUserID());
        complaints.setComplaint(complaints.getComplaint());
        complaints.setComplaintId(integer);
        complaints.setMobile(complaints.getMobile());

        Complaints updatedComplaint = complaintRepo.save(complaints);
        return updatedComplaint;

    }

    @Override
    public List<Complaints> getUnresolvedComplaints() {
        return complaintRepo.findByIsResolvedFalse();
    }

    @Override
    public boolean markAsResolved(Integer id) {
        Optional<Complaints> complaint = complaintRepo.findById(id);
        if (complaint.isPresent()) {
            Complaints updatedComplaint = complaint.get();
            updatedComplaint.setIsResolved(true);
            complaintRepo.save(updatedComplaint);
            return true;
        }
        return false;
    }


}
