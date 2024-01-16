package org.coders.contactapp.web;

import lombok.RequiredArgsConstructor;
import org.coders.contactapp.Exception.ContactNotFoundException;
import org.coders.contactapp.domain.entity.Contact;
import org.coders.contactapp.dto.ContactDTO;
import org.coders.contactapp.mapper.ContactMapper;
import org.coders.contactapp.service.ContactService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.coders.contactapp.constants.AppConstants.IMAGE_DIRECTORY;

@RestController
@RequiredArgsConstructor
@RequestMapping( "/contacts")
public class ContactController {
    private final ContactService contactService;
    private final ContactMapper dtoMapper;
    @PostMapping
    public ResponseEntity<ContactDTO> createContact(@RequestBody ContactDTO contactDTO) {
        return ResponseEntity.ok().body(contactService.createContact(contactDTO));
    }
    @GetMapping
    public ResponseEntity<Page<Contact>> getAllContacts(@RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "size", defaultValue = "10") int size){
        return ResponseEntity.ok().body(contactService.getAllContacts(page, size));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContact(@PathVariable String id) throws ContactNotFoundException {
        return ResponseEntity.ok().body(dtoMapper.toContactDTO(contactService.getContact(id)));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ContactDTO> updateContact(@PathVariable String id,
                                                    ContactDTO contactDTO) throws ContactNotFoundException {
        Contact contact = contactService.getContact(id);
        contactDTO.setId(contact.getId());
        return ResponseEntity.ok().body(contactService.createContact(contactDTO));
    }
    @DeleteMapping("/{id}")
    public void  deleteContact(@PathVariable String id) throws ContactNotFoundException {
        contactService.deleteContact(id);
    }

    @PutMapping("/image")
    public ResponseEntity<String> uploadImg(@RequestParam(name = "id") String id,
                                            @RequestParam(name = "file")MultipartFile  file) throws ContactNotFoundException {
        return ResponseEntity.ok().body(contactService.uploadImg(id, file));
    }
    @GetMapping("/image/{filename}")
    public byte[] getImage(@PathVariable(value = "filename") String filename) throws IOException {
        return Files.readAllBytes(Paths.get(IMAGE_DIRECTORY + filename));
    }



}
