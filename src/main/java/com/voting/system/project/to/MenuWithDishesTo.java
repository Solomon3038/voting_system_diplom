package com.voting.system.project.to;

import com.voting.system.project.model.Dish;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MenuWithDishesTo extends BaseTo {

    @NotNull
    private List<DishTo> dishes;

    public MenuWithDishesTo(Integer id, @NotNull List<DishTo> dishes) {
        super(id);
        this.dishes = dishes;
    }
}
