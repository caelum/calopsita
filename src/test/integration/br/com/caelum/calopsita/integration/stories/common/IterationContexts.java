package br.com.caelum.calopsita.integration.stories.common;

import org.hibernate.Session;
import org.joda.time.LocalDate;

import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.seleniumdsl.Browser;

public class IterationContexts extends ProjectContexts<IterationContexts> {

	private final Session session;
	private final Iteration iteration;
	private final Browser browser;

	public IterationContexts(Iteration iteration, Session session, Browser browser) {
		super(iteration.getProject(), session, browser);
		this.iteration = iteration;
		this.session = session;
		this.browser = browser;
	}

	public IterationContexts startingYesterday() {
        this.iteration.setStartDate(new LocalDate().minusDays(1));
        session.saveOrUpdate(iteration);
        session.flush();
        return this;
    }

	public IterationContexts startingAt(LocalDate date) {
		iteration.setStartDate(date);
		session.flush();
		return this;
	}

	public IterationContexts endingAt(LocalDate date) {
		iteration.setEndDate(date);
		session.flush();
		return this;
	}

	@Override
	public ProjectContexts<?> and() {
		return new ProjectContexts<IterationContexts>(iteration.getProject(), session, browser);
	}

	@Override
	public StoryContexts<IterationContexts> withAStoryNamed(String storyName) {
		StoryContexts<IterationContexts> story = super.withAStoryNamed(storyName);
		story.setIteration(iteration);
		return story;
	}
}
