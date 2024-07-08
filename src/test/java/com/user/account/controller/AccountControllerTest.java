package com.user.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.converters.Auto;
import com.user.account.payload.OtpPayload;
import com.user.account.payload.RechargeConfirmationPayload;
import com.user.account.payload.RechargePayload;
import com.user.account.service.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AccountController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;
    private Authentication authentication;

    @BeforeEach
    public void init(){
        authentication = new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of(new SimpleGrantedAuthority("ROLE_USER"));
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return "db@gmail.com";
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return "db@gmail.com";
            }
        };
    }

    @Test
    void getbalance() throws Exception{
        Double balance = 100.0;

        when(userService.getbalance(authentication.getName(),"ROLE_USER")).thenReturn(100.0);

        mockMvc.perform(get("/account/balance")
                .principal(authentication)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", CoreMatchers.is(balance)))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void recharge() throws Exception{
        String email="darshanbehere@gmail.com";
        String userEmail="a@g.com";
        double amount = 10.0;
        long balance = 10L;
        RechargePayload rechargePayload = new RechargePayload(10.0,"a@g.com");
        Principal p = new Principal() {
            @Override
            public String getName() {
                return "darshanbehere@gmail.com";
            }
        };
//
        //given(userService.recharge(amount,email,userEmail)).willAnswer(i->i.getArgument(0));
        when(userService.recharge(amount,email,userEmail)).thenReturn(balance);
        ResultActions response = mockMvc.perform(post("/account/recharge")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(rechargePayload))
                        .principal(p)
                        ).andExpect(status().isOk());
    }

    @Test
    void testRecharge() throws Exception{
        OtpPayload otpPayload = new OtpPayload(1L,"123256");
        RechargeConfirmationPayload rechargeConfirmationPayload = new RechargeConfirmationPayload("a@g.com",1.0,1.0);
        when(userService.confirmRecharge(any(),any())).thenReturn(rechargeConfirmationPayload);

        mockMvc.perform(post("/account/recharge/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(otpPayload)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.user", CoreMatchers.is(rechargeConfirmationPayload.getUser())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.recharge", CoreMatchers.is(rechargeConfirmationPayload.getRecharge())))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void getAllUserEmail() throws Exception{
        List<String> emailList = List.of("a@gmail.com","b@gmail.com");
        when(userService.getAllUserEmail()).thenReturn(emailList);
        mockMvc.perform(get("/account/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",CoreMatchers.is(emailList.size())));
    }
}