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
 * Criteria class for the Author entity. This class is used in AuthorResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /authors?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AuthorCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter authorName;

    private LongFilter libraryResourceId;

    public AuthorCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAuthorName() {
        return authorName;
    }

    public void setAuthorName(StringFilter authorName) {
        this.authorName = authorName;
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
        final AuthorCriteria that = (AuthorCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(authorName, that.authorName) &&
            Objects.equals(libraryResourceId, that.libraryResourceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        authorName,
        libraryResourceId
        );
    }

    @Override
    public String toString() {
        return "AuthorCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (authorName != null ? "authorName=" + authorName + ", " : "") +
                (libraryResourceId != null ? "libraryResourceId=" + libraryResourceId + ", " : "") +
            "}";
    }

}
