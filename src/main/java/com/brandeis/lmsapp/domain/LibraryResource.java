package com.brandeis.lmsapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A LibraryResource.
 */
@Entity
@Table(name = "library_resource")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LibraryResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "resource_title", nullable = false)
    private String resourceTitle;

    @Column(name = "resource_description")
    private String resourceDescription;

    @NotNull
    @Column(name = "call_number", nullable = false)
    private String callNumber;

    @ManyToOne
    @JsonIgnoreProperties("libraryResources")
    private Author author;

    @ManyToOne
    @JsonIgnoreProperties("libraryResources")
    private Subject subject;

    @ManyToOne
    @JsonIgnoreProperties("libraryResources")
    private ResourceStatus resourceStatus;

    @ManyToOne
    @JsonIgnoreProperties("libraryResources")
    private ResourceType resourceType;

    @ManyToMany(mappedBy = "libraryResources")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<RentalTransaction> rentalTransactions = new HashSet<>();

    @ManyToMany(mappedBy = "libraryResources")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<WaitingList> waitingLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResourceTitle() {
        return resourceTitle;
    }

    public LibraryResource resourceTitle(String resourceTitle) {
        this.resourceTitle = resourceTitle;
        return this;
    }

    public void setResourceTitle(String resourceTitle) {
        this.resourceTitle = resourceTitle;
    }

    public String getResourceDescription() {
        return resourceDescription;
    }

    public LibraryResource resourceDescription(String resourceDescription) {
        this.resourceDescription = resourceDescription;
        return this;
    }

    public void setResourceDescription(String resourceDescription) {
        this.resourceDescription = resourceDescription;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public LibraryResource callNumber(String callNumber) {
        this.callNumber = callNumber;
        return this;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public Author getAuthor() {
        return author;
    }

    public LibraryResource author(Author author) {
        this.author = author;
        return this;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Subject getSubject() {
        return subject;
    }

    public LibraryResource subject(Subject subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public ResourceStatus getResourceStatus() {
        return resourceStatus;
    }

    public LibraryResource resourceStatus(ResourceStatus resourceStatus) {
        this.resourceStatus = resourceStatus;
        return this;
    }

    public void setResourceStatus(ResourceStatus resourceStatus) {
        this.resourceStatus = resourceStatus;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public LibraryResource resourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
        return this;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public Set<RentalTransaction> getRentalTransactions() {
        return rentalTransactions;
    }

    public LibraryResource rentalTransactions(Set<RentalTransaction> rentalTransactions) {
        this.rentalTransactions = rentalTransactions;
        return this;
    }

    public LibraryResource addRentalTransaction(RentalTransaction rentalTransaction) {
        this.rentalTransactions.add(rentalTransaction);
        rentalTransaction.getLibraryResources().add(this);
        return this;
    }

    public LibraryResource removeRentalTransaction(RentalTransaction rentalTransaction) {
        this.rentalTransactions.remove(rentalTransaction);
        rentalTransaction.getLibraryResources().remove(this);
        return this;
    }

    public void setRentalTransactions(Set<RentalTransaction> rentalTransactions) {
        this.rentalTransactions = rentalTransactions;
    }

    public Set<WaitingList> getWaitingLists() {
        return waitingLists;
    }

    public LibraryResource waitingLists(Set<WaitingList> waitingLists) {
        this.waitingLists = waitingLists;
        return this;
    }

    public LibraryResource addWaitingList(WaitingList waitingList) {
        this.waitingLists.add(waitingList);
        waitingList.getLibraryResources().add(this);
        return this;
    }

    public LibraryResource removeWaitingList(WaitingList waitingList) {
        this.waitingLists.remove(waitingList);
        waitingList.getLibraryResources().remove(this);
        return this;
    }

    public void setWaitingLists(Set<WaitingList> waitingLists) {
        this.waitingLists = waitingLists;
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
        LibraryResource libraryResource = (LibraryResource) o;
        if (libraryResource.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), libraryResource.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LibraryResource{" +
            "id=" + getId() +
            ", resourceTitle='" + getResourceTitle() + "'" +
            ", resourceDescription='" + getResourceDescription() + "'" +
            ", callNumber='" + getCallNumber() + "'" +
            "}";
    }
}
