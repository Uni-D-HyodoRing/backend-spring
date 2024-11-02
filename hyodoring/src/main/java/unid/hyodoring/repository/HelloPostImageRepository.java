package unid.hyodoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unid.hyodoring.domain.HelloPostImage;

@Repository
public interface HelloPostImageRepository extends JpaRepository<HelloPostImage, Long> {
}
