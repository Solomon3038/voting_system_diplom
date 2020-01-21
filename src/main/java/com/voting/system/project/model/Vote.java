package com.voting.system.project.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString(callSuper = true, exclude = {"restaurant", "user"})
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "user_id"}, name = "date_unique_user_idx")})
public class Vote extends AbstractBaseEntity {

    @Column(name = "date", nullable = false, columnDefinition = "date default now()")
    @NotNull
    private LocalDate date = LocalDate.now();

    @JoinColumn(name = "restaurant_id")
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    @JoinColumn(name = "user_id")
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Vote(Integer id, LocalDate date, Restaurant restaurant, User user) {
        this(id, restaurant, user);
        this.date = date;
    }

    public Vote(Integer id, Restaurant restaurant, User user) {
        super(id);
        this.restaurant = restaurant;
        this.user = user;
    }
}
