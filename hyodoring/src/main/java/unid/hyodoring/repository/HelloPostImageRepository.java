package unid.hyodoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unid.hyodoring.domain.HelloPost;
import unid.hyodoring.domain.HelloPostImage;

import java.util.List;

@Repository
public interface HelloPostImageRepository extends JpaRepository<HelloPostImage, Long> {

    List<HelloPostImage> findAllByHelloPost(HelloPost helloPost);
}
