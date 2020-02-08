package pl.moras.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class HouseInmateDto {

    @NotNull(message = "pole wymagane")
    private String houseName;

    @NotNull(message = "pole wymagane")
    @Size(min = 8, message = "co najmniej 8 znaków")
    private String housePassword;

    @NotNull(message = "pole wymagane")
    private String inmateName;

    @NotNull
    @Size(min = 8, message = "co najmniej 8 znaków")
    private String inmatePassword;
}
