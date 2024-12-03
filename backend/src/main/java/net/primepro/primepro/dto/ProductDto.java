package net.primepro.primepro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.primepro.primepro.entity.Brand;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDto {
    private String productId;
    private String name;
    private Brand brand;
    private int quantity;
    private String unitType;
}
