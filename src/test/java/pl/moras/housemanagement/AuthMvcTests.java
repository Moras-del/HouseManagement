package pl.moras.housemanagement;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultHandler;
import pl.moras.models.House;
import pl.moras.models.HouseInmateDto;
import pl.moras.models.Inmate;
import pl.moras.service.AuthService;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthMvcTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Before
    public void setup(){
        when(authService.addInmate(any(HouseInmateDto.class))).thenReturn(new Inmate());
        when(authService.addHouse(any(HouseInmateDto.class))).thenReturn(new House());
        when(authService.authenticate(any(HouseInmateDto.class))).thenReturn(true);
    }

    @Test
    public void should_register_house() throws Exception {
        mockMvc.perform(postRequest("/house/register")).andExpect(redirectedUrl("/main"));
    }


    @Test
    public void should_invalid_register_house() throws Exception {
        RequestBuilder invalidPost = post("/house/register")
                .param("inmateName", "user")
                .param("inmatePassword", "short")
                .param("houseName", "dom")
                .param("housePassword", "short");
        mockMvc.perform(invalidPost).andExpect(view().name("houseregister"));
    }

    @Test
    public void should_register_inmate() throws Exception {
        mockMvc.perform(postRequest("/inmate/register")).andExpect(redirectedUrl("/main"));
    }

    @Test
    public void should_login_inmate() throws Exception {
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
