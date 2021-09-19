package com.brandeis.lmsapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the RentalTransaction entity. This class is used in RentalTransactionResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /rental-transactions?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RentalTransactionCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter rentalPeriod;

    private LocalDateFilter rentalStartDate;

    private LocalDateFilter rentalDueDate;

    private BooleanFilter extendRental;

    private LocalDateFilter rentalFinalDueDate;

    private BooleanFilter overdue;

    private IntegerFilter daysOverdue;

    private DoubleFilter finesOverdue;

    private LongFilter libraryResourceId;

    private LongFilter patronId;

    public RentalTransactionCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getRentalPeriod() {
        return rentalPeriod;
    }

    public void setRentalPeriod(IntegerFilter rentalPeriod) {
        this.rentalPeriod = rentalPeriod;
    }

    public LocalDateFilter getRentalStartDate() {
        return rentalStartDate;
    }

    public void setRentalStartDate(LocalDateFilter rentalStartDate) {
        this.rentalStartDate = rentalStartDate;
    }

    public LocalDateFilter getRentalDueDate() {
        return rentalDueDate;
    }

    public void setRentalDueDate(LocalDateFilter rentalDueDate) {
        this.rentalDueDate = rentalDueDate;
    }

    public BooleanFilter getExtendRental() {
        return extendRental;
    }

    public void setExtendRental(BooleanFilter extendRental) {
        this.extendRental = extendRental;
    }

    public LocalDateFilter getRentalFinalDueDate() {
        return rentalFinalDueDate;
    }

    public void setRentalFinalDueDate(LocalDateFilter rentalFinalDueDate) {
        this.rentalFinalDueDate = rentalFinalDueDate;
    }

    public BooleanFilter getOverdue() {
        return overdue;
    }

    public void setOverdue(BooleanFilter overdue) {
        this.overdue = overdue;
    }

    public IntegerFilter getDaysOverdue() {
        return daysOverdue;
    }

    public void setDaysOverdue(IntegerFilter daysOverdue) {
        this.daysOverdue = daysOverdue;
    }

    public DoubleFilter getFinesOverdue() {
        return finesOverdue;
    }

    public void setFinesOverdue(DoubleFilter finesOverdue) {
        this.finesOverdue = finesOverdue;
    }

    public LongFilter getLibraryResourceId() {
        return libraryResourceId;
    }

    public void setLibraryResourceId(LongFilter libraryResourceId) {
        this.libraryResourceId = libraryResourceId;
    }

    public LongFilter getPatronId() {
        return patronId;
    }

    public void setPatronId(LongFilter patronId) {
        this.patronId = patronId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RentalTransactionCriteria that = (RentalTransactionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(rentalPeriod, that.rentalPeriod) &&
            Objects.equals(rentalStartDate, that.rentalStartDate) &&
            Objects.equals(rentalDueDate, that.rentalDueDate) &&
            Objects.equals(extendRental, that.extendRental) &&
            Objects.equals(rentalFinalDueDate, that.rentalFinalDueDate) &&
            Objects.equals(overdue, that.overdue) &&
            Objects.equals(daysOverdue, that.daysOverdue) &&
            Objects.equals(finesOverdue, that.finesOverdue) &&
            Objects.equals(libraryResourceId, that.libraryResourceId) &&
            Objects.equals(patronId, that.patronId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        rentalPeriod,
        rentalStartDate,
        rentalDueDate,
        extendRental,
        rentalFinalDueDate,
        overdue,
        daysOverdue,
        finesOverdue,
        libraryResourceId,
        patronId
        );
    }

    @Override
    public String toString() {
        return "RentalTransactionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (rentalPeriod != null ? "rentalPeriod=" + rentalPeriod + ", " : "") +
                (rentalStartDate != null ? "rentalStartDate=" + rentalStartDate + ", " : "") +
                (rentalDueDate != null ? "rentalDueDate=" + rentalDueDate + ", " : "") +
                (extendRental != null ? "extendRental=" + extendRental + ", " : "") +
                (rentalFinalDueDate != null ? "rentalFinalDueDate=" + rentalFinalDueDate + ", " : "") +
                (overdue != null ? "overdue=" + overdue + ", " : "") +
                (daysOverdue != null ? "daysOverdue=" + daysOverdue + ", " : "") +
                (finesOverdue != null ? "finesOverdue=" + finesOverdue + ", " : "") +
                (libraryResourceId != null ? "libraryResourceId=" + libraryResourceId + ", " : "") +
                (patronId != null ? "patronId=" + patronId + ", " : "") +
            "}";
    }

}
