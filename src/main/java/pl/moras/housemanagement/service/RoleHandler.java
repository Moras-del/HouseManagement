package pl.moras.housemanagement.service;

import pl.moras.housemanagement.models.Role;
import pl.moras.housemanagement.repos.RoleRepo;

import java.util.List;

public interface RoleHandler {

    List<Role> getRoles(RoleRepo roleRepo);

}
