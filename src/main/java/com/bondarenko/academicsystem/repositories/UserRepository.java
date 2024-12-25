package com.bondarenko.academicsystem.repositories;

import com.bondarenko.academicsystem.dto.LoginDto;
import com.bondarenko.academicsystem.dto.user.UserIdRoleDto;
import com.bondarenko.academicsystem.enteties.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {


    @Query("SELECT new com.bondarenko.academicsystem.dto.LoginDto(u.username, u.password, u.role) " +
            "FROM User u " +
            "WHERE u.username = :username")
    Optional<LoginDto> findByUsername(@Param("username") String username);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM User u " +
            "WHERE u.username = :username")
    void deleteByUsername(String username);

    boolean existsByUsername(String username);

    @Query("SELECT new com.bondarenko.academicsystem.dto.user.UserIdRoleDto(u.id, u.role) " +
            "FROM User u " +
            "WHERE u.username = :username")
    Optional<UserIdRoleDto> findUserIdByUsername(@Param("username") String username);

}
