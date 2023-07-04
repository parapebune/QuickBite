
package com.sda.QuickBite.repository;

import com.sda.QuickBite.entity.ForgotPassword;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForgotPasswordRepository extends CrudRepository<ForgotPassword, Long> {

    Optional<ForgotPassword> findByEmail(String email);

    Boolean existsByEmail(String email);
}

