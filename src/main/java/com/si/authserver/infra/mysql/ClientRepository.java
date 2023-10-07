package com.si.authserver.infra.mysql;

import com.si.authserver.data.CustomClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<CustomClient, String> {

}
