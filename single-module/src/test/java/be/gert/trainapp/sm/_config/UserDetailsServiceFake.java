package be.gert.trainapp.sm._config;

import static java.util.Objects.requireNonNull;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.test.context.event.annotation.BeforeTestExecution;

@Component
public class UserDetailsServiceFake implements UserDetailsService {

	public static void withoutRoles() {
		setAuthentication(Set.of());
	}

	public static void withRoles(String... roles) {
		var authorities = Stream.of(requireNonNull(roles))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toSet());
	}

	private static void setAuthentication(Set<SimpleGrantedAuthority> authorities) {
		var token = new UsernamePasswordAuthenticationToken(
				"test", "test", authorities);
		SecurityContextHolder.getContext().setAuthentication(token);
	}

	@BeforeTestExecution
	void reset() {
		withoutRoles();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		throw new NotImplementedException("Shouldn't be called, else implement please!");
	}
}
