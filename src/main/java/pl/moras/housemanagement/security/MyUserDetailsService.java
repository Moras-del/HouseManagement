package pl.moras.housemanagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.moras.housemanagement.models.Inmate;
import pl.moras.housemanagement.repos.InmateRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    InmateRepo inmateRepo;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Inmate inmate = inmateRepo.findByName(s).orElseThrow(()-> new UsernameNotFoundException("not found"));
        return new MyUserDetails(inmate);
    }
}
