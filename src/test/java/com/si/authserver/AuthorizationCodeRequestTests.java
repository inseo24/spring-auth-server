package com.si.authserver;

import com.si.authserver.core.authorization.JpaOAuth2AuthorizationService;
import com.si.authserver.core.client.JpaRegisteredClientRepository;
import com.si.authserver.data.CustomAuthorization;
import com.si.authserver.data.CustomClient;
import com.si.authserver.infra.mysql.AuthorizationRepository;
import com.si.authserver.infra.mysql.ClientRepository;
import jakarta.transaction.Transactional;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Transactional
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorizationCodeRequestTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JpaRegisteredClientRepository jpaRegisteredClientRepository;

    @Autowired
    private AuthorizationRepository authorizationRepository;

    @Autowired
    private JpaOAuth2AuthorizationService authorizationService;

    // todo : passwordEncoder 추가 필요

    @BeforeEach
    public void setup() {
        RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("test-client")
                .clientSecret("test-secret")
                .clientName("Test Client")
                .redirectUris(s -> s.addAll(Set.of("http://localhost:8080/authorized")))
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .scope("openid")
                .scope("profile")
                .scope("email")
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofMinutes(30))
                        .refreshTokenTimeToLive(Duration.ofDays(1))
                        .reuseRefreshTokens(true)
                        .build()
                )
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofMinutes(10))
                        .refreshTokenTimeToLive(Duration.ofDays(1))
                        .reuseRefreshTokens(true)
                        .build()
                ).build();
        jpaRegisteredClientRepository.save(client);
    }

    @Test
    public void testAuthorizationCodeRequest() throws Exception {
        mockMvc.perform(get("/oauth2/authorize")
                        .param("grant_type", "authorization_code")
                        .param("response_type", "code")
                        .param("client_id", "test-client")
                        .param("scope", "openid profile email")
                        .param("state", "test-state")
                        .param("redirect_uri", "http://localhost:8080/authorized"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("http://localhost:8080/login/oauth2/code/test-client*"));

        CustomAuthorization jpaAuthorization = authorizationRepository.findAll().get(0);
        OAuth2Authorization oauth2Authorization = authorizationService.findById(jpaAuthorization.getId());
        assertThat(oauth2Authorization).isNotNull();
        assertThat(oauth2Authorization.getRegisteredClientId()).isEqualTo("test-client");
        assertThat(oauth2Authorization.getPrincipalName()).isNull();
        assertThat(oauth2Authorization.getAuthorizationGrantType()).isEqualTo(AuthorizationGrantType.AUTHORIZATION_CODE.getValue());
        assertThat(oauth2Authorization.getAuthorizedScopes()).isEqualTo("openid profile email");
        assertThat(oauth2Authorization.getAttributes()).isNull();
    }

}
