package com.voting.system.project.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString(callSuper = true, exclude = "menus")
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "address"}, name = "name_address_unique_idx")})
public class Restaurant extends AbstractNamedEntity {

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "address", nullable = false)
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant", cascade = CascadeType.PERSIST)
    @OrderBy("registered DESC")
    private List<Menu> menus;

    public Restaurant(Integer id, String name, String address) {
        this(id, name, address, new ArrayList<>());
    }

    public Restaurant(Integer id, String name, String address, List<Menu> menus) {
        super(id, name);
        this.address = address;
        setMenus(menus);
    }

    public void setMenus(List<Menu> menus) {
        this.menus = CollectionUtils.isEmpty(menus) ? new ArrayList<>() : new ArrayList<>(menus);
    }

    public void setMenu(@NotNull Menu menu) {
        if (menus == null) {
            menus = new ArrayList<>();
        }
        menus.add(menu);
    }
}