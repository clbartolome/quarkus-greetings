package es.clb.quarkus;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
public class Greeting extends PanacheEntityBase{

  @Id @GeneratedValue private Long id;
  private String value;

  public Greeting() {
  }

  public Greeting(Long id, String value) {
    this.id = id;
    this.value = value;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

}
