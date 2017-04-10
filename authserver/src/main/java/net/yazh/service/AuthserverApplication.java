package net.yazh.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@SpringBootApplication
public class AuthserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthserverApplication.class, args);
	}
}
@Configuration
@EnableAuthorizationServer
class AuthroizationServerConfig extends AuthorizationServerConfigurerAdapter{
	
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
	
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// TODO Auto-generated method stub
		super.configure(clients);
		clients.inMemory()
			.withClient("myclientid")
			.secret("mysecret")
			.authorizedGrantTypes("client_credentials")
			.scopes("myscope");
			
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// TODO Auto-generated method stub
		super.configure(endpoints);
		endpoints.tokenStore(tokenStore()).accessTokenConverter(tokenEnhancer());
	}
}
