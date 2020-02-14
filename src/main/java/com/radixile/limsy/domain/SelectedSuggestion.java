package com.radixile.limsy.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SelectedSuggestion.
 */
@Entity
@Table(name = "selected_suggestion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SelectedSuggestion implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "selected")
    private Boolean selected;

    @Column(name = "manual_select")
    private Boolean manualSelect;

    @OneToOne
    @JoinColumn(unique = true)
    private Suggestion suggestion;

    @OneToOne
    @JoinColumn(unique = true)
    private Dress dress;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isSelected() {
        return selected;
    }

    public SelectedSuggestion selected(Boolean selected) {
        this.selected = selected;
        return this;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Boolean isManualSelect() {
        return manualSelect;
    }

    public SelectedSuggestion manualSelect(Boolean manualSelect) {
        this.manualSelect = manualSelect;
        return this;
    }

    public void setManualSelect(Boolean manualSelect) {
        this.manualSelect = manualSelect;
    }

    public Suggestion getSuggestion() {
        return suggestion;
    }

    public SelectedSuggestion suggestion(Suggestion suggestion) {
        this.suggestion = suggestion;
        return this;
    }

    public void setSuggestion(Suggestion suggestion) {
        this.suggestion = suggestion;
    }

    public Dress getDress() {
        return dress;
    }

    public SelectedSuggestion dress(Dress dress) {
        this.dress = dress;
        return this;
    }

    public void setDress(Dress dress) {
        this.dress = dress;
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
        SelectedSuggestion selectedSuggestion = (SelectedSuggestion) o;
        if (selectedSuggestion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), selectedSuggestion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SelectedSuggestion{" +
            "id=" + getId() +
            ", selected='" + isSelected() + "'" +
            ", manualSelect='" + isManualSelect() + "'" +
            "}";
    }
}
