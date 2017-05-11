package hr.tosulc.greendaotestapplication;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import org.greenrobot.greendao.DaoException;
import hr.tosulc.greendaotestapplication.daoclasses.DaoSession;
import hr.tosulc.greendaotestapplication.daoclasses.PersonDao;
import hr.tosulc.greendaotestapplication.daoclasses.NoteDao;

/**
 * Created by tomkan on 7.5.17..
 */
@Entity
public class Note {

    @Id
    private Long id;

    private String text;

    private Date date;

    @ToOne(joinProperty = "id")
    private Person person;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 363862535)
    private transient NoteDao myDao;

    @Generated(hash = 1154009267)
    private transient Long person__resolvedKey;

    @Generated(hash = 2071910743)
    public Note(Long id, String text, Date date) {
        this.id = id;
        this.text = text;
        this.date = date;
    }

    @Generated(hash = 1272611929)
    public Note() {
    }

    public Note(String da, Date date) {
        this.date = date;
        this.setText(da);
    }

    //private NoteType type; za ovo trebam converter iz examplea!

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 650969830)
    public Person getPerson() {
        Long __key = this.id;
        if (person__resolvedKey == null || !person__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PersonDao targetDao = daoSession.getPersonDao();
            Person personNew = targetDao.load(__key);
            synchronized (this) {
                person = personNew;
                person__resolvedKey = __key;
            }
        }
        return person;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 920221497)
    public void setPerson(Person person) {
        synchronized (this) {
            this.person = person;
            id = person == null ? null : person.getId();
            person__resolvedKey = id;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 799086675)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getNoteDao() : null;
    }

    /*
    public NoteType getType() {
        return type;
    }

    public void setType(NoteType type) {
        this.type = type;
    }*/
}
