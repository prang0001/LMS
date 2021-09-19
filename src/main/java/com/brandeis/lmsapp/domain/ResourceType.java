package com.brandeis.lmsapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ResourceType.
 */
@Entity
@Table(name = "resource_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ResourceType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "resource_type_name", nullable = false)
    private String resourceTypeName;

    @NotNull
    @Column(name = "rental_duration", nullable = false)
    private Integer rentalDuration;

    @NotNull
    @Column(name = "overdue_charge", nullable = false)
    private Double overdueCharge;

    @OneToMany(mappedBy = "resourceType")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LibraryResource> libraryResources = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResourceTypeName() {
        return resourceTypeName;
    }

    public ResourceType resourceTypeName(String resourceTypeName) {
        this.resourceTypeName = resourceTypeName;
        return this;
    }

    public void setResourceTypeName(String resourceTypeName) {
        this.resourceTypeName = resourceTypeName;
    }

    public Integer getRentalDuration() {
        return rentalDuration;
    }

    public ResourceType rentalDuration(Integer rentalDuration) {
        this.rentalDuration = rentalDuration;
        return this;
    }

    public void setRentalDuration(Integer rentalDuration) {
        this.rentalDuration = rentalDuration;
    }

    public Double getOverdueCharge() {
        return overdueCharge;
    }

    public ResourceType overdueCharge(Double overdueCharge) {
        this.overdueCharge = overdueCharge;
        return this;
    }

    public void setOverdueCharge(Double overdueCharge) {
        this.overdueCharge = overdueCharge;
    }

    public Set<LibraryResource> getLibraryResources() {
        return libraryResources;
    }

    public ResourceType libraryResources(Set<LibraryResource> libraryResources) {
        this.libraryResources = libraryResources;
        return this;
    }

    public ResourceType addLibraryResource(LibraryResource libraryResource) {
        this.libraryResources.add(libraryResource);
        libraryResource.setResourceType(this);
        return this;
    }

    public ResourceType removeLibraryResource(LibraryResource libraryResource) {
        this.libraryResources.remove(libraryResource);
        libraryResource.setResourceType(null);
        return this;
    }

    public void setLibraryResources(Set<LibraryResource> libraryResources) {
        this.libraryResources = libraryResources;
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
        ResourceType resourceType = (ResourceType) o;
        if (resourceType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resourceType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResourceType{" +
            "id=" + getId() +
            ", resourceTypeName='" + getResourceTypeName() + "'" +
            ", rentalDuration=" + getRentalDuration() +
            ", overdueCharge=" + getOverdueCharge() +
            "}";
    }
}
