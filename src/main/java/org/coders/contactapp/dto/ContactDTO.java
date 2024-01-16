package org.coders.contactapp.dto;

import lombok.Data;

@Data
public class ContactDTO {
    private String id;
    private String fullName;
    private String email;
    private String job;
    private String phone;
    private String address;
    private String status;
    private String imgUrl;
}
