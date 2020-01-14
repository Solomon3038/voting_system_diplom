package com.voting.system.project.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "menus", uniqueConstraints = {@UniqueConstraint(columnNames = {"registered", "id"}, name = "menus_unique_registered_idx")})
public class Menu extends AbstractBaseEntity {

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private Date registered = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "menu")
    @OrderBy("id DESC")
    private Set<Dish> dishes;

    public Menu(Integer id, Date registered, Set<Dish> dishes) {
        super(id);
        this.registered = registered;
        setDishes(dishes);
    }

    public void setDishes(Set<Dish> dishes) {
        this.dishes = CollectionUtils.isEmpty(dishes) ? new HashSet<>() : Set.copyOf(dishes);
    }
}