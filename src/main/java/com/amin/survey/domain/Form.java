package com.amin.survey.domain;

import com.amin.survey.domain.enumeration.FormStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Form.
 */
@Entity
@Table(name = "form")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Form implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "future_plan")
    private String futurePlan;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private FormStatus status;

    @Column(name = "create_date")
    private Instant createDate;

    @Column(name = "update_date")
    private Instant updateDate;

    @ManyToOne(optional = false)
    @NotNull
    private User user;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    private Organization organization;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Form id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFuturePlan() {
        return this.futurePlan;
    }

    public Form futurePlan(String futurePlan) {
        this.setFuturePlan(futurePlan);
        return this;
    }

    public void setFuturePlan(String futurePlan) {
        this.futurePlan = futurePlan;
    }

    public FormStatus getStatus() {
        return this.status;
    }

    public Form status(FormStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(FormStatus status) {
        this.status = status;
    }

    public Instant getCreateDate() {
        return this.createDate;
    }

    public Form createDate(Instant createDate) {
        this.setCreateDate(createDate);
        return this;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getUpdateDate() {
        return this.updateDate;
    }

    public Form updateDate(Instant updateDate) {
        this.setUpdateDate(updateDate);
        return this;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Form user(User user) {
        this.setUser(user);
        return this;
    }

    public Organization getOrganization() {
        return this.organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Form organization(Organization organization) {
        this.setOrganization(organization);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Form)) {
            return false;
        }
        return getId() != null && getId().equals(((Form) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Form{" +
            "id=" + getId() +
            ", futurePlan='" + getFuturePlan() + "'" +
            ", status='" + getStatus() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
