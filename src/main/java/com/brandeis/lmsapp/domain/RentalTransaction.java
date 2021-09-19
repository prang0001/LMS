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
 * A RentalTransaction.
 */
@Entity
@Table(name = "rental_transaction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RentalTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rental_period")
    private Integer rentalPeriod;

    @Column(name = "rental_start_date")
    private LocalDate rentalStartDate;

    @Column(name = "rental_due_date")
    private LocalDate rentalDueDate;

    @Column(name = "extend_rental")
    private Boolean extendRental;

    @Column(name = "rental_final_due_date")
    private LocalDate rentalFinalDueDate;

    @Column(name = "overdue")
    private Boolean overdue;

    @Column(name = "days_overdue")
    private Integer daysOverdue;

    @Column(name = "fines_overdue")
    private Double finesOverdue;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "rental_transaction_library_resource",
               joinColumns = @JoinColumn(name = "rental_transactions_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "library_resources_id", referencedColumnName = "id"))
    private Set<LibraryResource> libraryResources = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "rental_transaction_patron",
               joinColumns = @JoinColumn(name = "rental_transactions_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "patrons_id", referencedColumnName = "id"))
    private Set<Patron> patrons = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRentalPeriod() {
        return rentalPeriod;
    }

    public RentalTransaction rentalPeriod(Integer rentalPeriod) {
        this.rentalPeriod = rentalPeriod;
        return this;
    }

    public void setRentalPeriod(Integer rentalPeriod) {
        this.rentalPeriod = rentalPeriod;
    }

    public LocalDate getRentalStartDate() {
        return rentalStartDate;
    }

    public RentalTransaction rentalStartDate(LocalDate rentalStartDate) {
        this.rentalStartDate = rentalStartDate;
        return this;
    }

    public void setRentalStartDate(LocalDate rentalStartDate) {
        this.rentalStartDate = rentalStartDate;
    }

    public LocalDate getRentalDueDate() {
        return rentalDueDate;
    }

    public RentalTransaction rentalDueDate(LocalDate rentalDueDate) {
        this.rentalDueDate = rentalDueDate;
        return this;
    }

    public void setRentalDueDate(LocalDate rentalDueDate) {
        this.rentalDueDate = rentalDueDate;
    }

    public Boolean isExtendRental() {
        return extendRental;
    }

    public RentalTransaction extendRental(Boolean extendRental) {
        this.extendRental = extendRental;
        return this;
    }

    public void setExtendRental(Boolean extendRental) {
        this.extendRental = extendRental;
    }

    public LocalDate getRentalFinalDueDate() {
        return rentalFinalDueDate;
    }

    public RentalTransaction rentalFinalDueDate(LocalDate rentalFinalDueDate) {
        this.rentalFinalDueDate = rentalFinalDueDate;
        return this;
    }

    public void setRentalFinalDueDate(LocalDate rentalFinalDueDate) {
        this.rentalFinalDueDate = rentalFinalDueDate;
    }

    public Boolean isOverdue() {
        return overdue;
    }

    public RentalTransaction overdue(Boolean overdue) {
        this.overdue = overdue;
        return this;
    }

    public void setOverdue(Boolean overdue) {
        this.overdue = overdue;
    }

    public Integer getDaysOverdue() {
        return daysOverdue;
    }

    public RentalTransaction daysOverdue(Integer daysOverdue) {
        this.daysOverdue = daysOverdue;
        return this;
    }

    public void setDaysOverdue(Integer daysOverdue) {
        this.daysOverdue = daysOverdue;
    }

    public Double getFinesOverdue() {
        return finesOverdue;
    }

    public RentalTransaction finesOverdue(Double finesOverdue) {
        this.finesOverdue = finesOverdue;
        return this;
    }

    public void setFinesOverdue(Double finesOverdue) {
        this.finesOverdue = finesOverdue;
    }

    public Set<LibraryResource> getLibraryResources() {
        return libraryResources;
    }

    public RentalTransaction libraryResources(Set<LibraryResource> libraryResources) {
        this.libraryResources = libraryResources;
        return this;
    }

    public RentalTransaction addLibraryResource(LibraryResource libraryResource) {
        this.libraryResources.add(libraryResource);
        libraryResource.getRentalTransactions().add(this);
        return this;
    }

    public RentalTransaction removeLibraryResource(LibraryResource libraryResource) {
        this.libraryResources.remove(libraryResource);
        libraryResource.getRentalTransactions().remove(this);
        return this;
    }

    public void setLibraryResources(Set<LibraryResource> libraryResources) {
        this.libraryResources = libraryResources;
    }

    public Set<Patron> getPatrons() {
        return patrons;
    }

    public RentalTransaction patrons(Set<Patron> patrons) {
        this.patrons = patrons;
        return this;
    }

    public RentalTransaction addPatron(Patron patron) {
        this.patrons.add(patron);
        patron.getRentalTransactions().add(this);
        return this;
    }

    public RentalTransaction removePatron(Patron patron) {
        this.patrons.remove(patron);
        patron.getRentalTransactions().remove(this);
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
        RentalTransaction rentalTransaction = (RentalTransaction) o;
        if (rentalTransaction.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rentalTransaction.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RentalTransaction{" +
            "id=" + getId() +
            ", rentalPeriod=" + getRentalPeriod() +
            ", rentalStartDate='" + getRentalStartDate() + "'" +
            ", rentalDueDate='" + getRentalDueDate() + "'" +
            ", extendRental='" + isExtendRental() + "'" +
            ", rentalFinalDueDate='" + getRentalFinalDueDate() + "'" +
            ", overdue='" + isOverdue() + "'" +
            ", daysOverdue=" + getDaysOverdue() +
            ", finesOverdue=" + getFinesOverdue() +
            "}";
    }
}
