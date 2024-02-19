package com.travellog.travellog.repositories;

import com.travellog.travellog.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITokenRepository extends JpaRepository<Token, Integer> {
  @Query("SELECT t FROM Token t WHERE t.user.id = :id AND (t.isExpired = false OR t.isRevoked = false)")
  List<Token> findAllValidTokenByUser(@Param("id") Integer id);

  Optional<Token> findByToken(String token);
}
