package com.amin.survey.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.amin.survey.domain.System} entity. This class is used
 * in {@link com.amin.survey.web.rest.SystemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /systems?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SystemCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter question1;

    private StringFilter question2;

    private StringFilter question3;

    private StringFilter question4;

    private StringFilter question5;

    private LongFilter formId;

    private Boolean distinct;

    public SystemCriteria() {}

    public SystemCriteria(SystemCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.question1 = other.question1 == null ? null : other.question1.copy();
        this.question2 = other.question2 == null ? null : other.question2.copy();
        this.question3 = other.question3 == null ? null : other.question3.copy();
        this.question4 = other.question4 == null ? null : other.question4.copy();
        this.question5 = other.question5 == null ? null : other.question5.copy();
        this.formId = other.formId == null ? null : other.formId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public SystemCriteria copy() {
        return new SystemCriteria(this);
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

    public StringFilter getQuestion1() {
        return question1;
    }

    public StringFilter question1() {
        if (question1 == null) {
            question1 = new StringFilter();
        }
        return question1;
    }

    public void setQuestion1(StringFilter question1) {
        this.question1 = question1;
    }

    public StringFilter getQuestion2() {
        return question2;
    }

    public StringFilter question2() {
        if (question2 == null) {
            question2 = new StringFilter();
        }
        return question2;
    }

    public void setQuestion2(StringFilter question2) {
        this.question2 = question2;
    }

    public StringFilter getQuestion3() {
        return question3;
    }

    public StringFilter question3() {
        if (question3 == null) {
            question3 = new StringFilter();
        }
        return question3;
    }

    public void setQuestion3(StringFilter question3) {
        this.question3 = question3;
    }

    public StringFilter getQuestion4() {
        return question4;
    }

    public StringFilter question4() {
        if (question4 == null) {
            question4 = new StringFilter();
        }
        return question4;
    }

    public void setQuestion4(StringFilter question4) {
        this.question4 = question4;
    }

    public StringFilter getQuestion5() {
        return question5;
    }

    public StringFilter question5() {
        if (question5 == null) {
            question5 = new StringFilter();
        }
        return question5;
    }

    public void setQuestion5(StringFilter question5) {
        this.question5 = question5;
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
        final SystemCriteria that = (SystemCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(question1, that.question1) &&
            Objects.equals(question2, that.question2) &&
            Objects.equals(question3, that.question3) &&
            Objects.equals(question4, that.question4) &&
            Objects.equals(question5, that.question5) &&
            Objects.equals(formId, that.formId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question1, question2, question3, question4, question5, formId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SystemCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (question1 != null ? "question1=" + question1 + ", " : "") +
            (question2 != null ? "question2=" + question2 + ", " : "") +
            (question3 != null ? "question3=" + question3 + ", " : "") +
            (question4 != null ? "question4=" + question4 + ", " : "") +
            (question5 != null ? "question5=" + question5 + ", " : "") +
            (formId != null ? "formId=" + formId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
