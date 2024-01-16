package org.coders.contactapp;


import org.coders.contactapp.dto.ContactDTO;
import org.coders.contactapp.service.ContactService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	@Bean
	CommandLineRunner commandLineRunner(ContactService contactService) {
		return args -> {
			ContactDTO contactDTO = new ContactDTO();
			contactDTO.setJob("Developper");
			contactDTO.setEmail("bil@gmail.com");
			contactDTO.setPhone("77 102 71 72");
			contactService.createContact(contactDTO);

			ContactDTO contactDTO2 = new ContactDTO();
			contactDTO2.setJob("Developper");
			contactDTO2.setEmail("ahk@gmail.com");
			contactDTO2.setPhone("77 108 71 72");
			contactService.createContact(contactDTO2);

		};
	}

}
