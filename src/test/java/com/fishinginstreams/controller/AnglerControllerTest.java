package com.fishinginstreams.controller;

import com.fishinginstreams.exception.NoAnglerFoundByUsernameException;
import com.fishinginstreams.exception.NotNullConstraintViolationException;
import com.fishinginstreams.exception.UniqueConstraintViolationException;
import com.fishinginstreams.model.Angler;
import com.fishinginstreams.repository.AnglerRepo;
import com.google.gson.Gson;
import org.hibernate.PropertyValueException;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AnglerControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private AnglerRepo mockAnglerRepo;

    List<Angler> anglerList = new ArrayList<>();
    Page<Angler> anglerPage;
    Angler newAngler;
    Angler newAnglerWithUsername;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        newAngler = new Angler();
        anglerList.add(newAngler);
        anglerPage = new PageImpl<>(anglerList);
        newAnglerWithUsername = new Angler();
        newAnglerWithUsername.setUsername("Test");
    }

    @Test
    public void testGetAllAnglers() throws Exception{
        Mockito.doReturn(anglerPage).when(mockAnglerRepo).findAll(any(PageRequest.class));
        mockMvc.perform(MockMvcRequestBuilders.get("/angler")
            .content(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
        verify(mockAnglerRepo, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    public void testGetAnglerById() throws Exception{
        Mockito.doReturn(newAngler).when(mockAnglerRepo).findByUsername("Test");
        mockMvc.perform(MockMvcRequestBuilders.get("/angler/Test")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new Gson().toJson(newAngler)));
        verify(mockAnglerRepo, times(1)).findByUsername("Test");
    }

    @Test
    public void testSave() throws Exception{
        Mockito.doReturn(null).when(mockAnglerRepo).save(any(Angler.class));
        mockMvc.perform(MockMvcRequestBuilders.post("/angler")
                .content(new Gson().toJson(newAngler))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(mockAnglerRepo, times(1)).save(any(Angler.class));
    }

    @Test
    public void testSaveWithProperValueException() throws Exception{
        Mockito.doThrow(new PropertyValueException("test message","angler","username")).when(mockAnglerRepo).save(any(Angler.class));
        mockMvc.perform(MockMvcRequestBuilders.post("/angler")
                .content(new Gson().toJson(newAngler))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult -> assertTrue(mvcResult.getResolvedException() instanceof NotNullConstraintViolationException));
    }

    @Test
    public void testSaveWithGenericException() throws Exception{
        Mockito.doThrow(new RuntimeException()).when(mockAnglerRepo).save(any(Angler.class));
        mockMvc.perform(MockMvcRequestBuilders.post("/angler")
                .content(new Gson().toJson(newAngler))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult -> assertTrue(mvcResult.getResolvedException() instanceof UniqueConstraintViolationException));
    }

    @Test
    public void testUpdate() throws Exception{
        Mockito.doReturn(newAnglerWithUsername).when(mockAnglerRepo).findByUsername("Test");
        Mockito.doReturn(null).when(mockAnglerRepo).save(newAnglerWithUsername);
        mockMvc.perform(MockMvcRequestBuilders.put("/angler")
                .content(new Gson().toJson(newAnglerWithUsername))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(mockAnglerRepo, times(1)).findByUsername("Test");
        verify(mockAnglerRepo, times(1)).save(newAnglerWithUsername);
    }

    @Test
    public void testUpdateWithNullAngler() throws Exception{
        Mockito.doReturn(null).when(mockAnglerRepo).findByUsername(any(String.class));
        mockMvc.perform(MockMvcRequestBuilders.put("/angler")
                .content(new Gson().toJson(newAnglerWithUsername))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult -> assertTrue(mvcResult.getResolvedException() instanceof NoAnglerFoundByUsernameException));
    }

    @Test
    public void testDelete() throws Exception{
        Mockito.doReturn(newAnglerWithUsername).when(mockAnglerRepo).findByUsername("Test");
//        Mockito.doNothing().when(mockAnglerRepo).delete(newAnglerWithUsername);
        mockMvc.perform(MockMvcRequestBuilders.delete("/angler/{username}", "Test")
                .content(new Gson().toJson(newAnglerWithUsername))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(mockAnglerRepo, times(1)).findByUsername("Test");
        verify(mockAnglerRepo, times(1)).delete(newAnglerWithUsername);
    }

    @Test
    public void testDeleteWithNullAngler() throws Exception{
        Mockito.doReturn(null).when(mockAnglerRepo).findByUsername("Test");
        mockMvc.perform(MockMvcRequestBuilders.delete("/angler/{username}", "Test")
                .content(new Gson().toJson(newAnglerWithUsername))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult -> assertTrue(mvcResult.getResolvedException() instanceof NoAnglerFoundByUsernameException));
    }
}