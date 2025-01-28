package net.primepro.primepro.service;

import net.primepro.primepro.entity.Complaints;

import java.util.List;

public interface ComplaintService {

    Complaints addComplaint(Complaints complaints);
    void deleteComplaint(Integer integer);
    List<Complaints> getComplaints();
    Complaints updateComplaint(Integer integer, Complaints complaints);
    List<Complaints> getUnresolvedComplaints();
    boolean markAsResolved(Integer id);
}
