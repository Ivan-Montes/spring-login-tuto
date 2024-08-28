package dev.ime.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import dev.ime.config.ConfigProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtils {

    private static final Long EXPIRATION_TIME = 900_000L * 2; // 30 minutes
    private final ConfigProperties configProperties;    
    private final String secretKey;

    public JwtUtils(ConfigProperties configProperties) {
    	
    	this.configProperties = configProperties;
    	secretKey = this.configProperties.getKey();
    	
    }
    
	public String getToken(UserDetails user) {
		
		return generateToken(new HashMap<>(), user);
		
	}
	
	public String generateToken(Map<String,Object> extraClaims, UserDetails user) {
		
		return Jwts
				.builder()
				.claims(extraClaims)
				.subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(getKey())
				.compact();
		
	}
	
	private Key getKey() {		
		
       byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
       return Keys.hmacShaKeyFor(keyBytes);
       
	}

	private Claims extractAllClaims(String token) {

    	SecretKey secret = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    	return Jwts.parser()
	        .verifyWith(secret)
	        .build()
	        .parseSignedClaims(token)
	        .getPayload();
	}
		
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
        
    }
	
	public boolean validateToken(String token, UserDetails userDetails) {
		
        final String username = getUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    
	}

    public String getUsername(String token) {
    	
        return extractClaim(token, Claims::getSubject);
        
    }

	public boolean isTokenExpired(String token) {
		
		return getExpiration(token).before(new Date());
	        
	}
	 
	private Date getExpiration(String token){
	   
        return extractClaim(token, Claims::getExpiration);
        
	}	 
	 
}
