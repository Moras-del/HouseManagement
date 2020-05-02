package pl.moras.housemanagement;


import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.RequestBuilder;
import pl.moras.housemanagement.models.House;
import pl.moras.housemanagement.models.HouseInmateDto;
import pl.moras.housemanagement.models.Inmate;
import pl.moras.housemanagement.service.AuthService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class AuthMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @BeforeEach
    public void setup(){
        when(authService.addInmate(any(HouseInmateDto.class))).thenReturn(new Inmate());
        when(authService.addHouse(any(HouseInmateDto.class))).thenReturn(new House());
    }

    @Test
    void should_show_register_inmate_page() throws Exception {
        mockMvc.perform(get("/inmate/register"))
                .andExpect(view().name("inmateregister"));
    }
    @Test
    void should_show_register_house_page() throws Exception {
        mockMvc.perform(get("/house/register"))
                .andExpect(view().name("houseregister"));
    }
    @Test
    void should_show_login_inmate_page() throws Exception {
        mockMvc.perform(get("/inmate/login"))
                .andExpect(view().name("loginpage"));
    }

    @Test
    void should_register_house() throws Exception {
        mockMvc.perform(postRequest("/house/register")).andExpect(redirectedUrl("/main"));
    }


    @Test
    void should_invalid_register_house() throws Exception {
        RequestBuilder invalidPost = post("/house/register")
                .param("inmateName", "user")
                .param("inmatePassword", "short")
                .param("houseName", "dom")
                .param("housePassword", "short");
        mockMvc.perform(invalidPost).andExpect(view().name("houseregister"));
    }

    @Test
    void should_register_inmate() throws Exception {
        mockMvc.perform(postRequest("/inmate/register")).andExpect(redirectedUrl("/main"));
    }

    @Test
    void should_login_inmate() throws Exception {
        mockMvc.perform(postRequest("/inmate/login")).andExpect(redirectedUrl("/main"));
    }


    RequestBuilder postRequest(String url){
        return post(url)
                .param("inmateName", "user")
                .param("inmatePassword", "haslohaslo")
                .param("houseName", "dom")
                .param("housePassword", "haslohaslo");
    }

}
