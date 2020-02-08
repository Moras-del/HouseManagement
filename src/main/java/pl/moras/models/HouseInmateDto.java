package pl.moras.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HouseInmateDto {
    private String houseName, housePassword, inmateName, inmatePassword;
}
