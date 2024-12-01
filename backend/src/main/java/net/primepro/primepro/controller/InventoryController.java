package net.primepro.primepro.controller;

import net.primepro.primepro.entity.Inventory;
import net.primepro.primepro.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    // Add inventory for a branch
    @PostMapping("/add")
    public ResponseEntity<?> addInventory(@RequestParam Long productId, @RequestParam Long branchId, @RequestParam int quantity) {
        try {
            if(productId == null || branchId == null || quantity <= 0) {
                return ResponseEntity.badRequest().body("productId, branchId, quantity cannot be null or zero");
            }
            Inventory inventory = inventoryService.addInventory(productId, branchId, quantity);
            return ResponseEntity.ok(inventory);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

    }

    // Update inventory quantity for a branch
    @PutMapping("/updateQuantity")
    public ResponseEntity<?> updateInventoryQuantity(@RequestParam Long productId, @RequestParam Long branchId, @RequestParam int quantity) {
        try {
            if (productId == null || branchId == null || quantity <= 0) {
                return ResponseEntity.badRequest().body("productId, branchId, quantity cannot be null or zero");
            }
            Inventory updatedInventory = inventoryService.updateInventoryQuantity(productId, branchId, quantity);
            return ResponseEntity.ok(updatedInventory);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/adjustQuantity")
    public ResponseEntity<?> adjustInventoryQuantity(@RequestParam Long productId, @RequestParam Long branchId, @RequestParam int adjustQuantity) {
        try {
            if (productId == null || branchId == null || adjustQuantity <= 0) {
                return ResponseEntity.badRequest().body("productId, branchId, quantity cannot be null or zero");
            }
            Inventory updatedInventory = inventoryService.adjustInventoryQuantity(productId, branchId, adjustQuantity);
            return ResponseEntity.ok(updatedInventory);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // Generate inventory report for a branch
    @GetMapping("/report/{branchId}")
    public ResponseEntity<?> getInventoryReport(@PathVariable Long branchId) {
        try {
            List<Inventory> report = inventoryService.getInventoryReport(branchId);
            return ResponseEntity.ok(report);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
