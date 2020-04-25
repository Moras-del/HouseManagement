package pl.moras.housemanagement.models;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@NoArgsConstructor
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int id;


    @Getter
    @Setter
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<Inmate> inmates;

    private Role(String name){
        this.name = name;
    }

    public static Role of(String name){
        return new Role(name);
    }


}
