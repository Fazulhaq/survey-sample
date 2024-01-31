package com.amin.survey.service.criteria;

import com.amin.survey.domain.enumeration.ItDeviceType;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.amin.survey.domain.ItDevice} entity. This class is used
 * in {@link com.amin.survey.web.rest.ItDeviceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /it-devices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ItDeviceCriteria implements Serializable, Criteria {

    /**
     * Class for filtering ItDeviceType
     */
    public static class ItDeviceTypeFilter extends Filter<ItDeviceType> {

        public ItDeviceTypeFilter() {}

        public ItDeviceTypeFilter(ItDeviceTypeFilter filter) {
            super(filter);
        }

        @Override
        public ItDeviceTypeFilter copy() {
            return new ItDeviceTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ItDeviceTypeFilter deviceType;

    private StringFilter quantity;

    private StringFilter brandAndModel;

    private StringFilter age;

    private StringFilter purpose;

    private StringFilter currentStatus;

    private LongFilter formId;

    private Boolean distinct;

    public ItDeviceCriteria() {}

    public ItDeviceCriteria(ItDeviceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.deviceType = other.deviceType == null ? null : other.deviceType.copy();
        this.quantity = other.quantity == null ? null : other.quantity.copy();
        this.brandAndModel = other.brandAndModel == null ? null : other.brandAndModel.copy();
        this.age = other.age == null ? null : other.age.copy();
        this.purpose = other.purpose == null ? null : other.purpose.copy();
        this.currentStatus = other.currentStatus == null ? null : other.currentStatus.copy();
        this.formId = other.formId == null ? null : other.formId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ItDeviceCriteria copy() {
        return new ItDeviceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ItDeviceTypeFilter getDeviceType() {
        return deviceType;
    }

    public ItDeviceTypeFilter deviceType() {
        if (deviceType == null) {
            deviceType = new ItDeviceTypeFilter();
        }
        return deviceType;
    }

    public void setDeviceType(ItDeviceTypeFilter deviceType) {
        this.deviceType = deviceType;
    }

    public StringFilter getQuantity() {
        return quantity;
    }

    public StringFilter quantity() {
        if (quantity == null) {
            quantity = new StringFilter();
        }
        return quantity;
    }

    public void setQuantity(StringFilter quantity) {
        this.quantity = quantity;
    }

    public StringFilter getBrandAndModel() {
        return brandAndModel;
    }

    public StringFilter brandAndModel() {
        if (brandAndModel == null) {
            brandAndModel = new StringFilter();
        }
        return brandAndModel;
    }

    public void setBrandAndModel(StringFilter brandAndModel) {
        this.brandAndModel = brandAndModel;
    }

    public StringFilter getAge() {
        return age;
    }

    public StringFilter age() {
        if (age == null) {
            age = new StringFilter();
        }
        return age;
    }

    public void setAge(StringFilter age) {
        this.age = age;
    }

    public StringFilter getPurpose() {
        return purpose;
    }

    public StringFilter purpose() {
        if (purpose == null) {
            purpose = new StringFilter();
        }
        return purpose;
    }

    public void setPurpose(StringFilter purpose) {
        this.purpose = purpose;
    }

    public StringFilter getCurrentStatus() {
        return currentStatus;
    }

    public StringFilter currentStatus() {
        if (currentStatus == null) {
            currentStatus = new StringFilter();
        }
        return currentStatus;
    }

    public void setCurrentStatus(StringFilter currentStatus) {
        this.currentStatus = currentStatus;
    }

    public LongFilter getFormId() {
        return formId;
    }

    public LongFilter formId() {
        if (formId == null) {
            formId = new LongFilter();
        }
        return formId;
    }

    public void setFormId(LongFilter formId) {
        this.formId = formId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ItDeviceCriteria that = (ItDeviceCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(deviceType, that.deviceType) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(brandAndModel, that.brandAndModel) &&
            Objects.equals(age, that.age) &&
            Objects.equals(purpose, that.purpose) &&
            Objects.equals(currentStatus, that.currentStatus) &&
            Objects.equals(formId, that.formId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, deviceType, quantity, brandAndModel, age, purpose, currentStatus, formId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItDeviceCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (deviceType != null ? "deviceType=" + deviceType + ", " : "") +
            (quantity != null ? "quantity=" + quantity + ", " : "") +
            (brandAndModel != null ? "brandAndModel=" + brandAndModel + ", " : "") +
            (age != null ? "age=" + age + ", " : "") +
            (purpose != null ? "purpose=" + purpose + ", " : "") +
            (currentStatus != null ? "currentStatus=" + currentStatus + ", " : "") +
            (formId != null ? "formId=" + formId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
