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
 * A ResourceStatus.
 */
@Entity
@Table(name = "resource_status")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ResourceStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "status_name", nullable = false)
    private String statusName;

    @OneToMany(mappedBy = "resourceStatus")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LibraryResource> libraryResources = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public ResourceStatus statusName(String statusName) {
        this.statusName = statusName;
        return this;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Set<LibraryResource> getLibraryResources() {
        return libraryResources;
    }

    public ResourceStatus libraryResources(Set<LibraryResource> libraryResources) {
        this.libraryResources = libraryResources;
        return this;
    }

    public ResourceStatus addLibraryResource(LibraryResource libraryResource) {
        this.libraryResources.add(libraryResource);
        libraryResource.setResourceStatus(this);
        return this;
    }

    public ResourceStatus removeLibraryResource(LibraryResource libraryResource) {
        this.libraryResources.remove(libraryResource);
        libraryResource.setResourceStatus(null);
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
        ResourceStatus resourceStatus = (ResourceStatus) o;
        if (resourceStatus.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resourceStatus.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResourceStatus{" +
            "id=" + getId() +
            ", statusName='" + getStatusName() + "'" +
            "}";
    }
}
