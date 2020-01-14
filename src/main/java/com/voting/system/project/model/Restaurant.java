package com.voting.system.project.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("id DESC")
    private Set<Menu> menus;

    public Restaurant(Integer id, String name) {
        this(id, name, new HashSet<>());
    }

    public Restaurant(Integer id, String name, Set<Menu> menus) {
        super(id, name);
        setMenus(menus);
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = CollectionUtils.isEmpty(menus) ? new HashSet<>() : Set.copyOf(menus);
    }
}