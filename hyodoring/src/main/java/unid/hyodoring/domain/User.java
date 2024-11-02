package unid.hyodoring.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import unid.hyodoring.domain.common.BaseEntity;
import unid.hyodoring.domain.enums.Role;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, unique = true)
  private Long id;

  @Column
  private String name;

  @Column
  private String loginId;

  @Column
  private String loginPw;

  @Column
  private LocalDate birth;

  @Enumerated(EnumType.STRING)
  @Column
  private Role role;

  @Column
  private Integer hyodoPower;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "family_id", nullable = true, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  @Setter
  private Family family;

  @Builder.Default
  @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<HelloPost> sentHelloPosts = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<HelloPost> receivedHelloPosts = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<MwohaeRequest> sentRequests = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<MwohaeRequest> receivedRequests = new ArrayList<>();

  public void addSentHelloPost(HelloPost helloPost) {
    sentHelloPosts.add(helloPost);
    helloPost.setSender(this);
  }

  public void addReceivedHelloPost(HelloPost helloPost) {
    receivedHelloPosts.add(helloPost);
    helloPost.setReceiver(this);
  }

  public void addComment(Comment comment) {
    comments.add(comment);
    comment.setUser(this);
  }

  public void addSentRequest(MwohaeRequest request) {
    sentRequests.add(request);
    request.setSender(this);
  }

  public void addReceivedRequest(MwohaeRequest request) {
    receivedRequests.add(request);
    request.setReceiver(this);
  }
}
