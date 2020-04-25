package pl.moras.housemanagement.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
public class PlanDto {
    private String name;

    @Positive(message = "Koszt musi być wyższy od 0")
    private int cost;
    private int contribution;
}
