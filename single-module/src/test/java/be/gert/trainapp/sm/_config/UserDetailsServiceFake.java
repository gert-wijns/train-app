package be.gert.trainapp.sm._config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceFake implements UserDetailsService {
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return new UserDetails() {
			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				return List.of();
			}

			@Override
			public String getPassword() {
				return "";
			}

			@Override
			public String getUsername() {
				return "";
			}
		};
	}
}
