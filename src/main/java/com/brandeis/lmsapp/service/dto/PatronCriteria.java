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
 * Criteria class for the Patron entity. This class is used in PatronResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /patrons?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PatronCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter patronStatus;

    private StringFilter firstName;

    private StringFilter middleName;

    private StringFilter lastName;

    private StringFilter streetAddress;

    private StringFilter mailingAddress;

    private StringFilter email;

    private StringFilter phoneNum1;

    private StringFilter phoneNum2;

    private StringFilter phoneNum3;

    private StringFilter login;

    private LongFilter rentalTransactionId;

    private LongFilter waitingListId;

    public PatronCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPatronStatus() {
        return patronStatus;
    }

    public void setPatronStatus(StringFilter patronStatus) {
        this.patronStatus = patronStatus;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getMiddleName() {
        return middleName;
    }

    public void setMiddleName(StringFilter middleName) {
        this.middleName = middleName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(StringFilter streetAddress) {
        this.streetAddress = streetAddress;
    }

    public StringFilter getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(StringFilter mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getPhoneNum1() {
        return phoneNum1;
    }

    public void setPhoneNum1(StringFilter phoneNum1) {
        this.phoneNum1 = phoneNum1;
    }

    public StringFilter getPhoneNum2() {
        return phoneNum2;
    }

    public void setPhoneNum2(StringFilter phoneNum2) {
        this.phoneNum2 = phoneNum2;
    }

    public StringFilter getPhoneNum3() {
        return phoneNum3;
    }

    public void setPhoneNum3(StringFilter phoneNum3) {
        this.phoneNum3 = phoneNum3;
    }

    public StringFilter getLogin() {
        return login;
    }

    public void setLogin(StringFilter login) {
        this.login = login;
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
        final PatronCriteria that = (PatronCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(patronStatus, that.patronStatus) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(middleName, that.middleName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(streetAddress, that.streetAddress) &&
            Objects.equals(mailingAddress, that.mailingAddress) &&
            Objects.equals(email, that.email) &&
            Objects.equals(phoneNum1, that.phoneNum1) &&
            Objects.equals(phoneNum2, that.phoneNum2) &&
            Objects.equals(phoneNum3, that.phoneNum3) &&
            Objects.equals(login, that.login) &&
            Objects.equals(rentalTransactionId, that.rentalTransactionId) &&
            Objects.equals(waitingListId, that.waitingListId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        patronStatus,
        firstName,
        middleName,
        lastName,
        streetAddress,
        mailingAddress,
        email,
        phoneNum1,
        phoneNum2,
        phoneNum3,
        login,
        rentalTransactionId,
        waitingListId
        );
    }

    @Override
    public String toString() {
        return "PatronCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (patronStatus != null ? "patronStatus=" + patronStatus + ", " : "") +
                (firstName != null ? "firstName=" + firstName + ", " : "") +
                (middleName != null ? "middleName=" + middleName + ", " : "") +
                (lastName != null ? "lastName=" + lastName + ", " : "") +
                (streetAddress != null ? "streetAddress=" + streetAddress + ", " : "") +
                (mailingAddress != null ? "mailingAddress=" + mailingAddress + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (phoneNum1 != null ? "phoneNum1=" + phoneNum1 + ", " : "") +
                (phoneNum2 != null ? "phoneNum2=" + phoneNum2 + ", " : "") +
                (phoneNum3 != null ? "phoneNum3=" + phoneNum3 + ", " : "") +
                (login != null ? "login=" + login + ", " : "") +
                (rentalTransactionId != null ? "rentalTransactionId=" + rentalTransactionId + ", " : "") +
                (waitingListId != null ? "waitingListId=" + waitingListId + ", " : "") +
            "}";
    }

}
