package hellojpa;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B") // DTYPE 축약
public class Book extends Item { // 고급 매핑

    private String author;
    private String isbn;

}
