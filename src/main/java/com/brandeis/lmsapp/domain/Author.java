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
 * A Author.
 */
@Entity
@Table(name = "author")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Author implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "author_name", nullable = false)
    private String authorName;

    @OneToMany(mappedBy = "author")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<LibraryResource> libraryResources = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public Author authorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Set<LibraryResource> getLibraryResources() {
        return libraryResources;
    }

    public Author libraryResources(Set<LibraryResource> libraryResources) {
        this.libraryResources = libraryResources;
        return this;
    }

    public Author addLibraryResource(LibraryResource libraryResource) {
        this.libraryResources.add(libraryResource);
        libraryResource.setAuthor(this);
        return this;
    }

    public Author removeLibraryResource(LibraryResource libraryResource) {
        this.libraryResources.remove(libraryResource);
        libraryResource.setAuthor(null);
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
        Author author = (Author) o;
        if (author.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), author.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Author{" +
            "id=" + getId() +
            ", authorName='" + getAuthorName() + "'" +
            "}";
    }
}
