package unid.hyodoring.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unid.hyodoring.domain.Family;
import unid.hyodoring.domain.User;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {

  Optional<Family> findByJoinCode(String joinCode);
  Boolean existsByJoinCode(String joinCode);
}
