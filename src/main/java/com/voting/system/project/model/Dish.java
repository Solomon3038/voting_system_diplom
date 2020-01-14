package com.voting.system.project.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "menu_id"}, name = "users_unique_email_idx")})
public class Dish extends AbstractNamedEntity {

    @Column(name = "price", nullable = false)
    @NotNull
    @Range(min = 100)
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Menu menu;

    public Dish(Integer id, String name, Long price) {
        super(id, name);
        this.price = price;
    }
}