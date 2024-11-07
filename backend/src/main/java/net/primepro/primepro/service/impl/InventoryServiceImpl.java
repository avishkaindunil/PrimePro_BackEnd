package net.primepro.primepro.service.impl;


import lombok.AllArgsConstructor;
import net.primepro.primepro.entity.Inventory;
import net.primepro.primepro.repository.InventoryRepo;
import net.primepro.primepro.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.List;

@Service
@AllArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepo inventoryRepo;

    @Override
    public Inventory addInventory(Inventory inventory) {
        return null;
    }

    @Override
    public Long DeleteInventory(Long InventoryID) {
        return InventoryID;
    }

    @Override
    public List<Inventory> viewInventory() {
        return List.of();
    }

    @Override
    public Inventory updateInventory(Long Long, Inventory inventory) {
        return null;
    }
}
