package br.com.caelum.calopsita.repository;

import br.com.caelum.calopsita.model.Story;

public interface StoryRepository {

	void save(Story story);

	Story load(Story story);

	void update(Story story);

}
