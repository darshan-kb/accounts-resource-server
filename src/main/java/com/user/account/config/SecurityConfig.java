package com.user.account.config;

import com.user.account.filters.CustomCSRFFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
//import org.springframework.security.oauth2.client.registration.ClientRegistration;
//import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
//import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
//import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
//import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
//import org.springframework.security.oauth2.core.AuthorizationGrantType;
//import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
//import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${jwksuri}")
    String jwksUri;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

//        http.httpBasic(Customizer.withDefaults());
//        http.addFilterAfter(new CustomCSRFFilter(), CsrfFilter.class);
//
//        http.cors(c->{
//            CorsConfigurationSource source = s ->{
//                CorsConfiguration cc = new CorsConfiguration();
//                cc.setAllowCredentials(true);
//                cc.setAllowedOrigins(List.of("http://localhost:9090","http://localhost:8081"));
//                cc.setAllowedHeaders(List.of("*"));
//                cc.setAllowedMethods(List.of("*"));
//                return cc;
//            };
//            c.configurationSource(source);
//        });
        System.out.println("--------"+jwksUri);
        http.cors(AbstractHttpConfigurer::disable);
        http.oauth2ResourceServer(
                r -> r.jwt((j)-> {
                            j.jwkSetUri(jwksUri);
                            j.jwtAuthenticationConverter(new CustomJwtAuthenticationTokenConverter());
                        }
                )

        );
        http.authorizeHttpRequests((a) -> {
            a.requestMatchers("/account/users").hasRole("ADMIN");
            a.requestMatchers("/account/recharge/**").hasRole("ADMIN");
            a.anyRequest().authenticated();
        });
        //http.oauth2Client();
        return http.build();

    }

//    @Bean
//    public OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager(
//            ClientRegistrationRepository clientRegistrationRepository,
//            OAuth2AuthorizedClientRepository auth2AuthorizedClientRepository
//    ) {
//        OAuth2AuthorizedClientProvider provider =
//                OAuth2AuthorizedClientProviderBuilder.builder()
//                        .authorizationCode()
//                        .refreshToken()
//                        .clientCredentials()
//                        .build();
//
//        DefaultOAuth2AuthorizedClientManager defaultOAuth2AuthorizedClientManager
//                = new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, auth2AuthorizedClientRepository);
//        defaultOAuth2AuthorizedClientManager.setAuthorizedClientProvider(provider);
//
//        return defaultOAuth2AuthorizedClientManager;
//    }
//
//    @Bean
//    public ClientRegistrationRepository clientRegistrationRepository() {
//        ClientRegistration c1 = ClientRegistration.withRegistrationId("3")
//                .clientId("account")
//                .clientSecret("#Big2120")
//                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .tokenUri("http://localhost:8080/oauth2/token")
//                .scope(OidcScopes.OPENID)
//                .build();
//
//        InMemoryClientRegistrationRepository clientRegistrationRepository =
//                new InMemoryClientRegistrationRepository(c1);
//
//        return clientRegistrationRepository;
//    }
}
