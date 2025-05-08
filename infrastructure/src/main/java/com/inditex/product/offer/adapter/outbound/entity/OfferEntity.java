package com.inditex.product.offer.adapter.outbound.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing an Offer in the system. Maps to the "OFFER" table in the database. This entity contains details
 * about a specific offer, including its associated brand, validity period, pricing, and priority.
 *
 * @author [product-offers@inditex.es]
 * @since 1.0.0
 */
@Entity
@Table(name = "OFFER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferEntity {

    @Id
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "BRAND_ID", referencedColumnName = "ID")
    private BrandEntity brandEntity;

    @Column(name = "START_DATE")
    private ZonedDateTime startDate;

    @Column(name = "END_DATE")
    private ZonedDateTime endDate;

    @Column(name = "PRICE_LIST")
    private Long priceListId;

    @Column(name = "PART_NUMBER")
    private String productPartNumber;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "CURR")
    private String currencyIso;
}

