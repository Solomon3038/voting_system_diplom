package com.voting.system.project.to;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
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
}
