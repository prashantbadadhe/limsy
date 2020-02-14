package com.radixile.limsy.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Dress.
 */
@Entity
@Table(name = "dress")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Dress implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "color")
    private String color;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "price")
    private Double price;

    @Column(name = "in_use")
    private Boolean inUse;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @OneToOne
    @JoinColumn(unique = true)
    private Category category;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public Dress color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public Dress purchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
        return this;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Double getPrice() {
        return price;
    }

    public Dress price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean isInUse() {
        return inUse;
    }

    public Dress inUse(Boolean inUse) {
        this.inUse = inUse;
        return this;
    }

    public void setInUse(Boolean inUse) {
        this.inUse = inUse;
    }

    public byte[] getImage() {
        return image;
    }

    public Dress image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Dress imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Category getCategory() {
        return category;
    }

    public Dress category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dress dress = (Dress) o;
        if (dress.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dress.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Dress{" +
            "id=" + getId() +
            ", color='" + getColor() + "'" +
            ", purchaseDate='" + getPurchaseDate() + "'" +
            ", price=" + getPrice() +
            ", inUse='" + isInUse() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
