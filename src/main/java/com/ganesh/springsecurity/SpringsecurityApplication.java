package com.ganesh.springsecurity;

import com.ganesh.springsecurity.models.Authority;
import com.ganesh.springsecurity.models.User;
import com.ganesh.springsecurity.repo.UserDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringsecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringsecurityApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserDetailsRepo userDetailsRepo;

@PostConstruct
	protected  void  init()
{
	List<Authority> authorityList=new ArrayList<>();
	authorityList.add( createAuthority("USER","USER_ROLE"));
	authorityList.add(createAuthority("ADMIN","ADMIN_ROLE"));

	 User user=new User();
	 user.setUserName("gani");
	 user.setFirstName("ganesh");
	 user.setLastName("neerati");
	 user.setPassword(passwordEncoder.encode("gani"));
	 user.setEnabled(true);
	 user.setAuthorities(authorityList);
	 userDetailsRepo.save(user);
}

private Authority createAuthority(String role,String roleDescription){
		Authority authority=new Authority();
		authority.setRoleCode(role);
		authority.setRoleDescription(roleDescription);
		return  authority;
}


}
