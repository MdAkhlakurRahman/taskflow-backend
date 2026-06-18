package com.taskflow.demo.repository;

import com.taskflow.demo.entity.User;
import com.taskflow.demo.projection.UserLightweightProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Page<User> findByNameContainingIgnoreCase(String search, Pageable pageable);

    @Query("SELECT u FROM User u WHERE LOWER(u.email) LIKE LOWER(CONCAT('%', '@', :domain))")
    Page<User> findUsersByDomain(@Param("domain") String domain, Pageable pageable);

    @Query("""
    SELECT u FROM User u
    WHERE LOWER(u.email) LIKE LOWER(CONCAT('%@', :domain))
    AND LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%'))
""")
    Page<User> findUsersByDomainAndName(@Param("search") String search, @Param("domain") String domain,
            Pageable pageable);

    @Query(value= """
            SELECT u.id,u.name FROM users u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%')) AND u.email LIKE LOWER(CONCAT('%','@', :searchDomain)) AND deleted = false
            """, nativeQuery = true, countQuery = "SELECT COUNT(*) FROM users u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%')) AND u.email LIKE LOWER(CONCAT('%','@', :searchDomain)) AND deleted = false")
    Page<UserLightweightProjection> findLightUsers(Pageable pageable, @Param("search") String search, @Param("searchDomain") String searchDomain);

    //Don't load tasks later. Load users and tasks together.
    @Query("SELECT u FROM User u JOIN FETCH u.tasks")
    List<User> findAllTasks();
}
