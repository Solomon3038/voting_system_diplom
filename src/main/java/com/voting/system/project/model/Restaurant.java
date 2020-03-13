package com.voting.system.project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.CollectionUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@ToString(callSuper = true, exclude = "menuItems")
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "address"}, name = "name_address_unique_idx")})
public class Restaurant extends AbstractNamedEntity {
    @NotBlank
    @Size(min = 2, max = 500)
    @Column(name = "address", nullable = false)
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @JsonIgnore
    private List<MenuItem> menuItems;

    public Restaurant(Integer id, String name, String address) {
        this(id, name, address, new ArrayList<>());
    }

    public Restaurant(Integer id, String name, String address, List<MenuItem> menuItems) {
        super(id, name);
        this.address = address;
        setMenus(menuItems);
    }

    public void setMenus(List<MenuItem> menuItems) {
        this.menuItems = CollectionUtils.isEmpty(menuItems) ? new ArrayList<>() : new ArrayList<>(menuItems);
    }
}