package com.si.authserver.data;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "`client`")
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class CustomClient {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "client_id")
    private String clientId;

    @Column(name = "client_id_issued_at")
    private Instant clientIdIssuedAt;

    @Column(name = "client_secret")
    private String clientSecret;

    @Column(name = "client_secret_expires_at")
    private Instant clientSecretExpiresAt;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "client_authentication_methods", length = 1000)
    private String clientAuthenticationMethods;

    @Column(name = "authorization_grant_types", length = 1000)
    private String authorizationGrantTypes;

    @Column(name = "redirect_uris", length = 1000)
    private String redirectUris;

    @Column(name = "post_logout_redirect_uris", length = 1000)
    private String postLogoutRedirectUris;

    @Column(name = "scopes", length = 1000)
    private String scopes;

    @Column(name = "client_settings", length = 2000)
    private String clientSettings;

    @Column(name = "token_settings", length = 2000)
    private String tokenSettings;

    @Builder
    public CustomClient(String clientId, Instant clientIdIssuedAt, String clientSecret, Instant clientSecretExpiresAt, String clientName, String clientAuthenticationMethods, String authorizationGrantTypes, String redirectUris, String postLogoutRedirectUris, String scopes, String clientSettings, String tokenSettings) {
        this.clientId = clientId;
        this.clientIdIssuedAt = clientIdIssuedAt;
        this.clientSecret = clientSecret;
        this.clientSecretExpiresAt = clientSecretExpiresAt;
        this.clientName = clientName;
        this.clientAuthenticationMethods = clientAuthenticationMethods;
        this.authorizationGrantTypes = authorizationGrantTypes;
        this.redirectUris = redirectUris;
        this.postLogoutRedirectUris = postLogoutRedirectUris;
        this.scopes = scopes;
        this.clientSettings = clientSettings;
        this.tokenSettings = tokenSettings;
    }
}