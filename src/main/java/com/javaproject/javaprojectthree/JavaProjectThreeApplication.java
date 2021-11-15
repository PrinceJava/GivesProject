package com.javaproject.javaprojectthree;

import com.javaproject.javaprojectthree.model.Role;
import com.javaproject.javaprojectthree.model.User;
import com.javaproject.javaprojectthree.repository.CharityRepository;
import com.javaproject.javaprojectthree.repository.CredentialRepository;
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

	private CredentialRepository credentialRepository;
	@Autowired
	public void setCredentialsRepository(CredentialRepository credentialsRepository){this.credentialRepository = credentialsRepository;}

	@Bean
	CommandLineRunner run(InitService initService) {
		return args -> {

			initService.addRole(new Role(null, "ROLE_SENDER"));
			initService.addRole(new Role(null, "ROLE_RECEIVER"));

			initService.addUser(new User(null,"admin","admin@admin.com",passwordEncoder.encode("admin")));
			initService.addUser(new User(null,"user","user@user.com",passwordEncoder.encode("user")));
			initService.addUser(new User(null, "Raul", "Rosales", "raulr", "raulr@user.com", passwordEncoder.encode("password1")));
			initService.addUser(new User(null, "Matthew", "James", "mattj", "mattj@user.com", passwordEncoder.encode("password2")));

			initService.addUserRole("admin","ROLE_SENDER");
			initService.addUserRole("admin","ROLE_RECEIVER");
			initService.addUserRole("user","ROLE_SENDER");
			initService.addUserRole("user","ROLE_RECEIVER");
			initService.addUserRole("raulr","ROLE_SENDER");

			initService.createCharity("Red Cross", "Red Cross goal is to provide to those in need", 1000, 500,true, "https://www.redcross.org/content/dam/redcross/red-cross-logos/American-Red-Cross_Logo_1200x630.jpg");
			initService.createCharity("American Humane", "To ensure the safety, welfare and well-being of animals; to serve in promoting and nurturing the bonds between animals and humans.", 10000, 3500, true, "https://static.wikia.nocookie.net/logopedia/images/b/bf/Bandicam_2016-04-15_07-25-17-122.jpg/revision/latest?cb=20160415123103");
			initService.createCharity("Breast Cancer Research Foundation", "To prevent and cure breast cancer by advancing the world's most promising research.", 100000, 20000, true, "https://d2g8igdw686xgo.cloudfront.net/35039048_1543798258447629_r.jpeg");
			initService.createCharity("Child Find of America", "To prevent and resolve child abduction and the family conflicts that can lead to abduction and abuse.", 25000, 10000, true, "http://childfindofamerica.org/wp-content/uploads/2015/04/Loaction-Services-01.jpg");
			initService.createCharity("Children Incorporated", "To arrange and provide funding for supplies and services to meet the basic and educational needs of impoverished children.", 30000, 8000, true, "https://childrenincorporated.org/wp-content/uploads/2017/07/ChildrenIncInstagramLogo_Option02.jpg");
			initService.createCharity("GLAAD", "Lesbian, gay, bisexual, transgender and queer (LGBTQ) media advocacy organization. Ensures fair, accurate, and inclusive representation that leads to 100% LGBTQ acceptance.", 10000, 5000, true, "https://www.glaad.org/sites/default/files/GLAAD-Social-Share.jpg");

			initService.addUserToCharity("user",1L);

		};
	}
}
