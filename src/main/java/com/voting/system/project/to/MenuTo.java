package com.voting.system.project.to;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MenuTo extends BaseTo {

    @NotNull
    private LocalDate registered = LocalDate.now();

    public MenuTo(Integer id) {
        super(id);
    }

    public MenuTo(Integer id, LocalDate registered) {
        this(id);
        this.registered = registered;
    }
}
