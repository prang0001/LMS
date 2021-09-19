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
 * A Subject.
 */
@Entity
@Table(name = "subject")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Subject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "subject_name", nullable = false)
    private String subjectName;

    @OneToMany(mappedBy = "subject")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LibraryResource> libraryResources = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public Subject subjectName(String subjectName) {
        this.subjectName = subjectName;
        return this;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Set<LibraryResource> getLibraryResources() {
        return libraryResources;
    }

    public Subject libraryResources(Set<LibraryResource> libraryResources) {
        this.libraryResources = libraryResources;
        return this;
    }

    public Subject addLibraryResource(LibraryResource libraryResource) {
        this.libraryResources.add(libraryResource);
        libraryResource.setSubject(this);
        return this;
    }

    public Subject removeLibraryResource(LibraryResource libraryResource) {
        this.libraryResources.remove(libraryResource);
        libraryResource.setSubject(null);
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
        Subject subject = (Subject) o;
        if (subject.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), subject.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Subject{" +
            "id=" + getId() +
            ", subjectName='" + getSubjectName() + "'" +
            "}";
    }
}
