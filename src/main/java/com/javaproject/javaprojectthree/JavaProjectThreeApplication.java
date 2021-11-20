package com.javaproject.javaprojectthree;

import com.javaproject.javaprojectthree.model.Charity;
import com.javaproject.javaprojectthree.model.Role;
import com.javaproject.javaprojectthree.model.User;
import com.javaproject.javaprojectthree.repository.UserRepository;
import com.javaproject.javaprojectthree.security.MyUserDetails;
import com.javaproject.javaprojectthree.service.InitService;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.ShippingAddress;
import com.paypal.api.payments.Transaction;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@ServletComponentScan
@SpringBootApplication
public class JavaProjectThreeApplication {
	public static UserDetails myUserDetails = null;
	public static Charity charity = null;
	public static String siteURL = "http://givesapp-env.eba-j53cbhw3.us-east-2.elasticbeanstalk.com/";
	public static PayerInfo payerInfo = null;
	public static Transaction transaction = null;
	public static ShippingAddress shippingAddress = null;

	public static void main(String[] args) {
		SpringApplication.run(JavaProjectThreeApplication.class, args);
	}

	private PasswordEncoder passwordEncoder;
	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder){this.passwordEncoder = passwordEncoder;}
	private UserRepository userRepository;
	@Autowired
	public void setUserRepository(UserRepository userRepository){this.userRepository = userRepository;}

	Contact contact = new Contact().name("Matthew James & Raul Rosales").email("matjames@paypal.com").url("https://www.linkedin.com/in/matthewljames/");

	@Bean
	public OpenAPI customOpenAPI(@Value("${info.app.description}") String appDesciption, @Value("${info.app.version}") String appVersion) {
		return new OpenAPI()
				.info(new Info()
						.title("SpringBoot JAVA RestAPI")
						.contact(contact)
						.version("1.1")
						.description("Fully functional marketplace RestAPI")
						.termsOfService("http://swagger.io/terms/")
						.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}

	@Bean
	public ViewResolver getViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);
		return resolver;
	}

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

			initService.createCharity("Red Cross", "Red Cross goal is to provide to those in need", 1000, 500,true, "https://www.redcross.org/content/dam/redcross/red-cross-logos/American-Red-Cross_Logo_1200x630.jpg", "AQyXFrhWBt3G73-_sGK4Rez_x2uSnDUAus2q9ebccrJa61jfj-cI77kjWMTTvMEL1aSN09xZHo6Wbwu4", "EKFNRo5k5E8SQ8ZIp3VkjCC1tWw2ryTEdsJDfRDohXbxdHA0cb0tRY9oNDxGacJUQDEW7sgjKP5VmNPI","sandbox");
			initService.createCharity("American Humane", "To ensure the safety, welfare and well-being of animals; to serve in promoting and nurturing the bonds between animals and humans.", 10000, 3500, true, "https://www.findingshelter.org/wp-content/uploads/2019/04/1200px-HSUS_logo.svg.png", "AU5ps_9-may2jfB81wwVZVk3iIOAtkqnS3JKu2LH4xh1gGWFDPN4U_NIi4AnQWBSH2Kwv3Remv8L3o6s", "EJOstlfwZnPiQ9wjFZqdknKzvEda0zlb4OOs7qCkvnIcvhwu5BGrrNQCY-W_gY-9ZuZQI66vI5xzB04-", "sandbox");
			initService.createCharity("Breast Cancer Research Foundation", "To prevent and cure breast cancer by advancing the world's most promising research.", 100000, 20000, true, "https://d2g8igdw686xgo.cloudfront.net/35039048_1543798258447629_r.jpeg");
			initService.createCharity("Child Find of America", "To prevent and resolve child abduction and the family conflicts that can lead to abduction and abuse.", 25000, 10000, true, "http://childfindofamerica.org/wp-content/uploads/2015/04/Loaction-Services-01.jpg");
			initService.createCharity("Children Incorporated", "To arrange and provide funding for supplies and services to meet the basic and educational needs of impoverished children.", 30000, 8000, true, "https://childrenincorporated.org/wp-content/uploads/2017/07/ChildrenIncInstagramLogo_Option02.jpg");
			initService.createCharity("GLAAD", "Lesbian, gay, bisexual, transgender and queer (LGBTQ) media advocacy organization. Ensures fair, accurate, and inclusive representation that leads to 100% LGBTQ acceptance.", 10000, 5000, true, "https://www.glaad.org/sites/default/files/GLAAD-Social-Share.jpg");
			initService.createCharity("National Park Trust", "Preserving parks today; creating park stewards for tomorrow.", 80000, 30000, true, "https://wi.cdn.brillianthosting.app/uploads/2017/07/url2Ffmi2Fxml2Fcnt2FNational20Park20Trust-2-e1553782434308.jpg");
			initService.createCharity("Action Against Hunger-USA", "To save lives by preventing, detecting, and treating under-nutrition, particularly during and after disasters and conflicts. From crisis to sustainability, tackles the direct and underlying causes of hunger through integrated, holistic solutions.", 250000, 38000, true, "https://upload.wikimedia.org/wikipedia/commons/1/19/Eng_Col_RGB_-_Copy.png");
			initService.createCharity("Comic Relief", "To drive positive change through the power of entertainment; undertakes charitable & educational activities with the aim of eliminating poverty & improving conditions for children & disadvantaged persons in the U.S. & throughout the world.", 125000, 75000, true, "https://www.moreaboutadvertising.com/wp-content/uploads/2020/05/Comic-Relief-scaled.jpg");
			initService.createCharity("NAMI", "Dedicated to improving the lives of persons and their families living with serious mental illness.", 200000, 100000, true, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/58/NAMI_logo.svg/1200px-NAMI_logo.svg.png");
			initService.createCharity("National Council on Aging", "To improve the lives of millions of older adults, especially those who are struggling.", 250000, 25000, true, "https://mma.prnewswire.com/media/1577756/National_Council_on_Aging_Logo.jpg?p=facebook");
			initService.createCharity("Fisher House Foundation", "To construct and furnish Fisher Houses, provide assistance and scholarships to military families & children, and enhance the quality of life for veterans & armed forces members.", 800000, 500000, true, "https://upload.wikimedia.org/wikipedia/en/thumb/7/77/Fisher_House_Foundation_logo.svg/1200px-Fisher_House_Foundation_logo.svg.png");
			initService.createCharity("Wounded Warriors Family Support", "To provide better quality of life to military personnel and their families wounded, injured or killed in Iraq & Afghanistan, and to provide financial funding to other veteran organizations with similar missions.", 125000, 100000, true, "https://cdn.greatnonprofits.org/images/logos/WWFSLogoStack_PLAIN_WHITE_FLAG-1024x547.jpg");
			initService.createCharity("Scholarship America", "To mobilize America through scholarships and educational support to make post-secondary education possible for all students.", 450000, 350000, true, "https://www.nshss.org/media/32246/scholarship-america-260-x-260.png?anchor=center&mode=crop&width=260&height=260");
			initService.createCharity("DonorsChoose.org", "Engages the public in public schools by giving people a simple, accountable and personal way to address educational inequity; envisions a nation where children in every community have the tools and experiences needed for an excellent education.", 600000, 550000, true, "https://upload.wikimedia.org/wikipedia/commons/e/ee/DonorsChoose-Logo.png");

			initService.addUserToCharity("user",1L);

		};
	}
}
