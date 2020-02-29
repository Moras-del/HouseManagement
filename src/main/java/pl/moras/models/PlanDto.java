package pl.moras.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
public class PlanDto {
    private String name;

    @Min(value = 1, message = "Koszt musi być wyższy od 0")
    private int cost;
    private int contribution;
}
