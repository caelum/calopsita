package br.com.caelum.calopsita.persistence.dao;

import java.util.List;

import org.hibernate.Session;

import br.com.caelum.calopsita.model.Story;
import br.com.caelum.calopsita.repository.StoryRepository;

public class StoryDao implements StoryRepository {

	private final Session session;
	
	public StoryDao(Session session) {
		this.session = session;
	}
	
	@Override
	public void add(Story story) {
		session.save(story);
	}
	@Override
	public Story load(Story story) {
		return (Story) session.load(Story.class, story.getId());
	}
	@Override
	public void update(Story story) {
		session.update(story);
	}

	@Override
	public void remove(Story story) {
		session.delete(story);
	}
	
	@Override
	public List<Story> storiesWithoutIteration() {
		return session.createQuery("from Story s where s.iteration is null").list();
	}

}
