package edu.tku.db.repository;

import edu.tku.db.model.Func;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncRepository extends JpaRepository<Func, String> {
}
