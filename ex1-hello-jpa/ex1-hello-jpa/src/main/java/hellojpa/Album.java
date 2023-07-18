package hellojpa;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A") // DTYPE 축약
public class Album extends Item { // 고급매핑

    private String artist;

}
