package br.com.caelum.calopsita.persistence.dao;

import java.util.List;

import org.hibernate.Session;

import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.repository.StoryRepository;

public class StoryDao implements StoryRepository {

	private final Session session;
	
	public StoryDao(Session session) {
		this.session = session;
	}
	
	@Override
	public void add(Card story) {
		session.save(story);
	}
	@Override
	public Card load(Card story) {
		return (Card) session.load(Card.class, story.getId());
	}
	@Override
	public void update(Card story) {
		session.update(story);
	}

	@Override
	public void remove(Card story) {
		session.delete(story);
	}
	
	@Override
	public List<Card> storiesWithoutIteration(Project project) {
		return session.createQuery("from Story s where s.project = :project and " +
				" s.iteration is null order by priority")
				.setParameter("project", project).list();
	}
	
	@Override
	public List<Card> listSubstories(Card story) {
		return session.createQuery("from Story s where s.parent = :story order by priority")
			.setParameter("story", story).list();
	}

}
