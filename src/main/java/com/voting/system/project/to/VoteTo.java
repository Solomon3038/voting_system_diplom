package com.voting.system.project.to;

import com.voting.system.project.model.AbstractBaseEntity;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.beans.ConstructorProperties;
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

    @ConstructorProperties({"id", "date", "userId", "restaurantId"})
    public VoteTo(Integer id, LocalDate date, Integer userId, Integer restaurantId) {
        this(id, userId, restaurantId);
        this.date = date;
    }

    @ConstructorProperties({"id", "userId", "restaurantId"})
    public VoteTo(Integer id, Integer userId, Integer restaurantId) {
        super(id);
        this.userId = userId;
        this.restaurantId = restaurantId;
    }
}
