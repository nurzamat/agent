package com.agent.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Set;


@Entity
@Table(name="companies")
public class Company implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    private String name;

    private String website;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company",fetch = FetchType.LAZY)
    private Set<TradePoint> tradePoints;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Set<TradePoint> getTradePoints() {
        return tradePoints;
    }

    public void setTradePoints(Set<TradePoint> tradePoints) {
        this.tradePoints = tradePoints;
    }
}

