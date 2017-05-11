package hr.tosulc.greendaotestapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.joda.time.DateTime;

import java.util.List;

import hr.tosulc.greendaotestapplication.daoclasses.DaoSession;
import hr.tosulc.greendaotestapplication.daoclasses.NoteDao;
import hr.tosulc.greendaotestapplication.daoclasses.PersonDao;

import static hr.tosulc.greendaotestapplication.daoclasses.NoteDao.Properties;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DaoSession session = AppController.getDaoSession();
        NoteDao dao = session.getNoteDao();

        /*
        For reference: When using Long with @Id(autoincrement = true) greenDAO creates a new
        key for you when inserting. When using long your are responsible for setting the correct
        key before inserting. Otherwise it will remain at the Java default value 0.
        So, hasKey() does not make sense (it will throw an exception) when using long as the
        key can never be null/not exist.
         */

        final EditText editText = (EditText) findViewById(R.id.et_text);
        final EditText editTextRemove = (EditText) findViewById(R.id.et_remove_note);
        final EditText editTextPerson = (EditText) findViewById(R.id.et_add_person);


        Button btn_add = (Button) findViewById(R.id.btn_add_note);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteDao dao = AppController.getDaoSession().getNoteDao();
                DateTime dt = DateTime.now();
                dt = dt.withYear(1990);
                Note n = new Note(editText.getText().toString(), dt.toDate());

                String person = editTextPerson.getText().toString();
                Person p = new Person(person.length() > 0 ? person : "Cool person", dt.toDate());
                n.setPerson(p);
                editText.setText("");
                editTextPerson.setText("");
                dao.insert(n);

                PersonDao personDao = AppController.getDaoSession().getPersonDao();
                personDao.insert(p);
            }
        });

        Button btn_remove = (Button) findViewById(R.id.btn_remove_note);
        btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteDao dao = AppController.getDaoSession().getNoteDao();
                dao.loadAll();
                List<Note> list = dao.loadAll();
                String numberStr = editTextRemove.getText().toString();
                if (numberStr.length() > 0) {
                    int number = Integer.parseInt(numberStr);
                    dao.delete(list.get(number));
                }

            }
        });

        Button btn = (Button) findViewById(R.id.btn_all_notes);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteDao dao = AppController.getDaoSession().getNoteDao();
                List<Note> list = dao.loadAll();
                StringBuilder stringb = new StringBuilder();
                for (Note n : list) {
                    stringb.append(n.getText()).append(" no.").append(n.getDate()).append("\n ");
                }
                Toast.makeText(getApplicationContext(), stringb.toString(), Toast.LENGTH_SHORT)
                     .show();
            }
        });

        Button btn2 = (Button) findViewById(R.id.btn_change_first_note);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteDao dao = AppController.getDaoSession().getNoteDao();
                dao.loadAll();
                List<Note> list = dao.loadAll();
                Note n = list.get(0);
                n.setText("First entry editted!");
                dao.save(n);
            }
        });

        Button btnQuery = (Button) findViewById(R.id.btn_querybuilder);
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteDao dao = AppController.getDaoSession().getNoteDao();
                List<Note> notes = dao.queryBuilder().where(Properties.Text.like("%he%")).list();
                StringBuilder stringb = new StringBuilder();
                for (Note n : notes) {
                    stringb.append(n.getText()).append("\n");
                }
                Toast.makeText(getApplicationContext(), stringb.toString(), Toast.LENGTH_SHORT)
                     .show();
            }
        });
        btnQuery.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                NoteDao dao = AppController.getDaoSession().getNoteDao();
                QueryBuilder<Note> qb = dao.queryBuilder();
                qb.LOG_SQL = true;
                qb.LOG_VALUES = true;
                qb.where(qb.or(Properties.Text.like("%he%"), Properties.Text.like("%lo%")));
                Query<Note> query = qb.build(); //for reusing!
                List<Note> notes = qb.list();
                StringBuilder stringb = new StringBuilder();
                for (Note n : notes) {
                    stringb.append(n.getText()).append("\n");
                }
                Toast.makeText(getApplicationContext(), stringb.toString(), Toast.LENGTH_SHORT)
                     .show();

                //reusing query builder
                query.setParameter(0, "%first%");
                query.setParameter(1, "%zakon%");
                List<Note> notes2 = query.list();
                StringBuilder stringb2 = new StringBuilder();
                for (Note n : notes2) {
                    stringb2.append(n.getText()).append("\n");
                }
                Toast.makeText(getApplicationContext(), stringb2.toString(), Toast.LENGTH_SHORT)
                     .show();
                return true;
            }
        });

        Button btnRelation = (Button) findViewById(R.id.btn_query_relation);
        btnRelation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteDao dao = AppController.getDaoSession().getNoteDao();
                QueryBuilder<Note> queryBuilder = dao.queryBuilder();
                queryBuilder.join(Person.class, PersonDao.Properties.Id)
                            .where(PersonDao.Properties.Name.like("%Cool%"));
                Query<Note> query = queryBuilder.build();
                List<Note> listaaa = queryBuilder.list();
                StringBuilder stringb2 = new StringBuilder();
                for (Note n : listaaa) {
                    stringb2.append(n.getText()).append("\n");
                }
                Toast.makeText(getApplicationContext(), stringb2.toString(), Toast.LENGTH_SHORT)
                     .show();

                query.setParameter(0, "%Bljak%");
                List<Note> listaaa2 = query.list();
                StringBuilder stringb3 = new StringBuilder();
                for (Note n : listaaa2) {
                    stringb3.append(n.getText()).append("\n");
                }
                Toast.makeText(getApplicationContext(), stringb3.toString(), Toast.LENGTH_SHORT)
                     .show();

            }
        });

    }
}
