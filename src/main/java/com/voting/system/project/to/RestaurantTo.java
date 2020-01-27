package com.voting.system.project.to;

import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RestaurantTo extends BaseTo {

    @NotBlank
    private String name;

    @NotBlank
    private String address;
}
