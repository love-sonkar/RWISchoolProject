package com.RWISchoolProject.controller;

import com.RWISchoolProject.entity.Event;
import com.RWISchoolProject.links.EventLinks;
import com.RWISchoolProject.repository.EventRepository;
import com.RWISchoolProject.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping(EventLinks.EVENT_LINK)
public class EventController {

    @Autowired
    EventRepository eventRepo;

    @Autowired
    EventService eventService;

    @GetMapping(path = EventLinks.GET_ALL_EVENT)
    public List<Event> getAllEvent(){
        return eventRepo.findAll();
    }

    @PostMapping(path = EventLinks.ADD_EVENT)
    public ResponseEntity<?> addEvent(@RequestParam("event") String event, @RequestParam("picture") MultipartFile picture) throws IOException {
        Event ee = new ObjectMapper().readValue(event.toString(),Event.class);
        return eventService.addEventService(ee,picture);
    }

    @PostMapping(path = EventLinks.UPDATE_LINK)
    public ResponseEntity<?> updateEvent(@RequestParam("event") String event, @PathVariable String id) throws IOException {
        Event ee = new ObjectMapper().readValue(event.toString(),Event.class);
        return eventService.updateEventService(ee, Long.parseLong(id));
    }

    @DeleteMapping(path = EventLinks.DELETE_LINK)
    public ResponseEntity<?> deleteEvent(@PathVariable String id){
        return eventService.deleteEvent(Long.parseLong(id));
    }

}
