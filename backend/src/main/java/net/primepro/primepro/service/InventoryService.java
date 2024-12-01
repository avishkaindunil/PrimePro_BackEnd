package net.primepro.primepro.service;

import net.primepro.primepro.entity.Inventory;

import java.util.List;

public interface InventoryService {
    Inventory addInventory(Long productId, Long branchId, int quantity);
    Inventory updateInventoryQuantity(Long productId, Long branchId, int newQuantity);
    Inventory adjustInventoryQuantity(Long productId, Long branchId, int adjustment);
    List<Inventory> getInventoryReport(Long branchId);
}
