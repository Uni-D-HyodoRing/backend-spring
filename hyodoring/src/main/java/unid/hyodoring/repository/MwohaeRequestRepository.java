package unid.hyodoring.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unid.hyodoring.domain.MwohaeRequest;

@Repository
public interface MwohaeRequestRepository extends JpaRepository<MwohaeRequest, Long> {

  List<MwohaeRequest> findAllByReceiverId(Long id);
  Optional<MwohaeRequest> findFirstBySenderIdOrderByCreatedAtDesc(Long senderId);
}
