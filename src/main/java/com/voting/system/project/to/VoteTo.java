package com.voting.system.project.to;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VoteTo extends BaseTo {
    @NotNull
    private LocalDate date = LocalDate.now();

    @NotNull
    private Integer userId;

    @NotNull
    private Integer restaurantId;

    public VoteTo(Integer id, LocalDate date, Integer userId, Integer restaurantId) {
        super(id);
        this.date = date;
        this.userId = userId;
        this.restaurantId = restaurantId;
    }
}
