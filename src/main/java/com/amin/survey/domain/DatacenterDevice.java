package com.amin.survey.domain;

import com.amin.survey.domain.enumeration.DataCenterDeviceType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A DatacenterDevice.
 */
@Entity
@Table(name = "datacenter_device")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DatacenterDevice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "device_type")
    private DataCenterDeviceType deviceType;

    @Column(name = "quantity")
    private String quantity;

    @Column(name = "brand_and_model")
    private String brandAndModel;

    @Column(name = "age")
    private String age;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "current_status")
    private String currentStatus;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "user", "organization" }, allowSetters = true)
    private Form form;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DatacenterDevice id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DataCenterDeviceType getDeviceType() {
        return this.deviceType;
    }

    public DatacenterDevice deviceType(DataCenterDeviceType deviceType) {
        this.setDeviceType(deviceType);
        return this;
    }

    public void setDeviceType(DataCenterDeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getQuantity() {
        return this.quantity;
    }

    public DatacenterDevice quantity(String quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getBrandAndModel() {
        return this.brandAndModel;
    }

    public DatacenterDevice brandAndModel(String brandAndModel) {
        this.setBrandAndModel(brandAndModel);
        return this;
    }

    public void setBrandAndModel(String brandAndModel) {
        this.brandAndModel = brandAndModel;
    }

    public String getAge() {
        return this.age;
    }

    public DatacenterDevice age(String age) {
        this.setAge(age);
        return this;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPurpose() {
        return this.purpose;
    }

    public DatacenterDevice purpose(String purpose) {
        this.setPurpose(purpose);
        return this;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getCurrentStatus() {
        return this.currentStatus;
    }

    public DatacenterDevice currentStatus(String currentStatus) {
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

    public DatacenterDevice form(Form form) {
        this.setForm(form);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DatacenterDevice)) {
            return false;
        }
        return getId() != null && getId().equals(((DatacenterDevice) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DatacenterDevice{" +
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
