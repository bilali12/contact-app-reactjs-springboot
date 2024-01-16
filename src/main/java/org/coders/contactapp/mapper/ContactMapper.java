package org.coders.contactapp.mapper;

import org.coders.contactapp.domain.entity.Contact;
import org.coders.contactapp.dto.ContactDTO;
import org.springframework.stereotype.Service;

@Service
public class ContactMapper {
    public ContactDTO toContactDTO(Contact contact) {
        ContactDTO contactDTO = new ContactDTO();
        contactDTO.setId(contact.getId());
        contactDTO.setAddress(contact.getAddress());
        contactDTO.setJob(contact.getJob());
        contactDTO.setEmail(contact.getEmail());
        contactDTO.setFullName(contact.getFullName());
        contactDTO.setPhone(contact.getPhone());
        contactDTO.setImgUrl(contact.getImgUrl());
        contactDTO.setStatus(contact.getStatus());
        return contactDTO;
    }
    public Contact fromContactDTO(ContactDTO contactDTO) {
        Contact contact = new Contact();
        contact.setId(contactDTO.getId());
        contact.setEmail(contactDTO.getEmail());
        contact.setFullName(contactDTO.getFullName());
        contact.setJob(contactDTO.getJob());
        contact.setPhone(contactDTO.getPhone());
        contact.setImgUrl(contactDTO.getImgUrl());
        contact.setAddress(contactDTO.getAddress());
        contact.setStatus(contactDTO.getStatus());
        return contact;
    }
}
