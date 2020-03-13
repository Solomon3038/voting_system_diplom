package com.voting.system.project.to;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MenuItemDishIdTo extends BaseTo {
    @NotNull
    private LocalDate date = LocalDate.now();

    @NotNull
    @Range(min = 100)
    private Integer price;

    @NotNull
    private Integer dishId;

    public MenuItemDishIdTo(Integer id, LocalDate date, Integer price, Integer dishId) {
        super(id);
        this.date = date;
        this.price = price;
        this.dishId = dishId;
    }

    public MenuItemDishIdTo(@NotNull LocalDate date, @NotNull @Range(min = 100) Integer price, @NotNull Integer dishId) {
        this.date = date;
        this.price = price;
        this.dishId = dishId;
    }
}
