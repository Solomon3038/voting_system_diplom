package com.voting.system.project.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true, exclude = "dish")
@Table(name = "menu_items", uniqueConstraints = {@UniqueConstraint(columnNames = {"dish_id", "date"}, name = "menu_items_unique_date_idx")})
public class MenuItem extends AbstractBaseEntity {
    @Column(name = "date", nullable = false, columnDefinition = "date default now()")
    @NotNull
    private LocalDate date = LocalDate.now();

    @Column(name = "price", nullable = false)
    @NotNull
    @Range(min = 100)
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id", nullable = false)
    private Dish dish;

    public MenuItem(Integer id, Integer price, Dish dish) {
        super(id);
        this.price = price;
        this.dish = dish;
    }

    public MenuItem(Integer id, LocalDate date, Integer price, Dish dish) {
        this(id, price, dish);
        this.date = date;
    }
}