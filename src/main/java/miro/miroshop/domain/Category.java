package miro.miroshop.domain;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import miro.miroshop.domain.item.Item;

@Entity
@Getter
@Setter
public class Category {

  @Id @GeneratedValue
  @Column(name = "category_id")
  private Long id;

  private String name;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "category_item", //다대다관계에서 사용하는 DB테이블 명을 category_item으로 지정
      joinColumns = @JoinColumn(name = "category_id"), // 현재 엔티티 category_item테이블에 포링키로 들어갈 컬럼명
      inverseJoinColumns = @JoinColumn(name = "item_id") // 대상 엔티티 category_item테이블에 포링키로 들어갈 컬럼명
      )
  private List<Item> items = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  private Category parent;

  @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
  private List<Category> child = new ArrayList<>();

  // 연관관계 메서드 //
  public void addChildCategory(Category child) {
    this.child.add(child);
    child.setParent(this);
  }

}
