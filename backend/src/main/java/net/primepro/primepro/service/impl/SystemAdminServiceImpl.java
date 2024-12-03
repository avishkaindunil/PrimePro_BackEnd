package net.primepro.primepro.service.impl;

import lombok.AllArgsConstructor;
import net.primepro.primepro.entity.SystemAdmin;
import net.primepro.primepro.repository.SystemAdminRepo;
import net.primepro.primepro.service.SystemAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class SystemAdminServiceImpl implements SystemAdminService {

    @Autowired
    private SystemAdminRepo systemAdminRepo;

    @Override
    public SystemAdmin addSystemAdmin(SystemAdmin systemAdmin) {
        SystemAdmin newSystemAdmin = new SystemAdmin();

        newSystemAdmin.setId(systemAdmin.getId());
        newSystemAdmin.setEmail(systemAdmin.getEmail());

        return systemAdminRepo.save(newSystemAdmin);
    }
}
