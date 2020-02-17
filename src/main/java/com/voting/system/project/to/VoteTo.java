package com.voting.system.project.to;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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
        this(id, userId, restaurantId);
        this.date = date;
    }

    public VoteTo(Integer id, Integer userId, Integer restaurantId) {
        super(id);
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public VoteTo(Integer userId, Integer restaurantId) {
        this(null, userId, restaurantId);
    }
}
