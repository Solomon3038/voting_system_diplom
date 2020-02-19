package com.voting.system.project.model;

import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString(callSuper = true, exclude = {"restaurant", "dishes"})
@Table(name = "menus", uniqueConstraints = {@UniqueConstraint(columnNames = {"registered", "restaurant_id"}, name = "menus_unique_registered_idx")})
public class Menu extends AbstractBaseEntity {

    @Column(name = "registered", nullable = false, columnDefinition = "date default now()")
    @NotNull
    private LocalDate registered = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "menu", cascade = CascadeType.PERSIST)
    @OrderBy("name ASC")
    @BatchSize(size = 200)
    @Valid
    private List<Dish> dishes = new ArrayList<>();

    public Menu(Integer id, Restaurant restaurant) {
        super(id);
        this.restaurant = restaurant;
    }

    public Menu(Integer id, LocalDate registered, Restaurant restaurant) {
        this(id, restaurant);
        this.registered = registered;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = CollectionUtils.isEmpty(dishes) ? new ArrayList<>() : new ArrayList<>(dishes);
    }

    public void setDish(@NotNull Dish dish) {
        if (dishes == null) {
            dishes = new ArrayList<>();
        }
        dishes.add(dish);
    }
}