package com.voting.system.project.to;

import com.voting.system.project.model.Restaurant;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RestaurantTo extends BaseTo {
    @NotBlank
    @Size(min = 2, max = 500)
    private String name;

    @NotBlank
    @Size(min = 2, max = 500)
    private String address;

    private List<MenuItemDishNameTo> menuItemDishNameTos;

    public RestaurantTo(Restaurant restaurant, List<MenuItemDishNameTo> menuItemDishNameTos) {
        super(restaurant.getId());
        this.name = restaurant.getName();
        this.address = restaurant.getAddress();
        this.menuItemDishNameTos = menuItemDishNameTos;
    }
}
