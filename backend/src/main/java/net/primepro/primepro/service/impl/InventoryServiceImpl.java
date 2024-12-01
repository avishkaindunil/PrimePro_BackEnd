package net.primepro.primepro.service.impl;

import net.primepro.primepro.entity.Branch;
import net.primepro.primepro.entity.Inventory;
import net.primepro.primepro.entity.Product;
import net.primepro.primepro.repository.BranchRepository;
import net.primepro.primepro.repository.InventoryRepository;
import net.primepro.primepro.repository.ProductRepository;
import net.primepro.primepro.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BranchRepository branchRepository;

    public Inventory addInventory(Long productId, Long branchId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new IllegalArgumentException("Branch not found"));

        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setBranch(branch);
        inventory.setQuantity(quantity);

        return inventoryRepository.save(inventory);
    }

    public Inventory updateInventoryQuantity(Long productId, Long branchId, int newQuantity) {
        Inventory inventory = inventoryRepository.findByProductIdAndBranchId(productId, branchId)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found for this branch and product"));

        inventory.setQuantity(newQuantity);

        return inventoryRepository.save(inventory);
    }

    public Inventory adjustInventoryQuantity(Long productId, Long branchId, int adjustment) {
        Inventory inventory = inventoryRepository.findByProductIdAndBranchId(productId, branchId)
                .orElseThrow(() -> new IllegalArgumentException("Inventory not found for this branch and product"));

        inventory.setQuantity(inventory.getQuantity() + adjustment);

        return inventoryRepository.save(inventory);
    }

    public List<Inventory> getInventoryReport(Long branchId) {
        Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() -> new IllegalArgumentException("Branch not found"));

        return inventoryRepository.findByBranchId(branchId);
    }
}
