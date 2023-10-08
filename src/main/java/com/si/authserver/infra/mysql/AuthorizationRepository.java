package com.si.authserver.infra.mysql;

import com.si.authserver.data.CustomAuthorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorizationRepository extends JpaRepository<CustomAuthorization, String> {
    Optional<CustomAuthorization> findByState(String state);

    Optional<CustomAuthorization> findByAuthorizationCodeValue(String authorizationCode);

    Optional<CustomAuthorization> findByAccessTokenValue(String accessToken);

    Optional<CustomAuthorization> findByRefreshTokenValue(String refreshToken);

    Optional<CustomAuthorization> findByOidcIdTokenValue(String idToken);

    Optional<CustomAuthorization> findByUserCodeValue(String userCode);

    Optional<CustomAuthorization> findByDeviceCodeValue(String deviceCode);

    @Query("select a from CustomAuthorization a where a.state = :token" +
            " or a.authorizationCodeValue = :token" +
            " or a.accessTokenValue = :token" +
            " or a.refreshTokenValue = :token" +
            " or a.oidcIdTokenValue = :token" +
            " or a.userCodeValue = :token" +
            " or a.deviceCodeValue = :token"
    )
    Optional<CustomAuthorization> findByStateOrAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValueOrOidcIdTokenValueOrUserCodeValueOrDeviceCodeValue(@Param("token") String token);
}
