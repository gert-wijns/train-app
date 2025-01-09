package be.gert.trainapp.sm.usermanagement;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import be.gert.trainapp.sm.usermanagement._repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserJpaRepository jpa;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = jpa.getById(new UserId(username));
		return new UserUserDetails(user);
	}
}
