package com.voting.system.project.to;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MenuItemDishNameTo extends BaseTo {
    @NotNull
    private LocalDate date = LocalDate.now();

    @NotNull
    @Range(min = 100)
    private Integer price;

    @NotBlank
    @Size(min = 2, max = 500)
    private String dishName;
}
