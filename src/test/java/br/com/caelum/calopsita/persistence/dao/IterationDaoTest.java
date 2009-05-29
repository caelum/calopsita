package br.com.caelum.calopsita.persistence.dao;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.classic.Session;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.Project;

public class IterationDaoTest {

    private Session session;
    private IterationDao dao;
    private Transaction transaction;

    @Before
    public void setUp() throws Exception {
        session = new AnnotationConfiguration().configure().buildSessionFactory().openSession();

        dao = new IterationDao(session);
        transaction = session.beginTransaction();
    }
    
    @After
    public void tearDown() throws Exception {
        if (transaction != null) {
            transaction.rollback();
        }
    }
    
    @Test
    public void gettingCurrentIterationWithNoDates() throws Exception {
        Iteration iteration = givenAnIteration();
        Iteration current = dao.getCurrentIterationFromProject(iteration.getProject());

        assertThat(current, not(is(iteration)));
    }
    
    @Test
    public void gettingCurrentIterationAlreadyStartedButNotFinished() throws Exception {
        Iteration iteration = givenAnIteration(withStartDate(yesterday()), withEndDate(tomorrow()));
        Iteration current = dao.getCurrentIterationFromProject(iteration.getProject());

        assertThat(current, is(iteration));
    }
    
    @Test
    public void gettingCurrentIterationAlreadyStartedAndFinished() throws Exception {
        Iteration iteration = givenAnIteration(withStartDate(yesterday()), withEndDate(yesterday()));
        Iteration current = dao.getCurrentIterationFromProject(iteration.getProject());

        assertThat(current, not(is(iteration)));
    }
    
    @Test
    public void gettingCurrentIterationAlreadyStartedWithNoEndDate() throws Exception {
        Iteration iteration = givenAnIteration(withStartDate(yesterday()));
        Iteration current = dao.getCurrentIterationFromProject(iteration.getProject());

        assertThat(current, is(iteration));
    }
    
    @Test
    public void gettingCurrentIterationNotStartedYet() throws Exception {
        Iteration iteration = givenAnIteration(withStartDate(tomorrow()));
        Iteration current = dao.getCurrentIterationFromProject(iteration.getProject());

        assertThat(current, not(is(iteration)));
    }

    private Iteration givenAnIteration(LocalDate startDate) {
        Iteration iteration = new Iteration();
        iteration.setGoal("An iteration");
        iteration.setProject(givenAProject());
        iteration.setStartDate(startDate);
        session.save(iteration);
        session.flush();
        return iteration;
    }

    private Iteration givenAnIteration(LocalDate startDate, LocalDate endDate) {
        Iteration iteration = new Iteration();
        iteration.setGoal("An iteration");
        iteration.setProject(givenAProject());
        iteration.setStartDate(startDate);
        iteration.setEndDate(endDate);
        session.save(iteration);
        session.flush();
        return iteration;
    }

    private LocalDate withEndDate(LocalDate date) {
        return date;
    }

    private LocalDate tomorrow() {
        return new LocalDate().plusDays(1);
    }

    private LocalDate withStartDate(LocalDate date) {
        return date;
    }

    private LocalDate yesterday() {
        return new LocalDate().minusDays(1);
    }

    private Project givenAProject() {
        Project project = new Project();
        project.setName("A project");
        session.save(project);
        session.flush();
        return project;
    }

    private Iteration givenAnIteration() {
        Iteration iteration = new Iteration();
        iteration.setGoal("An iteration");
        iteration.setProject(givenAProject());
        session.save(iteration);
        session.flush();
        return iteration;
    }
}
