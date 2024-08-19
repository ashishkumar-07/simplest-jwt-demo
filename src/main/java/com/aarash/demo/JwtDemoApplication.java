package com.aarash.demo;

import com.aarash.demo.controller.UserController;
import com.aarash.demo.controller.service.UserService;
import com.aarash.demo.dto.UserRegisterDto;
import com.aarash.demo.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class JwtDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtDemoApplication.class, args);
	}
	@Autowired
	UserService userService;

	@EventListener
	public void register1stUser(ApplicationReadyEvent event){
		createTestAdmin();
		createTestUser();
	}

	private void createTestAdmin() {
        try {
            UserRegisterDto userRegisterDto = new UserRegisterDto();
            userRegisterDto.setUsername("admin");
            userRegisterDto.setPassword("admin");
            userRegisterDto.setEmail("admin@admin.com");
            userRegisterDto.setFirstName("Dummy");
            userRegisterDto.setLastName("Admin");
            userRegisterDto.setRole(UserRole.ADMIN);
            System.out.println("Creating ADMIN");
			userService.registerUser(userRegisterDto);
        } catch (Exception e) {
			System.out.println(e.getMessage());
        }
    }

	private void createTestUser() {
        try {
            UserRegisterDto userRegisterDto = new UserRegisterDto();
            userRegisterDto.setUsername("user");
            userRegisterDto.setPassword("user");
            userRegisterDto.setEmail("user@user.com");
            userRegisterDto.setFirstName("Dummy");
            userRegisterDto.setLastName("User");
            userRegisterDto.setRole(UserRole.USER);
            System.out.println("Creating USER");
			userService.registerUser(userRegisterDto);
        } catch (Exception e) {
			System.out.println(e.getMessage());
        }
    }

}
