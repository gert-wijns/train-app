package be.gert.trainapp.sm._shared.config.jwt;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Configuration
public class SecretKeyConfiguration {
	private String jwtSecret = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

	@Bean
	public SecretKey jwtKey() {
		byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
