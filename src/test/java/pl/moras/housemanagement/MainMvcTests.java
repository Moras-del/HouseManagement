package pl.moras.housemanagement;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import pl.moras.models.Inmate;
import pl.moras.security.MyUserDetails;
import pl.moras.service.MainService;

import java.security.Principal;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class MainMvcTests {

    @Autowired
    MockMvc mockMvc;

    @Mock
    Principal principal;

    @BeforeEach
    void setup(){
        initMocks(this);
        when(principal.getName()).thenReturn("user");
    }

    @Test
    void should_take_money_from_budget() throws Exception {
        mockMvc.perform(put("/main")
                .principal(principal)
                .param("expenses", "20"))
                .andExpect(status().isOk());
    }

    MyUserDetails getUserDetails(){
        Inmate inmate = new Inmate();
        return new MyUserDetails(inmate);
    }

}
