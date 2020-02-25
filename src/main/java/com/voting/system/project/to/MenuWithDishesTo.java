package com.voting.system.project.to;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MenuWithDishesTo extends BaseTo {

    @NotNull
    private LocalDate registered = LocalDate.now();

    private List<DishTo> dishes = new ArrayList<>();

    public MenuWithDishesTo(Integer id) {
        super(id);
    }

    public MenuWithDishesTo(Integer id, LocalDate registered) {
        this(id);
        this.registered = registered;
    }

    public MenuWithDishesTo(Integer id, LocalDate registered, List<DishTo> dishes) {
        this(id, registered);
        setDishes(dishes);
    }

    public void setDishes(List<DishTo> dishes) {
        this.dishes = CollectionUtils.isEmpty(dishes) ? new ArrayList<>() : new ArrayList<>(dishes);
    }
}
