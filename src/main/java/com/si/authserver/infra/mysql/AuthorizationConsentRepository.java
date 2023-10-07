package com.si.authserver.infra.mysql;

import com.si.authserver.data.CustomAuthorizationConsent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationConsentRepository extends JpaRepository<CustomAuthorizationConsent, CustomAuthorizationConsent.AuthorizationConsentId> {
}
