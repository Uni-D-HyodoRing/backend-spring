package unid.hyodoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unid.hyodoring.domain.MwohaeRequest;

@Repository
public interface MwohaeRequestRepository extends JpaRepository<MwohaeRequest, Long> {
}
