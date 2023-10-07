package com.si.authserver.infra.mysql;

import com.si.authserver.data.CustomAuthorizationConsent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorizationConsentRepository extends JpaRepository<CustomAuthorizationConsent, CustomAuthorizationConsent.AuthorizationConsentId> {
    Optional<CustomAuthorizationConsent> findByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
    void deleteByRegisteredClientIdAndPrincipalName(String registeredClientId, String principalName);
}
