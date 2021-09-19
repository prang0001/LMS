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
 * Criteria class for the WaitingList entity. This class is used in WaitingListResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /waiting-lists?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class WaitingListCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter dateRequest;

    private BooleanFilter requested;

    private LongFilter libraryResourceId;

    private LongFilter patronId;

    public WaitingListCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getDateRequest() {
        return dateRequest;
    }

    public void setDateRequest(LocalDateFilter dateRequest) {
        this.dateRequest = dateRequest;
    }

    public BooleanFilter getRequested() {
        return requested;
    }

    public void setRequested(BooleanFilter requested) {
        this.requested = requested;
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
        final WaitingListCriteria that = (WaitingListCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(dateRequest, that.dateRequest) &&
            Objects.equals(requested, that.requested) &&
            Objects.equals(libraryResourceId, that.libraryResourceId) &&
            Objects.equals(patronId, that.patronId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        dateRequest,
        requested,
        libraryResourceId,
        patronId
        );
    }

    @Override
    public String toString() {
        return "WaitingListCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dateRequest != null ? "dateRequest=" + dateRequest + ", " : "") +
                (requested != null ? "requested=" + requested + ", " : "") +
                (libraryResourceId != null ? "libraryResourceId=" + libraryResourceId + ", " : "") +
                (patronId != null ? "patronId=" + patronId + ", " : "") +
            "}";
    }

}
