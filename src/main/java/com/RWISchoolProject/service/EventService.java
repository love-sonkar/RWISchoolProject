package com.RWISchoolProject.service;

import com.RWISchoolProject.entity.Event;
import com.RWISchoolProject.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Random;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepo;


    public ResponseEntity<?> addEventService(Event event, MultipartFile picture) throws IOException {
        if (!picture.isEmpty()) {
            String profileUploadDir = "D:\\new\\RWISchoolProject\\src\\main\\resources\\static\\picture";
            try {
                String fileExtension = picture.getOriginalFilename()
                        .substring(picture.getOriginalFilename().lastIndexOf(".") + 1);
                String filePath = profileUploadDir + File.separator + event.getTitle() + "_title" + "."
                        + fileExtension;
                File destFile = new File(filePath);
                picture.transferTo(destFile);
                Random rr = new Random();
                event.setPicture(rr.nextInt(3) + "." + fileExtension);
            } catch (IOException e) {
                e.printStackTrace();
                throw new IOException("Failed to save the picture: " + e.getMessage());
            }
        }
        if(!event.getTitle().isEmpty() && !event.getDescription().isEmpty()){;
            Event eventSave = eventRepo.save(event);
           return ResponseEntity.ok(eventSave);
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Error");
    }

    public ResponseEntity<?> updateEventService(Event event, long id) throws IOException {
        Event ee = eventRepo.findById(id);
        if(ee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event Not found");
        }
        if(!event.getTitle().isEmpty() && !event.getDescription().isEmpty()){
            ee.setTitle(event.getTitle());
            ee.setDescription(event.getDescription());
            Event eventSave = eventRepo.save(ee);
            return ResponseEntity.ok(eventSave);
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Error");
    }

    public ResponseEntity<?> deleteEvent(long id){
        Event eventData = eventRepo.findById(id);
        if(eventData != null) {
            eventRepo.delete(eventData);
            return ResponseEntity.ok().body("Successfully Deleted");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event Not found");
    }

}
