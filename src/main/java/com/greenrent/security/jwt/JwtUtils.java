package com.greenrent.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.greenrent.exception.message.ErrorMessage;
import com.greenrent.security.service.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;



@Component//Başka yerde injekte edebilmek için
public class JwtUtils {

	private static Logger logger= LoggerFactory.getLogger(JwtUtils.class);
	
	@Value("${greenrent.app.jwtSecret}")
	private String jwtSecret;
	
	@Value("${greenrent.app.jwtExpirationMs}")
	private Long jwtExpirationMs;
	
	
	public String generateJwtToken(Authentication authentication) {
		//Currently login olan kullanıcı demek --> Principal
		UserDetailsImpl userDetails=(UserDetailsImpl)authentication.getPrincipal();
		
		return Jwts.builder()
				.setSubject(""+(userDetails.getId()))
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime()+jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	
	public Long getIdFromJwtToken(String token) {
		String strId=Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
		return Long.parseLong(strId);
	}
	
	//gelen tokenleri valide eden bir sınıf
	public boolean validateJwtToken(String token) {
		
		try {
			//parser parçalıyo ihtiyacımız olanı alıyorz.
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException |
				SignatureException | IllegalArgumentException e ) {
			logger.error(String.format(ErrorMessage.JWTTOKEN_ERROR_MESSAGE, e.getMessage()));
		}
		return false;
	}
	
}
