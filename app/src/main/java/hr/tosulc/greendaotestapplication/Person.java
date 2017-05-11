package hr.tosulc.greendaotestapplication;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

import java.util.Date;

/**
 * Created by tomkan on 11.5.17..
 */

@Entity
public class Person {

    @Id
    private Long id;

    @NotNull
    private String name;
    private Date birth;

    @Generated(hash = 1913211006)
    public Person(Long id, @NotNull String name, Date birth) {
        this.id = id;
        this.name = name;
        this.birth = birth;
    }

    @Generated(hash = 1024547259)
    public Person() {
    }

    public Person(String name, Date birth) {
        this.name = name;
        this.birth = birth;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirth() {
        return this.birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
