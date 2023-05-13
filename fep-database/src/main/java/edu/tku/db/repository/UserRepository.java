package edu.tku.db.repository;

import edu.tku.db.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    List<User> findAllByUserIdAndUserName(String userId, String userName);
}
