package pl.moras.housemanagement.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class HouseInmateDto {

    @NotEmpty(message = "pole wymagane")
    private String houseName;

    @Size(min = 8, message = "co najmniej 8 znaków")
    private String housePassword;

    private String confirmHousePassword;

    @NotEmpty(message = "pole wymagane")
    private String inmateName;

    @Size(min = 8, message = "co najmniej 8 znaków")
    private String inmatePassword;

    private String confirmInmatePassword;
}
