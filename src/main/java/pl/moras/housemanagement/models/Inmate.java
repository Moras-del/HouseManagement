package pl.moras.housemanagement.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "inmate")
public class Inmate{


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private int id;

    private String name;

    private String password;

    private int expenses;

    @OneToMany(mappedBy = "owner",
                cascade = CascadeType.ALL)
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private List<Plan> plans;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "house_id")
    private House house;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    @Getter(AccessLevel.NONE)
    private List<Role> roles;

    public Inmate(Inmate inmate) {
        this.id = inmate.getId();
        this.name = inmate.getName();
        this.password = inmate.getPassword();
        this.expenses = inmate.getExpenses();
        this.plans = inmate.getPlans();
        this.house = inmate.getHouse();
        this.roles = inmate.getRoles();
    }

    public void addPlan(Plan plan){
        if (plans == null){
            plans = new ArrayList<>();
        }
        plans.add(plan);
        plan.setOwner(this);
    }

    public List<Plan> getPlans(){
        return Collections.unmodifiableList(plans);
    }


    public void addExpenses(int expenses){
        this.expenses+=expenses;
    }



    public List<Role> getRoles(){
        return Collections.unmodifiableList(roles);
    }
}
