package pl.moras.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyDto {
    private String inmateName;
    private String inmatePassword;

    private String houseName;
    private String housePassword;
}
