package be.gert.trainapp.sm.usermanagement.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import be.gert.trainapp.sm.usermanagement.UserId;
import be.gert.trainapp.sm.usermanagement._repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserJpaRepository jpa;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = jpa.getById(new UserId(username));
		return new User(
				user.id().id(),
				user.password(),
				user.roles().stream()
						.map(SimpleGrantedAuthority::new)
						.toList());
	}
}
