package pl.moras.housemanagement.service;

import pl.moras.housemanagement.models.Role;
import pl.moras.housemanagement.repos.RoleRepo;

import java.util.Collections;
import java.util.List;

enum InmateType implements RoleHandler {
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
