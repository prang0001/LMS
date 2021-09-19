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
 * A Patron.
 */
@Entity
@Table(name = "patron")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Patron implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "patron_status", nullable = false)
    private String patronStatus;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "mailing_address")
    private String mailingAddress;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_num_1")
    private String phoneNum1;

    @Column(name = "phone_num_2")
    private String phoneNum2;

    @Column(name = "phone_num_3")
    private String phoneNum3;

    @Column(name = "login")
    private String login;

    @ManyToMany(mappedBy = "patrons")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<RentalTransaction> rentalTransactions = new HashSet<>();

    @ManyToMany(mappedBy = "patrons")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<WaitingList> waitingLists = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatronStatus() {
        return patronStatus;
    }

    public Patron patronStatus(String patronStatus) {
        this.patronStatus = patronStatus;
        return this;
    }

    public void setPatronStatus(String patronStatus) {
        this.patronStatus = patronStatus;
    }

    public String getFirstName() {
        return firstName;
    }

    public Patron firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Patron middleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public Patron lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public Patron streetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
        return this;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public Patron mailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
        return this;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public String getEmail() {
        return email;
    }

    public Patron email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum1() {
        return phoneNum1;
    }

    public Patron phoneNum1(String phoneNum1) {
        this.phoneNum1 = phoneNum1;
        return this;
    }

    public void setPhoneNum1(String phoneNum1) {
        this.phoneNum1 = phoneNum1;
    }

    public String getPhoneNum2() {
        return phoneNum2;
    }

    public Patron phoneNum2(String phoneNum2) {
        this.phoneNum2 = phoneNum2;
        return this;
    }

    public void setPhoneNum2(String phoneNum2) {
        this.phoneNum2 = phoneNum2;
    }

    public String getPhoneNum3() {
        return phoneNum3;
    }

    public Patron phoneNum3(String phoneNum3) {
        this.phoneNum3 = phoneNum3;
        return this;
    }

    public void setPhoneNum3(String phoneNum3) {
        this.phoneNum3 = phoneNum3;
    }

    public String getLogin() {
        return login;
    }

    public Patron login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Set<RentalTransaction> getRentalTransactions() {
        return rentalTransactions;
    }

    public Patron rentalTransactions(Set<RentalTransaction> rentalTransactions) {
        this.rentalTransactions = rentalTransactions;
        return this;
    }

    public Patron addRentalTransaction(RentalTransaction rentalTransaction) {
        this.rentalTransactions.add(rentalTransaction);
        rentalTransaction.getPatrons().add(this);
        return this;
    }

    public Patron removeRentalTransaction(RentalTransaction rentalTransaction) {
        this.rentalTransactions.remove(rentalTransaction);
        rentalTransaction.getPatrons().remove(this);
        return this;
    }

    public void setRentalTransactions(Set<RentalTransaction> rentalTransactions) {
        this.rentalTransactions = rentalTransactions;
    }

    public Set<WaitingList> getWaitingLists() {
        return waitingLists;
    }

    public Patron waitingLists(Set<WaitingList> waitingLists) {
        this.waitingLists = waitingLists;
        return this;
    }

    public Patron addWaitingList(WaitingList waitingList) {
        this.waitingLists.add(waitingList);
        waitingList.getPatrons().add(this);
        return this;
    }

    public Patron removeWaitingList(WaitingList waitingList) {
        this.waitingLists.remove(waitingList);
        waitingList.getPatrons().remove(this);
        return this;
    }

    public void setWaitingLists(Set<WaitingList> waitingLists) {
        this.waitingLists = waitingLists;
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
        Patron patron = (Patron) o;
        if (patron.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), patron.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Patron{" +
            "id=" + getId() +
            ", patronStatus='" + getPatronStatus() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", streetAddress='" + getStreetAddress() + "'" +
            ", mailingAddress='" + getMailingAddress() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNum1='" + getPhoneNum1() + "'" +
            ", phoneNum2='" + getPhoneNum2() + "'" +
            ", phoneNum3='" + getPhoneNum3() + "'" +
            ", login='" + getLogin() + "'" +
            "}";
    }
}
