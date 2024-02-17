package com.amin.survey.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A Backup.
 */
@Entity
@Table(name = "backup")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Backup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "question_1")
    private String question1;

    @Column(name = "question_2")
    private String question2;

    @Column(name = "question_3")
    private String question3;

    @Column(name = "question_4")
    private String question4;

    @Column(name = "question_5")
    private String question5;

    @Column(name = "question_6")
    private String question6;

    @Column(name = "question_7")
    private String question7;

    @ManyToOne(optional = false)
    @NotNull
    @JoinColumn(name = "form_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = { "user", "organization" }, allowSetters = true)
    private Form form;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Backup id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion1() {
        return this.question1;
    }

    public Backup question1(String question1) {
        this.setQuestion1(question1);
        return this;
    }

    public void setQuestion1(String question1) {
        this.question1 = question1;
    }

    public String getQuestion2() {
        return this.question2;
    }

    public Backup question2(String question2) {
        this.setQuestion2(question2);
        return this;
    }

    public void setQuestion2(String question2) {
        this.question2 = question2;
    }

    public String getQuestion3() {
        return this.question3;
    }

    public Backup question3(String question3) {
        this.setQuestion3(question3);
        return this;
    }

    public void setQuestion3(String question3) {
        this.question3 = question3;
    }

    public String getQuestion4() {
        return this.question4;
    }

    public Backup question4(String question4) {
        this.setQuestion4(question4);
        return this;
    }

    public void setQuestion4(String question4) {
        this.question4 = question4;
    }

    public String getQuestion5() {
        return this.question5;
    }

    public Backup question5(String question5) {
        this.setQuestion5(question5);
        return this;
    }

    public void setQuestion5(String question5) {
        this.question5 = question5;
    }

    public String getQuestion6() {
        return this.question6;
    }

    public Backup question6(String question6) {
        this.setQuestion6(question6);
        return this;
    }

    public void setQuestion6(String question6) {
        this.question6 = question6;
    }

    public String getQuestion7() {
        return this.question7;
    }

    public Backup question7(String question7) {
        this.setQuestion7(question7);
        return this;
    }

    public void setQuestion7(String question7) {
        this.question7 = question7;
    }

    public Form getForm() {
        return this.form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public Backup form(Form form) {
        this.setForm(form);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Backup)) {
            return false;
        }
        return getId() != null && getId().equals(((Backup) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Backup{" +
            "id=" + getId() +
            ", question1='" + getQuestion1() + "'" +
            ", question2='" + getQuestion2() + "'" +
            ", question3='" + getQuestion3() + "'" +
            ", question4='" + getQuestion4() + "'" +
            ", question5='" + getQuestion5() + "'" +
            ", question6='" + getQuestion6() + "'" +
            ", question7='" + getQuestion7() + "'" +
            "}";
    }
}
