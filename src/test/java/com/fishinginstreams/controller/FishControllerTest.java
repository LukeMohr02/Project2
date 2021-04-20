package com.fishinginstreams.controller;

import com.fishinginstreams.enums.HabitatType;
import com.fishinginstreams.exception.NoAnglerFoundByUsernameException;
import com.fishinginstreams.exception.NoFishFoundByIdException;
import com.fishinginstreams.model.Angler;
import com.fishinginstreams.model.Fish;
import com.fishinginstreams.repository.AnglerRepo;
import com.fishinginstreams.repository.FishRepo;
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
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class FishControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private FishRepo mockFishRepo;

    List<Fish> fishList = new ArrayList<>();
    Page<Fish> fishPage;
    Fish newFish;


    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        newFish = new Fish(1, "TestSpecies", 1, 1,
                HabitatType.FRESHWATER, "TestDanger", null);
        fishList.add(newFish);
        fishPage = new PageImpl<>(fishList);
    }

    @Test
    public void testGetAllFish() throws Exception{
        Mockito.doReturn(fishPage).when(mockFishRepo).findAll(any(PageRequest.class));
        mockMvc.perform(MockMvcRequestBuilders.get("/fish")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
        verify(mockFishRepo, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    public void testGetFishById() throws Exception{
        Mockito.doReturn(newFish).when(mockFishRepo).getOne(newFish.getId());
        mockMvc.perform(MockMvcRequestBuilders.get("/fish/{id}", newFish.getId())
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new Gson().toJson(newFish)));
        verify(mockFishRepo, times(1)).getOne(newFish.getId());
    }

    @Test
    public void testSave() throws Exception{
        Mockito.doReturn(null).when(mockFishRepo).save(newFish);
        mockMvc.perform(MockMvcRequestBuilders.post("/fish")
                .content(new Gson().toJson(newFish))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(mockFishRepo, times(1)).save(newFish);
    }

    @Test
    public void testUpdate() throws Exception{
        Mockito.doReturn(Optional.of(newFish)).when(mockFishRepo).findById(newFish.getId());
        Mockito.doReturn(null).when(mockFishRepo).save(newFish);
        mockMvc.perform(MockMvcRequestBuilders.put("/fish")
                .content(new Gson().toJson(newFish))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(mockFishRepo, times(1)).findById(newFish.getId());
        verify(mockFishRepo, times(1)).save(newFish);
    }

    @Test
    public void testUpdateWithNullFish() throws Exception{
        Mockito.doReturn(Optional.of(newFish)).when(mockFishRepo).findById(newFish.getId());
        Mockito.doThrow(new NoSuchElementException()).when(mockFishRepo).save(newFish);
        mockMvc.perform(MockMvcRequestBuilders.put("/fish")
                .content(new Gson().toJson(newFish))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult -> assertTrue(mvcResult.getResolvedException() instanceof NoFishFoundByIdException));
    }

    @Test
    public void testDelete() throws Exception{
        Mockito.doReturn(newFish).when(mockFishRepo).getOne(newFish.getId());
        Mockito.doNothing().when(mockFishRepo).delete(newFish);
        mockMvc.perform(MockMvcRequestBuilders.delete("/fish/{id}", newFish.getId())
                .content(new Gson().toJson(newFish))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(mockFishRepo, times(1)).getOne(newFish.getId());
        verify(mockFishRepo, times(1)).delete(newFish);
    }

}