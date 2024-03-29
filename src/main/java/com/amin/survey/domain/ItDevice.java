package com.amin.survey.domain;

import com.amin.survey.domain.enumeration.ItDeviceType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A ItDevice.
 */
@Entity
@Table(name = "it_device")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ItDevice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "device_type")
    @NotNull
    private ItDeviceType deviceType;

    @Column(name = "quantity")
    @NotBlank
    private String quantity;

    @Column(name = "brand_and_model")
    @NotBlank
    private String brandAndModel;

    @Column(name = "age")
    private String age;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "current_status")
    private String currentStatus;

    @ManyToOne(optional = false)
    @NotNull
    @JoinColumn(name = "form_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = { "user", "organization" }, allowSetters = true)
    private Form form;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ItDevice id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ItDeviceType getDeviceType() {
        return this.deviceType;
    }

    public ItDevice deviceType(ItDeviceType deviceType) {
        this.setDeviceType(deviceType);
        return this;
    }

    public void setDeviceType(ItDeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getQuantity() {
        return this.quantity;
    }

    public ItDevice quantity(String quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getBrandAndModel() {
        return this.brandAndModel;
    }

    public ItDevice brandAndModel(String brandAndModel) {
        this.setBrandAndModel(brandAndModel);
        return this;
    }

    public void setBrandAndModel(String brandAndModel) {
        this.brandAndModel = brandAndModel;
    }

    public String getAge() {
        return this.age;
    }

    public ItDevice age(String age) {
        this.setAge(age);
        return this;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPurpose() {
        return this.purpose;
    }

    public ItDevice purpose(String purpose) {
        this.setPurpose(purpose);
        return this;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getCurrentStatus() {
        return this.currentStatus;
    }

    public ItDevice currentStatus(String currentStatus) {
        this.setCurrentStatus(currentStatus);
        return this;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Form getForm() {
        return this.form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public ItDevice form(Form form) {
        this.setForm(form);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItDevice)) {
            return false;
        }
        return getId() != null && getId().equals(((ItDevice) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItDevice{" +
            "id=" + getId() +
            ", deviceType='" + getDeviceType() + "'" +
            ", quantity='" + getQuantity() + "'" +
            ", brandAndModel='" + getBrandAndModel() + "'" +
            ", age='" + getAge() + "'" +
            ", purpose='" + getPurpose() + "'" +
            ", currentStatus='" + getCurrentStatus() + "'" +
            "}";
    }
}
