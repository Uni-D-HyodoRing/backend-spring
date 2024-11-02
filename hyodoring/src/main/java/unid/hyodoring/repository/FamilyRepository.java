package unid.hyodoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unid.hyodoring.domain.Family;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {
}
