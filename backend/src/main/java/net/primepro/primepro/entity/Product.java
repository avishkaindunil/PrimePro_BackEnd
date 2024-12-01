package net.primepro.primepro.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productId;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders orders;

    private String name;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    private String description;
    private int quantity;
    private String unitType;
    private double price;
}
