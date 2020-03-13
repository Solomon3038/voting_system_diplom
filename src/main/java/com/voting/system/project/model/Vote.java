package com.voting.system.project.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true, exclude = {"restaurant", "user"})
@Table(name = "votes", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date"}, name = "date_unique_user_idx")})
public class Vote extends AbstractBaseEntity {
    @Column(name = "date", nullable = false, columnDefinition = "date default now()")
    @NotNull
    private LocalDate date = LocalDate.now();

    @JoinColumn(name = "user_id")
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "restaurant_id")
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    public Vote(Integer id, @Nullable LocalDate date, User user, Restaurant restaurant) {
        super(id);
        this.restaurant = restaurant;
        this.user = user;
        if (date != null) {
            this.date = date;
        }
    }
}
