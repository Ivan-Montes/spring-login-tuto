package dev.ime.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.ime.repository.UserRepository;

@Configuration
public class AppConfig {

	private final UserRepository userRepository;

	public AppConfig(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Bean
	UserDetailsService userDetailsService() {
		
		return new UserDetailsService() {

			@Override
			public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
				
				return userRepository.findByEmail(userName)
						.orElseThrow( () -> new UsernameNotFoundException("Error"));
			}
			
		};
		
	}
	
	@Bean
    AuthenticationProvider authenticationProvider(){
		
        DaoAuthenticationProvider authenticationProvider= new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
        
    }

    @Bean
    PasswordEncoder passwordEncoder() {
    	
        return new BCryptPasswordEncoder();
        
    }    

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        
    	return config.getAuthenticationManager();
        
    }
    
}
