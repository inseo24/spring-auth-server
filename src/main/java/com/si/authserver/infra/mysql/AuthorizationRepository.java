package com.si.authserver.infra.mysql;

import com.si.authserver.data.CustomAuthorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationRepository extends JpaRepository<CustomAuthorization, String> {
}
