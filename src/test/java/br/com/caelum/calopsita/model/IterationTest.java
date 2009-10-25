package br.com.caelum.calopsita.model;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;

public class IterationTest {
    @Test
    public void testIterationStartingYesterdayIsCurrent() throws Exception {
        Iteration i = new Iteration();
        i.setStartDate(new LocalDate().minusDays(1));

        Assert.assertTrue(i.isCurrent());
    }

    @Test
    public void testIterationStartingTodayIsCurrent() throws Exception {
        Iteration i = new Iteration();
        i.setStartDate(new LocalDate());

        Assert.assertTrue(i.isCurrent());
    }

    @Test
    public void testIterationStartingTomorrowIsNotCurrent() throws Exception {
        Iteration i = new Iteration();
        i.setStartDate(new LocalDate().plusDays(1));

        Assert.assertFalse(i.isCurrent());
    }

    @Test
    public void testIterationStartingYesterdayAndFinishingToday() throws Exception {
        Iteration i = new Iteration();
        i.setStartDate(new LocalDate().minusDays(1));
        i.setEndDate(new LocalDate());

        Assert.assertTrue(i.isCurrent());
    }

    @Test
    public void testIterationStartingYesterdayAndFinishingTomorrow() throws Exception {
        Iteration i = new Iteration();
        i.setStartDate(new LocalDate().minusDays(1));
        i.setEndDate(new LocalDate().plusDays(1));

        Assert.assertTrue(i.isCurrent());
    }

    @Test
    public void testIterationStartingTodayAndFinishingTomorrow() throws Exception {
        Iteration i = new Iteration();
        i.setStartDate(new LocalDate());
        i.setEndDate(new LocalDate().plusDays(1));

        Assert.assertTrue(i.isCurrent());
    }

    @Test
    public void testIterationStartingTodayAndFinishingToday() throws Exception {
        Iteration i = new Iteration();
        i.setStartDate(new LocalDate());
        i.setEndDate(new LocalDate());

        Assert.assertTrue(i.isCurrent());
    }
}
