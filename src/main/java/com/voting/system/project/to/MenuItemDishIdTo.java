package com.voting.system.project.to;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
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
}
