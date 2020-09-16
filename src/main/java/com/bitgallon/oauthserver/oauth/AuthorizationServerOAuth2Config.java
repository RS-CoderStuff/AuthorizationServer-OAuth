package com.bitgallon.oauthserver.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.bitgallon.oauthserver.config.AppConfig;

@Configuration
@EnableAuthorizationServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import(OAuthServerSecurityConfig.class)
public class AuthorizationServerOAuth2Config extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AppConfig appConfig;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		 clients.jdbc(appConfig.dataSource()); //Get token from database
		/*
		 * clients.inMemory().withClient("web").authorizedGrantTypes(
		 * "client-credentials", "password", "authorization_code", "refresh_token")
		 * .authorities("ROLE_CLIENT", "ROLE_ANDROID_CLIENT").scopes("read", "write",
		 * "trust") .redirectUris("https://www.getpostman.com/oauth2/callback")
		 * .resourceIds("oauth2-resource").accessTokenValiditySeconds(5000).secret(
		 * "secret") .refreshTokenValiditySeconds(50000);
		 */
	}

	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("permitAll()")
		.allowFormAuthenticationForClients(); 
	}
	 
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		  endpoints.tokenStore(tokenStore()).accessTokenConverter(accessTokenConverter()) 
		  .authenticationManager(authenticationManager).userDetailsService(userDetailsService)
		  .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
	}
	
	@Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }
	
	@Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("QoiC$e0Wn0-rzZ&vmbZeyJhbGc!iOiJIUzUx!MiJ9");
        return converter;
    }
	
}

