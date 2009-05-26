package br.com.caelum.calopsita.integration.stories.common;

import org.hibernate.Session;

import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.Card;
import br.com.caelum.seleniumdsl.Browser;

public class StoryContexts<T extends ProjectContexts<T>> {

	private final Card story;
	private final Session session;
	private final T parent;
	private final Browser browser;

	public StoryContexts(Card story, Session session, Browser browser, T parent) {
		this.story = story;
		this.session = session;
		this.browser = browser;
		this.parent = parent;
	}

	public StoryContexts<T> withASubstoryNamed(String storyName) {
		Card story = new Card();
		story.setName(storyName);
		story.setProject(this.story.getProject());
		story.setParent(this.story);
		session.save(story);
		session.flush();
		return new StoryContexts<T>(story, session, browser, parent);
	}

	public StoryContexts<T> whichDescriptionIs(String storyDescription) {
		story.setDescription(storyDescription);
		session.flush();
		return this;
	}

	public StoryContexts<T> withPriority(int priority) {
		story.setPriority(priority);
		session.saveOrUpdate(story);
		session.flush();
		return this;
	}

	public void setIteration(Iteration iteration) {
		story.setIteration(iteration);
	}

	/**
	 * Go Up a level
	 * @return
	 */
	public T and() {
		return parent;
	}

	/**
	 * Go Up two levels
	 * @return
	 */
	public ProjectContexts<?> also() {
		return new ProjectContexts<T>(story.getProject(), session, browser);
	}

}
