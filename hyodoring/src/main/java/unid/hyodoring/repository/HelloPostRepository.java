package unid.hyodoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unid.hyodoring.domain.Family;
import unid.hyodoring.domain.HelloPost;

import java.util.List;

@Repository
public interface HelloPostRepository extends JpaRepository<HelloPost, Long> {

    List<HelloPost> findAllByFamily(Family family);
}
