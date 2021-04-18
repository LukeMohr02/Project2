package com.fishinginstreams.controller;

import com.fishinginstreams.model.Angler;
import com.fishinginstreams.model.Groop;
import com.fishinginstreams.repository.AnglerRepo;
import com.fishinginstreams.repository.GroopRepo;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
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
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GroopControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private GroopRepo mockGroopRepo;

    @MockBean
    private AnglerRepo mockAnglerRepo;

    List<Groop> groopList = new ArrayList<>();
    Page<Groop> groopPage;
    Groop newGroop;
    List<Angler> anglerList = new ArrayList<>();
    Page<Angler> anglerPage;
    Angler newAngler;
    List<Angler> joinAnglerList = new ArrayList<>();
    List<Groop> joinGroopList = new ArrayList<>();
    List<Angler> leaveAnglerList = new ArrayList<>();
    List<Groop> leaveGroopList = new ArrayList<>();
    Angler leaveAngler;
    Groop leaveGroop;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        newAngler = new Angler();
        newAngler.setId(1);
        newAngler.setUsername("Test");
        anglerList.add(newAngler);
        newGroop = new Groop();
        newGroop.setId(1);
        groopList.add(newGroop);
        groopPage = new PageImpl<>(groopList);
        newGroop.setAnglers(joinAnglerList);
        newAngler.setGroups(joinGroopList);

        leaveAngler = new Angler();
        leaveGroop = new Groop();
        leaveAngler.setId(1);
        leaveAngler.setUsername("Test");
        leaveGroop.setId(1);
        leaveAnglerList.add(leaveAngler);
        leaveGroopList.add(leaveGroop);
        leaveAngler.setGroups(leaveGroopList);
        leaveGroop.setAnglers(leaveAnglerList);
    }

    @Test
    public void testGetAllGroups() throws Exception{
        Mockito.doReturn(groopPage).when(mockGroopRepo).findAll(any(PageRequest.class));
        mockMvc.perform(MockMvcRequestBuilders.get("/group")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
        verify(mockGroopRepo, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    public void testGetGroopById() throws Exception{
        Mockito.doReturn(newGroop).when(mockGroopRepo).getOne(newGroop.getId());
        mockMvc.perform(MockMvcRequestBuilders.get("/group/{id}", newGroop.getId())
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new Gson().toJson(newGroop)));
        verify(mockGroopRepo, times(1)).getOne(newGroop.getId());
    }

    @Test
    public void testSave() throws Exception{
        Mockito.doReturn(newGroop).when(mockGroopRepo).save(newGroop);
        mockMvc.perform(MockMvcRequestBuilders.post("/group")
                .content(new Gson().toJson(newGroop))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(mockGroopRepo, times(1)).save(newGroop);
    }

    @Test
    public void testUpdate() throws Exception{
        Mockito.doReturn(Optional.of(newGroop)).when(mockGroopRepo).findById(any(Integer.class));
        Mockito.doReturn(null).when(mockGroopRepo).save(any(Groop.class));
        mockMvc.perform(MockMvcRequestBuilders.put("/group")
                .content(new Gson().toJson(newGroop))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(mockGroopRepo, times(1)).findById(any(Integer.class));
        verify(mockGroopRepo, times(1)).save(any(Groop.class));
    }

    @Test
    public void testJoinGroup() throws Exception{
        Mockito.doReturn(newGroop).when(mockGroopRepo).findByName(any(String.class));
        Mockito.doReturn(newAngler).when(mockAnglerRepo).findByUsername(any(String.class));
        Mockito.doReturn(null).when(mockGroopRepo).save(any(Groop.class));
        Mockito.doReturn(null).when(mockAnglerRepo).save(any(Angler.class));
        mockMvc.perform(MockMvcRequestBuilders.put("/group/{group}/join", "Test")
                .content(new Gson().toJson(newAngler))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(mockGroopRepo, times(1)).findByName(any(String.class));
        verify(mockAnglerRepo, times(1)).findByUsername(any(String.class));
        verify(mockGroopRepo, times(1)).save(any(Groop.class));
        verify(mockAnglerRepo, times(1)).save(any(Angler.class));
    }

    @Test
    public void testLeaveGroup() throws Exception{
        Mockito.doReturn(leaveGroop).when(mockGroopRepo).findByName(any(String.class));
        Mockito.doReturn(leaveAngler).when(mockAnglerRepo).findByUsername(any(String.class));
        Mockito.doReturn(null).when(mockGroopRepo).save(any(Groop.class));
        Mockito.doReturn(null).when(mockAnglerRepo).save(any(Angler.class));
        mockMvc.perform(MockMvcRequestBuilders.put("/group/{group}/leave", "Test")
                .content(new Gson().toJson(newAngler))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(mockGroopRepo, times(1)).findByName(any(String.class));
        verify(mockAnglerRepo, times(1)).findByUsername(any(String.class));
        verify(mockGroopRepo, times(1)).save(any(Groop.class));
        verify(mockAnglerRepo, times(1)).save(any(Angler.class));
    }

    @Test
    public void testDelete() throws Exception{
        Mockito.doReturn(newGroop).when(mockGroopRepo).getOne(newGroop.getId());
        Mockito.doNothing().when(mockGroopRepo).delete(newGroop);
        mockMvc.perform(MockMvcRequestBuilders.delete("/group/{id}", newGroop.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(mockGroopRepo, times(1)).getOne(newGroop.getId());
        verify(mockGroopRepo, times(1)).delete(newGroop);
    }
}