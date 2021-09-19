package com.brandeis.lmsapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A WaitingList.
 */
@Entity
@Table(name = "waiting_list")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WaitingList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_request")
    private LocalDate dateRequest;

    @Column(name = "requested")
    private Boolean requested;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "waiting_list_library_resource",
               joinColumns = @JoinColumn(name = "waiting_lists_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "library_resources_id", referencedColumnName = "id"))
    private Set<LibraryResource> libraryResources = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "waiting_list_patron",
               joinColumns = @JoinColumn(name = "waiting_lists_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "patrons_id", referencedColumnName = "id"))
    private Set<Patron> patrons = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateRequest() {
        return dateRequest;
    }

    public WaitingList dateRequest(LocalDate dateRequest) {
        this.dateRequest = dateRequest;
        return this;
    }

    public void setDateRequest(LocalDate dateRequest) {
        this.dateRequest = dateRequest;
    }

    public Boolean isRequested() {
        return requested;
    }

    public WaitingList requested(Boolean requested) {
        this.requested = requested;
        return this;
    }

    public void setRequested(Boolean requested) {
        this.requested = requested;
    }

    public Set<LibraryResource> getLibraryResources() {
        return libraryResources;
    }

    public WaitingList libraryResources(Set<LibraryResource> libraryResources) {
        this.libraryResources = libraryResources;
        return this;
    }

    public WaitingList addLibraryResource(LibraryResource libraryResource) {
        this.libraryResources.add(libraryResource);
        libraryResource.getWaitingLists().add(this);
        return this;
    }

    public WaitingList removeLibraryResource(LibraryResource libraryResource) {
        this.libraryResources.remove(libraryResource);
        libraryResource.getWaitingLists().remove(this);
        return this;
    }

    public void setLibraryResources(Set<LibraryResource> libraryResources) {
        this.libraryResources = libraryResources;
    }

    public Set<Patron> getPatrons() {
        return patrons;
    }

    public WaitingList patrons(Set<Patron> patrons) {
        this.patrons = patrons;
        return this;
    }

    public WaitingList addPatron(Patron patron) {
        this.patrons.add(patron);
        patron.getWaitingLists().add(this);
        return this;
    }

    public WaitingList removePatron(Patron patron) {
        this.patrons.remove(patron);
        patron.getWaitingLists().remove(this);
        return this;
    }

    public void setPatrons(Set<Patron> patrons) {
        this.patrons = patrons;
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
        WaitingList waitingList = (WaitingList) o;
        if (waitingList.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), waitingList.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WaitingList{" +
            "id=" + getId() +
            ", dateRequest='" + getDateRequest() + "'" +
            ", requested='" + isRequested() + "'" +
            "}";
    }
}
