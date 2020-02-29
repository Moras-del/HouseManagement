package pl.moras.service;

import pl.moras.models.Role;
import pl.moras.repos.RoleRepo;

import java.util.List;

public interface RoleHandler {

    List<Role> getRoles(RoleRepo roleRepo);

}
