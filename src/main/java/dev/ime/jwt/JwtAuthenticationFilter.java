package dev.ime.jwt;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	private final UserDetailsService userDetailsService;
	private final JwtUtils jwtService;
	
	public JwtAuthenticationFilter(UserDetailsService userDetailsService, JwtUtils jwtService) {
		super();
		this.userDetailsService = userDetailsService;
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		Optional<String> optToken = recoverToken(request);
		
		 if ( optToken.isEmpty() ){
			 
	            filterChain.doFilter(request, response);
	            return;
	            
	     }
		
		String token = optToken.get();
		String userName = jwtService.getUsername(token);
		
		if ( userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
		
			if ( jwtService.validateToken(token, userDetails) ) {
			
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				
			}
			
		}
		
		filterChain.doFilter(request, response);
		
	}
	
	private Optional<String> recoverToken(HttpServletRequest request) {
		
		return Optional.ofNullable(request.getHeader("Authorization"))
		.filter( string -> string.startsWith("Bearer "))
		.map( string -> string.substring(7));
		
	}

}
