package pl.moras.housemanagement.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    @Setter(AccessLevel.NONE)
    private int id;

    private String name;

    private String password;

    private int budget;

    @OneToMany(mappedBy = "house",
            cascade = CascadeType.ALL)
    @Setter(AccessLevel.NONE)
    private List<Inmate> inmates;

    @OneToMany(mappedBy = "house",
                cascade = CascadeType.ALL)
    @Setter(AccessLevel.NONE)
    private List<Plan> plans;

    public void addInmate(Inmate inmate){
        if(inmates==null){
            inmates = new ArrayList<>();
        }
        inmates.add(inmate);
        inmate.setHouse(this);
    }

    public void addPlan(Plan plan){
        if(plans==null){
            plans = new ArrayList<>();
        }
        plans.add(plan);
        plan.setHouse(this);
    }


    public void cutBudget(int expenses){
        budget -= expenses;
    }
}
