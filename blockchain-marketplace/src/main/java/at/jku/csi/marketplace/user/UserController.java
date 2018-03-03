package at.jku.csi.marketplace.user;

import static at.jku.csi.marketplace.security.SecurityConstants.SIGN_UP_URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	private ApplicationUserRepository applicationUserRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@PostMapping(SIGN_UP_URL)
	public void signUp(@RequestBody() ApplicationUser user) {
		String username = user.getUsername();
		String password = bCryptPasswordEncoder.encode(user.getPassword());
		
		ApplicationUser newUser = new ApplicationUser();
		newUser.setUsername(username);
		newUser.setPassword(password);
		applicationUserRepository.save(newUser);
	}
}
