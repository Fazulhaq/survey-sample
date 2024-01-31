package com.amin.survey.service.criteria;

import com.amin.survey.domain.enumeration.FormStatus;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.amin.survey.domain.Form} entity. This class is used
 * in {@link com.amin.survey.web.rest.FormResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /forms?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FormCriteria implements Serializable, Criteria {

    /**
     * Class for filtering FormStatus
     */
    public static class FormStatusFilter extends Filter<FormStatus> {

        public FormStatusFilter() {}

        public FormStatusFilter(FormStatusFilter filter) {
            super(filter);
        }

        @Override
        public FormStatusFilter copy() {
            return new FormStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter futurePlan;

    private FormStatusFilter status;

    private InstantFilter createDate;

    private InstantFilter updateDate;

    private LongFilter userId;

    private LongFilter organizationId;

    private Boolean distinct;

    public FormCriteria() {}

    public FormCriteria(FormCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.futurePlan = other.futurePlan == null ? null : other.futurePlan.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.createDate = other.createDate == null ? null : other.createDate.copy();
        this.updateDate = other.updateDate == null ? null : other.updateDate.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.organizationId = other.organizationId == null ? null : other.organizationId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public FormCriteria copy() {
        return new FormCriteria(this);
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

    public StringFilter getFuturePlan() {
        return futurePlan;
    }

    public StringFilter futurePlan() {
        if (futurePlan == null) {
            futurePlan = new StringFilter();
        }
        return futurePlan;
    }

    public void setFuturePlan(StringFilter futurePlan) {
        this.futurePlan = futurePlan;
    }

    public FormStatusFilter getStatus() {
        return status;
    }

    public FormStatusFilter status() {
        if (status == null) {
            status = new FormStatusFilter();
        }
        return status;
    }

    public void setStatus(FormStatusFilter status) {
        this.status = status;
    }

    public InstantFilter getCreateDate() {
        return createDate;
    }

    public InstantFilter createDate() {
        if (createDate == null) {
            createDate = new InstantFilter();
        }
        return createDate;
    }

    public void setCreateDate(InstantFilter createDate) {
        this.createDate = createDate;
    }

    public InstantFilter getUpdateDate() {
        return updateDate;
    }

    public InstantFilter updateDate() {
        if (updateDate == null) {
            updateDate = new InstantFilter();
        }
        return updateDate;
    }

    public void setUpdateDate(InstantFilter updateDate) {
        this.updateDate = updateDate;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public LongFilter userId() {
        if (userId == null) {
            userId = new LongFilter();
        }
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getOrganizationId() {
        return organizationId;
    }

    public LongFilter organizationId() {
        if (organizationId == null) {
            organizationId = new LongFilter();
        }
        return organizationId;
    }

    public void setOrganizationId(LongFilter organizationId) {
        this.organizationId = organizationId;
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
        final FormCriteria that = (FormCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(futurePlan, that.futurePlan) &&
            Objects.equals(status, that.status) &&
            Objects.equals(createDate, that.createDate) &&
            Objects.equals(updateDate, that.updateDate) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(organizationId, that.organizationId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, futurePlan, status, createDate, updateDate, userId, organizationId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FormCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (futurePlan != null ? "futurePlan=" + futurePlan + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (createDate != null ? "createDate=" + createDate + ", " : "") +
            (updateDate != null ? "updateDate=" + updateDate + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (organizationId != null ? "organizationId=" + organizationId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
