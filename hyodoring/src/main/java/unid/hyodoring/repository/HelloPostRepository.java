package unid.hyodoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unid.hyodoring.domain.HelloPost;

@Repository
public interface HelloPostRepository extends JpaRepository<HelloPost, Long> {
}
