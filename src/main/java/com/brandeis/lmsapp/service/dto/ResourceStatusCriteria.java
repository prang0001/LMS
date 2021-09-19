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
 * Criteria class for the ResourceStatus entity. This class is used in ResourceStatusResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /resource-statuses?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ResourceStatusCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter statusName;

    private LongFilter libraryResourceId;

    public ResourceStatusCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getStatusName() {
        return statusName;
    }

    public void setStatusName(StringFilter statusName) {
        this.statusName = statusName;
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
        final ResourceStatusCriteria that = (ResourceStatusCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(statusName, that.statusName) &&
            Objects.equals(libraryResourceId, that.libraryResourceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        statusName,
        libraryResourceId
        );
    }

    @Override
    public String toString() {
        return "ResourceStatusCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (statusName != null ? "statusName=" + statusName + ", " : "") +
                (libraryResourceId != null ? "libraryResourceId=" + libraryResourceId + ", " : "") +
            "}";
    }

}
