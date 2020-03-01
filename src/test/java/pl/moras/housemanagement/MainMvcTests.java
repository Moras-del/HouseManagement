package pl.moras.housemanagement;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.moras.models.House;
import pl.moras.models.Inmate;
import pl.moras.models.PlanDto;
import pl.moras.security.MyUserDetails;
import pl.moras.service.MainService;

import java.security.Principal;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
public class MainMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MainService mainService;

    @Before
    public void setup(){
        when(mainService.getInmateFromPrincipal(any(Principal.class))).thenReturn(new Inmate());
        when(mainService.takeFromBudget(any(Inmate.class), anyInt())).thenReturn(new House());
        when(mainService.contribPlan(any(Inmate.class), any(PlanDto.class))).thenReturn(0);

    }

    @Test
    @WithMockUser
    public void should_take_money_from_budget() throws Exception {
        mockMvc.perform(put("/main")
                .param("expenses", "20"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "HOUSE_ADMIN")
    public void should_set_budget() throws Exception {
        mockMvc.perform(put("/main/budget")
                .param("budget", "200"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "HOUSE_ADMIN")
    public void should_reset_expenses() throws Exception {
        mockMvc.perform(post("/main/reset")).andExpect(redirectedUrl("/main"));
    }

}
