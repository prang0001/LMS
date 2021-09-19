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

/**
 * Criteria class for the LibraryResource entity. This class is used in LibraryResourceResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /library-resources?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LibraryResourceCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter resourceTitle;

    private StringFilter resourceDescription;

    private StringFilter callNumber;

    private LongFilter authorId;

    private LongFilter subjectId;

    private LongFilter resourceStatusId;

    private LongFilter resourceTypeId;

    private LongFilter rentalTransactionId;

    private LongFilter waitingListId;

    public LibraryResourceCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getResourceTitle() {
        return resourceTitle;
    }

    public void setResourceTitle(StringFilter resourceTitle) {
        this.resourceTitle = resourceTitle;
    }

    public StringFilter getResourceDescription() {
        return resourceDescription;
    }

    public void setResourceDescription(StringFilter resourceDescription) {
        this.resourceDescription = resourceDescription;
    }

    public StringFilter getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(StringFilter callNumber) {
        this.callNumber = callNumber;
    }

    public LongFilter getAuthorId() {
        return authorId;
    }

    public void setAuthorId(LongFilter authorId) {
        this.authorId = authorId;
    }

    public LongFilter getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(LongFilter subjectId) {
        this.subjectId = subjectId;
    }

    public LongFilter getResourceStatusId() {
        return resourceStatusId;
    }

    public void setResourceStatusId(LongFilter resourceStatusId) {
        this.resourceStatusId = resourceStatusId;
    }

    public LongFilter getResourceTypeId() {
        return resourceTypeId;
    }

    public void setResourceTypeId(LongFilter resourceTypeId) {
        this.resourceTypeId = resourceTypeId;
    }

    public LongFilter getRentalTransactionId() {
        return rentalTransactionId;
    }

    public void setRentalTransactionId(LongFilter rentalTransactionId) {
        this.rentalTransactionId = rentalTransactionId;
    }

    public LongFilter getWaitingListId() {
        return waitingListId;
    }

    public void setWaitingListId(LongFilter waitingListId) {
        this.waitingListId = waitingListId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LibraryResourceCriteria that = (LibraryResourceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(resourceTitle, that.resourceTitle) &&
            Objects.equals(resourceDescription, that.resourceDescription) &&
            Objects.equals(callNumber, that.callNumber) &&
            Objects.equals(authorId, that.authorId) &&
            Objects.equals(subjectId, that.subjectId) &&
            Objects.equals(resourceStatusId, that.resourceStatusId) &&
            Objects.equals(resourceTypeId, that.resourceTypeId) &&
            Objects.equals(rentalTransactionId, that.rentalTransactionId) &&
            Objects.equals(waitingListId, that.waitingListId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        resourceTitle,
        resourceDescription,
        callNumber,
        authorId,
        subjectId,
        resourceStatusId,
        resourceTypeId,
        rentalTransactionId,
        waitingListId
        );
    }

    @Override
    public String toString() {
        return "LibraryResourceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (resourceTitle != null ? "resourceTitle=" + resourceTitle + ", " : "") +
                (resourceDescription != null ? "resourceDescription=" + resourceDescription + ", " : "") +
                (callNumber != null ? "callNumber=" + callNumber + ", " : "") +
                (authorId != null ? "authorId=" + authorId + ", " : "") +
                (subjectId != null ? "subjectId=" + subjectId + ", " : "") +
                (resourceStatusId != null ? "resourceStatusId=" + resourceStatusId + ", " : "") +
                (resourceTypeId != null ? "resourceTypeId=" + resourceTypeId + ", " : "") +
                (rentalTransactionId != null ? "rentalTransactionId=" + rentalTransactionId + ", " : "") +
                (waitingListId != null ? "waitingListId=" + waitingListId + ", " : "") +
            "}";
    }

}
