package com.si.authserver.data;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "`authorization_consent`")
@IdClass(CustomAuthorizationConsent.AuthorizationConsentId.class)
@Getter
@NoArgsConstructor
public class CustomAuthorizationConsent {

    @Id
    @Column(name = "registered_client_id")
    private String registeredClientId;

    @Id
    @Column(name = "principal_name")
    private String principalName;

    @Column(name = "authorities", length = 1000)
    private String authorities;

    @Builder
    public CustomAuthorizationConsent(String registeredClientId, String principalName, String authorities) {
        this.registeredClientId = registeredClientId;
        this.principalName = principalName;
        this.authorities = authorities;
    }

    public static class AuthorizationConsentId implements Serializable {
        private String registeredClientId;
        private String principalName;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AuthorizationConsentId that = (AuthorizationConsentId) o;
            return registeredClientId.equals(that.registeredClientId) && principalName.equals(that.principalName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(registeredClientId, principalName);
        }
    }
}
