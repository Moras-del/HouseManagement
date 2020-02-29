package pl.moras.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.moras.models.Role;
import pl.moras.repos.RoleRepo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum InmateType implements RoleHandler {
    USER{
        @Override
        public List<Role> getRoles(RoleRepo roleRepo) {
            return Collections.singletonList(roleRepo.findByName("USER"));
        }
    },
    HOUSE_ADMIN{
        @Override
        public List<Role> getRoles(RoleRepo roleRepo) {
            return roleRepo.findAll();
        }
    };

}
