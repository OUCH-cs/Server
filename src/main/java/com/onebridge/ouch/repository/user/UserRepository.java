package com.onebridge.ouch.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onebridge.ouch.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByLoginId(String loginId);

	Optional<User> findByNickname(String nickName);

	@EntityGraph(attributePaths = {"language"}) // language 필드를 즉시 조인 (fetch join 쿼리 최적화)
	Optional<User> findWithLanguageById(Long id);

	@EntityGraph(attributePaths = {"nation"}) // language 필드를 즉시 조인 (fetch join 쿼리 최적화)
	Optional<User> findWithNationById(Long id);
}
