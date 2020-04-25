package pl.moras.housemanagement;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.moras.housemanagement.models.House;
import pl.moras.housemanagement.models.Inmate;
import pl.moras.housemanagement.models.PlanDto;
import pl.moras.housemanagement.service.IMainService;
import pl.moras.housemanagement.service.IPlanService;
import pl.moras.housemanagement.service.MainService;

import java.security.Principal;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext
public class MainMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IMainService mainService;

    @MockBean
    private IPlanService planService;

    @Before
    public void setup(){
        when(mainService.getInmate(any(Principal.class))).thenReturn(getInmate());
        when(mainService.takeFromBudget(any(Inmate.class), anyInt())).thenReturn(new House());
        when(planService.contribPlan(any(Inmate.class), any(PlanDto.class))).thenReturn(0);

    }

    @Test
    @WithMockUser
    public void should_show_main_page() throws Exception {
        mockMvc.perform(get("/main")).andExpect(view().name("mainpage"));
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

    private Inmate getInmate(){
        Inmate inmate = new Inmate();
        inmate.setHouse(new House());

        return inmate;
    }

}
