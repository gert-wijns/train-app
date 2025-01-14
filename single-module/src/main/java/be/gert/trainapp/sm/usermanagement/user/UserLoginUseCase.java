package be.gert.trainapp.sm.usermanagement.user;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import be.gert.trainapp.api.usermanagement.generated.UserLoginUseCaseApi;
import be.gert.trainapp.api.usermanagement.generated.model.UserLoginRequest;
import be.gert.trainapp.api.usermanagement.generated.model.UserLoginResponse;
import be.gert.trainapp.sm._shared.usecase.DomainUseCase;
import io.jsonwebtoken.Jwts;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@DomainUseCase
@RequiredArgsConstructor
public class UserLoginUseCase implements UserLoginUseCaseApi {
	private final AuthenticationManager authenticationManager;
	private final SecretKey jwtKey;
	private final int jwtExpirationMs = 8640000;

	@Override
	@Transactional
	public UserLoginResponse execute(UserLoginRequest request) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		String jwt = generateJwtToken(authentication);
		return new UserLoginResponse().token(jwt);
	}

	private String generateJwtToken(Authentication authentication) {
		User userPrincipal = (User) authentication.getPrincipal();
		return Jwts.builder()
				.subject((userPrincipal.getUsername()))
				.issuedAt(new Date())
				.expiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.claim("roles", userPrincipal.getAuthorities().stream()
						.map(GrantedAuthority::getAuthority)
						.toList())
				.signWith(jwtKey)
				.compact();

	}

}
