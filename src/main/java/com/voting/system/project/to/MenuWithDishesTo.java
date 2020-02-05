package com.voting.system.project.to;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

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
