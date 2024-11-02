package unid.hyodoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unid.hyodoring.domain.Comment;
import unid.hyodoring.domain.HelloPost;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Long countByHelloPost(HelloPost helloPost);
}
