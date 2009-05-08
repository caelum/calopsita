package br.com.caelum.calopsita.repository;

import java.util.List;

import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.Story;

public interface StoryRepository extends BaseRepository<Story>{

	Story load(Story story);

	void update(Story story);

	List<Story> storiesWithoutIteration(Project project);

	List<Story> listSubstories(Story story);

}
