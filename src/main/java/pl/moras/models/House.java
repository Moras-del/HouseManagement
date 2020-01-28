package pl.moras.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "house")
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private int budget;

    @OneToMany(mappedBy = "house"
                ,cascade = CascadeType.ALL)
    @Setter(AccessLevel.NONE)
    @JsonManagedReference
    private List<Inmate> inmates;

    @OneToMany(mappedBy = "house",
                cascade = CascadeType.ALL)
    @Setter(AccessLevel.NONE)
    @JsonManagedReference
    private List<Plan> plans;

    public void addInmates(Inmate inmate){
        if(inmates==null){
            inmates = new ArrayList<>();
        }
        inmates.add(inmate);
        inmate.setHouse(this);
    }

    public void addPlans(Plan plan){
        if (plans == null){
            plans = new ArrayList<>();
        }
        plans.add(plan);
        plan.setHouse(this);
    }
}