package com.inditex.product.offer.adapter.outbound.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a Brand in the system. Maps to the "BRAND" table in the database.
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
@Entity
@Table(name = "BRAND")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandEntity {

    @Id
    private Integer id;

    @Column(name = "BRAND_NAME")
    private String brandName;

}
