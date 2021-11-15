package com.javaproject.javaprojectthree;

import com.javaproject.javaprojectthree.model.Charity;
import com.javaproject.javaprojectthree.model.Role;
import com.javaproject.javaprojectthree.model.User;
import com.javaproject.javaprojectthree.repository.CharityRepository;
import com.javaproject.javaprojectthree.repository.UserRepository;
import com.javaproject.javaprojectthree.service.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@ServletComponentScan
@SpringBootApplication
public class JavaProjectThreeApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaProjectThreeApplication.class, args);
	}

	private PasswordEncoder passwordEncoder;
	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder){this.passwordEncoder = passwordEncoder;}
	private UserRepository userRepository;
	@Autowired
	public void setUserRepository(UserRepository userRepository){this.userRepository = userRepository;}

	private CharityRepository charityRepository;
	@Autowired
	public void setCharityRepository(CharityRepository charityRepository){this.charityRepository = charityRepository;}

	@Bean
	CommandLineRunner run(InitService initService) {
		return args -> {

			initService.addRole(new Role(null, "ROLE_SENDER"));
			initService.addRole(new Role(null, "ROLE_RECEIVER"));

			initService.addUser(new User(null,"admin","admin@admin.com",passwordEncoder.encode("admin")));
			initService.addUser(new User(null,"user","user@user.com",passwordEncoder.encode("user")));
			initService.addUser(new User(null, "Raul", "Rosales", "raulr", "raulr@user.com", passwordEncoder.encode("password1")));

			initService.addUserRole("admin","ROLE_SENDER");
			initService.addUserRole("admin","ROLE_RECEIVER");
			initService.addUserRole("user","ROLE_SENDER");
			initService.addUserRole("user","ROLE_RECEIVER");

			initService.createCharity("Red Cross", "Red Cross goal is to provide to those in need", 1000, 500,true, "https://www.redcross.org/content/dam/redcross/red-cross-logos/American-Red-Cross_Logo_1200x630.jpg");

		};
	}
}
