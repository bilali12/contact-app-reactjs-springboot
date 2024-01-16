package org.coders.contactapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.coders.contactapp.Exception.ContactNotFoundException;
import org.coders.contactapp.domain.entity.Contact;
import org.coders.contactapp.dto.ContactDTO;
import org.coders.contactapp.mapper.ContactMapper;
import org.coders.contactapp.repository.ContactRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.coders.contactapp.constants.AppConstants.IMAGE_DIRECTORY;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class ContactService {
    private final ContactRepository contactRepository;
    private final ContactMapper dtoMapper;

    public Page<Contact> getAllContacts(int page, int size) {
        return contactRepository.findAll(PageRequest.of(page, size, Sort.by("fullName")));
    }

    public Contact getContact(String id) throws ContactNotFoundException {

        return  contactRepository.findById(id).orElseThrow( () ->
                new ContactNotFoundException("Contact not found..."));
    }

    public ContactDTO createContact(ContactDTO contactDTO) {

        return dtoMapper.toContactDTO(contactRepository.save(dtoMapper.fromContactDTO(contactDTO)));
    }

    public void deleteContact(String id) throws ContactNotFoundException {
        Optional<Contact> contact = contactRepository.findById(id);
        if (contact.isPresent()) {
            contactRepository.deleteById(id);
        }
        if(contact.isEmpty()) {
            throw new ContactNotFoundException("Contact not found...");
        }
    }

    public String uploadImg(String id, MultipartFile file) throws ContactNotFoundException {
        log.info("Saving user image ID: " + id);
        Contact contact = getContact(id);
        String imgUrl = imgFunction.apply(id, file);
        contact.setImgUrl(imgUrl);
        contactRepository.save(contact);
        return imgUrl;
    }

    private final Function<String, String> fileExtension = (fileName) -> {
        Optional.of(fileName).filter(
                name -> name.contains(".")
        ).map(name -> "." + name.substring(fileName.lastIndexOf(".") +  1)).orElse(".png");

        return "";
    };
    private final BiFunction<String, MultipartFile, String>
    imgFunction = (id, img) -> {
        String filename = id + fileExtension.apply(img.getOriginalFilename());
        try {
            Path imgStoragePath = Paths.get(IMAGE_DIRECTORY).toAbsolutePath().normalize();
            if(!Files.exists(imgStoragePath)) {
                Files.createDirectories(imgStoragePath);
            }
            Files.copy(img.getInputStream(), imgStoragePath.resolve(id + filename), REPLACE_EXISTING);
            return ServletUriComponentsBuilder.fromCurrentContextPath().path("/contacts/image/" +  filename).toString();
        }catch (Exception e) {
            throw new RuntimeException("Cannot save the img");
        }

    };


}
