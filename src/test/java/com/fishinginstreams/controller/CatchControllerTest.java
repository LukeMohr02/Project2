package com.fishinginstreams.controller;

import com.fishinginstreams.exception.NoCatchFoundByIdException;
import com.fishinginstreams.model.*;
import com.fishinginstreams.repository.AnglerRepo;
import com.fishinginstreams.repository.CatchRepo;
import com.fishinginstreams.repository.FishRepo;
import com.fishinginstreams.repository.GearRepo;
import com.google.gson.Gson;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CatchControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private CatchRepo mockCatchRepo;

    @MockBean
    private AnglerRepo mockAnglerRepo;

    @MockBean
    private FishRepo mockFishRepo;

    @MockBean
    private GearRepo mockGearRepo;

    List<Catch> catchList = new ArrayList<>();
    Page<Catch> catchPage;
    Catch newCatch;
    PreCatch newPreCatch;
    Fish newFish;
    Gear newGear;
    Angler newAngler;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        newCatch = new Catch();
        newCatch.setId(1);
        catchList.add(newCatch);
        catchPage = new PageImpl<>(catchList);
        newPreCatch = new PreCatch("Test", 1, 1);
        newFish = new Fish();
        newGear = new Gear();
        newAngler = new Angler();
        newFish.setId(1);
        newGear.setId(1);
        newAngler.setId(1);
    }

    @Test
    public void testGetAllCatches() throws Exception{
        Mockito.doReturn(catchPage).when(mockCatchRepo).findAll(any(PageRequest.class));
        mockMvc.perform(MockMvcRequestBuilders.get("/catch?sortby=length")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
        verify(mockCatchRepo, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    public void testGetCatchById() throws Exception{
        Mockito.doReturn(newCatch).when(mockCatchRepo).getOne(1);
        mockMvc.perform(MockMvcRequestBuilders.get("/catch/{id}", 1)
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new Gson().toJson(newCatch)));
        verify(mockCatchRepo, times(1)).getOne(1);
    }

    @Test
    public void testSave() throws Exception{
        Mockito.doReturn(newAngler).when(mockAnglerRepo).findByUsername(any(String.class));
        Mockito.doReturn(Optional.of(newFish)).when(mockFishRepo).findById(any(Integer.class));
        Mockito.doReturn(Optional.of(newGear)).when(mockGearRepo).findById(any(Integer.class));
        Mockito.doReturn(newCatch).when(mockCatchRepo).save(any(Catch.class));
        mockMvc.perform(MockMvcRequestBuilders.post("/catch")
                .content(new Gson().toJson(newPreCatch))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(mockAnglerRepo, times(1)).findByUsername(any(String.class));
        verify(mockFishRepo, times(1)).findById(any(Integer.class));
        verify(mockGearRepo, times(1)).findById(any(Integer.class));
        verify(mockCatchRepo, times(1)).save(any(Catch.class));
    }

    @Test
    public void testUpdate() throws Exception{
        Mockito.doReturn(Optional.of(newCatch)).when(mockCatchRepo).findById(any(Integer.class));
        Mockito.doReturn(null).when(mockCatchRepo).save(any(Catch.class));
        mockMvc.perform(MockMvcRequestBuilders.put("/catch")
                .content(new Gson().toJson(newCatch))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(mockCatchRepo, times(1)).findById(any(Integer.class));
        verify(mockCatchRepo, times(1)).save(any(Catch.class));
    }

    @Test
    public void testUpdateWithNullAngler() throws Exception{
        Mockito.doReturn(null).when(mockAnglerRepo).findByUsername(any(String.class));
        mockMvc.perform(MockMvcRequestBuilders.put("/catch")
                .content(new Gson().toJson(newCatch))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult -> assertTrue(mvcResult.getResolvedException() instanceof NoCatchFoundByIdException));
    }

    @Test
    public void testDelete() throws Exception{
        Mockito.doReturn(newCatch).when(mockCatchRepo).getOne(any(Integer.class));
        Mockito.doNothing().when(mockCatchRepo).delete(any(Catch.class));
        mockMvc.perform(MockMvcRequestBuilders.delete("/catch/{id}", 1)
                .content(new Gson().toJson(newCatch))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(mockCatchRepo, times(1)).getOne(any(Integer.class));
        verify(mockCatchRepo, times(1)).delete(any(Catch.class));
    }
}