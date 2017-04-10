package net.yazh.service;

import java.security.Principal;

import javax.annotation.security.RolesAllowed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ResourceserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResourceserverApplication.class, args);
	}
}

@RestController
class MyResource {
	
	
	@RequestMapping("/")
	public Principal resource(Principal user){
		return user;
	}
	
}
@Configuration
@EnableResourceServer
class ResourceServiceConfig extends ResourceServerConfigurerAdapter {
	
	@Bean
	public JwtAccessTokenConverter tokenEnhancer(){
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey("123");
		return converter;
	}
	
	@Bean
	public JwtTokenStore tokenStore(){
		return new JwtTokenStore(tokenEnhancer());
	}
	
	
	@Bean
    public DefaultTokenServices tokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		// TODO Auto-generated method stub
		super.configure(resources);
		resources.tokenServices(tokenServices());
	}

}
