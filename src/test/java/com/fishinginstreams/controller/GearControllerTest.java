package com.fishinginstreams.controller;

import com.fishinginstreams.enums.*;
import com.fishinginstreams.model.Gear;
import com.fishinginstreams.repository.GearRepo;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GearControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private GearRepo mockGearRepo;

    List<Gear> gearList = new ArrayList<>();
    Page<Gear> gearPage;
    Gear newGear;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        newGear = new Gear(55, RodType.NONE, FishHookType.NONE,
                BaitType.NONE, LureType.NONE, BobberType.NONE, 2.7);
        gearList.add(newGear);
        gearPage = new PageImpl<>(gearList);
    }

    @Test
    public void testGetAllGears() throws Exception{
        Mockito.doReturn(gearPage).when(mockGearRepo).findAll(any(PageRequest.class));
        mockMvc.perform(MockMvcRequestBuilders.get("/gear")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
        verify(mockGearRepo, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    public void testGetGearById() throws Exception{
        Mockito.doReturn(newGear).when(mockGearRepo).getOne(1);
        mockMvc.perform(MockMvcRequestBuilders.get("/gear/1")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new Gson().toJson(newGear)));
        verify(mockGearRepo, times(1)).getOne(1);
    }

    @Test
    public void testSave() throws Exception {
        Mockito.doReturn(null).when(mockGearRepo).save(any(Gear.class));
        mockMvc.perform(MockMvcRequestBuilders.post("/gear")
                .content(new Gson().toJson(newGear))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(mockGearRepo, times(1)).save(newGear);
    }

    @Test
    public void testUpdate() throws Exception{
        Mockito.doReturn(Optional.of(newGear)).when(mockGearRepo).findById(any(Integer.class));
        Mockito.doReturn(null).when(mockGearRepo).save(any(Gear.class));
        mockMvc.perform(MockMvcRequestBuilders.put("/gear")
                .content(new Gson().toJson(newGear))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(mockGearRepo, times(1)).findById(55);
        verify(mockGearRepo, times(1)).save(newGear);

    }

    @Test
    public void testDelete() throws Exception{
        Mockito.doReturn(newGear).when(mockGearRepo).getOne(1);
        Mockito.doNothing().when(mockGearRepo).delete(newGear);
        mockMvc.perform(MockMvcRequestBuilders.delete("/gear/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(mockGearRepo, times(1)).getOne(1);
        verify(mockGearRepo, times(1)).delete(newGear);
    }
}