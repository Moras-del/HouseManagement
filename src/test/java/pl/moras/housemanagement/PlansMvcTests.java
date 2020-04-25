package pl.moras.housemanagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@DirtiesContext
public class PlansMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IMainService mainService;

    @MockBean
    private IPlanService planService;

    @Before
    public void setup(){
        when(mainService.getInmate(any(Principal.class))).thenReturn(getInmate());
        when(planService.contribPlan(any(Inmate.class), any(PlanDto.class))).thenReturn(30);
    }

    @Test
    @WithMockUser
    public void should_show_plans_page() throws Exception {
        mockMvc.perform(get("/plans")).andExpect(view().name("planspage"));
    }

    @Test
    @WithMockUser
    public void should_contrib_to_plan() throws Exception {
        PlanDto planDto = new PlanDto();
        planDto.setName("plan");
        planDto.setContribution(50);

        mockMvc.perform(put("/plans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(planDto)))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void should_add_plan() throws Exception {
        mockMvc.perform(post("/plans")
                .param("cost", "50")
                .param("name", "plan"))
                .andExpect(redirectedUrl("/plans"));
    }

    public static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Inmate getInmate(){
        Inmate inmate = new Inmate();
        inmate.setHouse(new House());

        return inmate;
    }
}
