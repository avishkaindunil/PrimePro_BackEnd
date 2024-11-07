package net.primepro.primepro.service;

import net.primepro.primepro.entity.Inventory;

import java.util.List;

public interface InventoryService {

    Inventory addInventory(Inventory inventory);
    Long DeleteInventory(Long InventoryID);
    List<Inventory> viewInventory();
    Inventory updateInventory(Long Long, Inventory inventory);

}
