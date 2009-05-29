package br.com.caelum.calopsita.logic;

import java.util.List;

import org.vraptor.annotations.Component;
import org.vraptor.annotations.InterceptedBy;

import br.com.caelum.calopsita.infra.interceptor.AuthenticationInterceptor;
import br.com.caelum.calopsita.infra.interceptor.AuthorizationInterceptor;
import br.com.caelum.calopsita.infra.interceptor.HibernateInterceptor;
import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.PrioritizableCard;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.repository.PrioritizationRepository;
import br.com.caelum.calopsita.repository.ProjectRepository;

@Component
@InterceptedBy( { HibernateInterceptor.class, AuthenticationInterceptor.class, AuthorizationInterceptor.class })
public class PrioritizationLogic {

	private Project project;
	private final PrioritizationRepository repository;
	private final ProjectRepository projectRepository;
	private List<List<Card>> cards;

	public PrioritizationLogic(PrioritizationRepository repository, ProjectRepository projectRepository) {
		this.repository = repository;
		this.projectRepository = projectRepository;
	}
	public void prioritization(Project project) {
		this.project = this.projectRepository.get(project.getId());
		this.cards = repository.listCards(project);
	}

	public void prioritize(Project project, List<PrioritizableCard> cards) {
		for (PrioritizableCard card : cards) {
			PrioritizableCard loaded = repository.load(card);
			loaded.setPriority(card.getPriority());
		}
		prioritization(project);
	}

	public List<List<Card>> getCards() {
		return cards;
	}

	public Project getProject() {
		return project;
	}
}
