package com.amin.survey.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A OrgResponsiblePerson.
 */
@Entity
@Table(name = "org_responsible_person")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrgResponsiblePerson implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @NotNull
    @Column(name = "position", nullable = false)
    private String position;

    @NotNull
    @Column(name = "contact", nullable = false)
    private String contact;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "user", "organization" }, allowSetters = true)
    private Form form;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OrgResponsiblePerson id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return this.fullName;
    }

    public OrgResponsiblePerson fullName(String fullName) {
        this.setFullName(fullName);
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPosition() {
        return this.position;
    }

    public OrgResponsiblePerson position(String position) {
        this.setPosition(position);
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getContact() {
        return this.contact;
    }

    public OrgResponsiblePerson contact(String contact) {
        this.setContact(contact);
        return this;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public OrgResponsiblePerson date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Form getForm() {
        return this.form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public OrgResponsiblePerson form(Form form) {
        this.setForm(form);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrgResponsiblePerson)) {
            return false;
        }
        return getId() != null && getId().equals(((OrgResponsiblePerson) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrgResponsiblePerson{" +
            "id=" + getId() +
            ", fullName='" + getFullName() + "'" +
            ", position='" + getPosition() + "'" +
            ", contact='" + getContact() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
