package com.voting.system.project.to;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MenuTo extends BaseTo {

    @NotNull
    private LocalDate registered;

    public MenuTo(Integer id, @NotNull LocalDate registered) {
        super(id);
        this.registered = registered;
    }
}
