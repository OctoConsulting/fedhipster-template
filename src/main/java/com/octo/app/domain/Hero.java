package com.octo.app.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Hero.
 */
@Entity
@Table(name = "hero")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "hero")
public class Hero implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "affilition")
    private String affilition;

    @Column(name = "eye_color")
    private String eyeColor;

    @Column(name = "hair_color")
    private String hairColor;

    @Column(name = "gender")
    private String gender;

    @Column(name = "status")
    private String status;

    @Column(name = "jhi_year")
    private Long year;

    @Column(name = "first_appearance")
    private String firstAppearance;

    @Column(name = "universe")
    private String universe;

    @Column(name = "intelligence")
    private Long intelligence;

    @Column(name = "strength")
    private Long strength;

    @Column(name = "speed")
    private Long speed;

    @Column(name = "durability")
    private Long durability;

    @Column(name = "power")
    private Long power;

    @Column(name = "combat")
    private Long combat;

    @Column(name = "total")
    private Long total;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Hero name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAffilition() {
        return affilition;
    }

    public Hero affilition(String affilition) {
        this.affilition = affilition;
        return this;
    }

    public void setAffilition(String affilition) {
        this.affilition = affilition;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public Hero eyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
        return this;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getHairColor() {
        return hairColor;
    }

    public Hero hairColor(String hairColor) {
        this.hairColor = hairColor;
        return this;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public String getGender() {
        return gender;
    }

    public Hero gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public Hero status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getYear() {
        return year;
    }

    public Hero year(Long year) {
        this.year = year;
        return this;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public String getFirstAppearance() {
        return firstAppearance;
    }

    public Hero firstAppearance(String firstAppearance) {
        this.firstAppearance = firstAppearance;
        return this;
    }

    public void setFirstAppearance(String firstAppearance) {
        this.firstAppearance = firstAppearance;
    }

    public String getUniverse() {
        return universe;
    }

    public Hero universe(String universe) {
        this.universe = universe;
        return this;
    }

    public void setUniverse(String universe) {
        this.universe = universe;
    }

    public Long getIntelligence() {
        return intelligence;
    }

    public Hero intelligence(Long intelligence) {
        this.intelligence = intelligence;
        return this;
    }

    public void setIntelligence(Long intelligence) {
        this.intelligence = intelligence;
    }

    public Long getStrength() {
        return strength;
    }

    public Hero strength(Long strength) {
        this.strength = strength;
        return this;
    }

    public void setStrength(Long strength) {
        this.strength = strength;
    }

    public Long getSpeed() {
        return speed;
    }

    public Hero speed(Long speed) {
        this.speed = speed;
        return this;
    }

    public void setSpeed(Long speed) {
        this.speed = speed;
    }

    public Long getDurability() {
        return durability;
    }

    public Hero durability(Long durability) {
        this.durability = durability;
        return this;
    }

    public void setDurability(Long durability) {
        this.durability = durability;
    }

    public Long getPower() {
        return power;
    }

    public Hero power(Long power) {
        this.power = power;
        return this;
    }

    public void setPower(Long power) {
        this.power = power;
    }

    public Long getCombat() {
        return combat;
    }

    public Hero combat(Long combat) {
        this.combat = combat;
        return this;
    }

    public void setCombat(Long combat) {
        this.combat = combat;
    }

    public Long getTotal() {
        return total;
    }

    public Hero total(Long total) {
        this.total = total;
        return this;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hero)) {
            return false;
        }
        return id != null && id.equals(((Hero) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Hero{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", affilition='" + getAffilition() + "'" +
            ", eyeColor='" + getEyeColor() + "'" +
            ", hairColor='" + getHairColor() + "'" +
            ", gender='" + getGender() + "'" +
            ", status='" + getStatus() + "'" +
            ", year=" + getYear() +
            ", firstAppearance='" + getFirstAppearance() + "'" +
            ", universe='" + getUniverse() + "'" +
            ", intelligence=" + getIntelligence() +
            ", strength=" + getStrength() +
            ", speed=" + getSpeed() +
            ", durability=" + getDurability() +
            ", power=" + getPower() +
            ", combat=" + getCombat() +
            ", total=" + getTotal() +
            "}";
    }
}
