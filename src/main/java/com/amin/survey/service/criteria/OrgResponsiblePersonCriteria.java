package com.amin.survey.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.amin.survey.domain.OrgResponsiblePerson} entity. This class is used
 * in {@link com.amin.survey.web.rest.OrgResponsiblePersonResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /org-responsible-people?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrgResponsiblePersonCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fullName;

    private StringFilter position;

    private StringFilter contact;

    private LocalDateFilter date;

    private LongFilter formId;

    private Boolean distinct;

    public OrgResponsiblePersonCriteria() {}

    public OrgResponsiblePersonCriteria(OrgResponsiblePersonCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fullName = other.fullName == null ? null : other.fullName.copy();
        this.position = other.position == null ? null : other.position.copy();
        this.contact = other.contact == null ? null : other.contact.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.formId = other.formId == null ? null : other.formId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public OrgResponsiblePersonCriteria copy() {
        return new OrgResponsiblePersonCriteria(this);
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

    public StringFilter getFullName() {
        return fullName;
    }

    public StringFilter fullName() {
        if (fullName == null) {
            fullName = new StringFilter();
        }
        return fullName;
    }

    public void setFullName(StringFilter fullName) {
        this.fullName = fullName;
    }

    public StringFilter getPosition() {
        return position;
    }

    public StringFilter position() {
        if (position == null) {
            position = new StringFilter();
        }
        return position;
    }

    public void setPosition(StringFilter position) {
        this.position = position;
    }

    public StringFilter getContact() {
        return contact;
    }

    public StringFilter contact() {
        if (contact == null) {
            contact = new StringFilter();
        }
        return contact;
    }

    public void setContact(StringFilter contact) {
        this.contact = contact;
    }

    public LocalDateFilter getDate() {
        return date;
    }

    public LocalDateFilter date() {
        if (date == null) {
            date = new LocalDateFilter();
        }
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
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
        final OrgResponsiblePersonCriteria that = (OrgResponsiblePersonCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(fullName, that.fullName) &&
            Objects.equals(position, that.position) &&
            Objects.equals(contact, that.contact) &&
            Objects.equals(date, that.date) &&
            Objects.equals(formId, that.formId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, position, contact, date, formId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrgResponsiblePersonCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (fullName != null ? "fullName=" + fullName + ", " : "") +
            (position != null ? "position=" + position + ", " : "") +
            (contact != null ? "contact=" + contact + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (formId != null ? "formId=" + formId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
