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
 * Criteria class for the ResourceType entity. This class is used in ResourceTypeResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /resource-types?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ResourceTypeCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter resourceTypeName;

    private IntegerFilter rentalDuration;

    private DoubleFilter overdueCharge;

    private LongFilter libraryResourceId;

    public ResourceTypeCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getResourceTypeName() {
        return resourceTypeName;
    }

    public void setResourceTypeName(StringFilter resourceTypeName) {
        this.resourceTypeName = resourceTypeName;
    }

    public IntegerFilter getRentalDuration() {
        return rentalDuration;
    }

    public void setRentalDuration(IntegerFilter rentalDuration) {
        this.rentalDuration = rentalDuration;
    }

    public DoubleFilter getOverdueCharge() {
        return overdueCharge;
    }

    public void setOverdueCharge(DoubleFilter overdueCharge) {
        this.overdueCharge = overdueCharge;
    }

    public LongFilter getLibraryResourceId() {
        return libraryResourceId;
    }

    public void setLibraryResourceId(LongFilter libraryResourceId) {
        this.libraryResourceId = libraryResourceId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ResourceTypeCriteria that = (ResourceTypeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(resourceTypeName, that.resourceTypeName) &&
            Objects.equals(rentalDuration, that.rentalDuration) &&
            Objects.equals(overdueCharge, that.overdueCharge) &&
            Objects.equals(libraryResourceId, that.libraryResourceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        resourceTypeName,
        rentalDuration,
        overdueCharge,
        libraryResourceId
        );
    }

    @Override
    public String toString() {
        return "ResourceTypeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (resourceTypeName != null ? "resourceTypeName=" + resourceTypeName + ", " : "") +
                (rentalDuration != null ? "rentalDuration=" + rentalDuration + ", " : "") +
                (overdueCharge != null ? "overdueCharge=" + overdueCharge + ", " : "") +
                (libraryResourceId != null ? "libraryResourceId=" + libraryResourceId + ", " : "") +
            "}";
    }

}
