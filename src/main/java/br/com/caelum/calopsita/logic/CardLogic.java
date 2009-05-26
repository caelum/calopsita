package br.com.caelum.calopsita.logic;

import java.util.ArrayList;
import java.util.List;

import org.vraptor.annotations.Component;
import org.vraptor.annotations.InterceptedBy;

import br.com.caelum.calopsita.infra.interceptor.AuthenticationInterceptor;
import br.com.caelum.calopsita.infra.interceptor.AuthorizationInterceptor;
import br.com.caelum.calopsita.infra.interceptor.HibernateInterceptor;
import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.Gadgets;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.calopsita.repository.CardRepository;
import br.com.caelum.calopsita.repository.ProjectRepository;

@Component
@InterceptedBy( { HibernateInterceptor.class, AuthenticationInterceptor.class, AuthorizationInterceptor.class })
public class CardLogic {

	private final CardRepository repository;
	private Project project;
	private final User currentUser;
	private final ProjectRepository projectRepository;
	private List<Card> cards;
	private Card card;
	private List<Gadgets> gadgets;

	public CardLogic(User user, CardRepository repository, ProjectRepository projectRepository) {
		this.currentUser = user;
		this.repository = repository;
		this.projectRepository = projectRepository;
	}

	public void save(Card card, Project project, List<Gadgets> gadgets) {
		this.project = project;
		card.setProject(project);
		repository.add(card);
		for (Gadgets gadget : gadgets) {
			repository.add(gadget.createGadgetFor(card));
		}
		this.cards = this.projectRepository.listCardsFrom(project);
	}

	public void saveSub(Card card) {
		repository.add(card);
		this.cards = this.repository.listSubcards(card.getParent());
		this.card = card.getParent();
		this.project = card.getProject();
	}

	public void edit(Card card) {
		this.card = this.repository.load(card);
		this.gadgets = Gadgets.valueOf(this.repository.listGadgets(card));
		this.cards = this.repository.listSubcards(card);
	}

	public List<Gadgets> getGadgets() {
		return gadgets;
	}
	public Card getCard() {
		return card;
	}

	public void update(Card card, List<Gadgets> gadgets) {
		Card managedCard = repository.load(card);
		this.project = managedCard.getProject();
		managedCard.setName(card.getName());
		managedCard.setDescription(card.getDescription());
		repository.updateGadgets(card, gadgets);
		repository.update(managedCard);
		this.cards = this.projectRepository.listCardsFrom(project);
	}

	public List<Card> getCards() {
		return cards;
	}

	public Project getProject() {
		return project;
	}

	public String delete(Card card, boolean deleteSubcards) {
		Card loaded = repository.load(card);
		this.project = loaded.getProject();
		if (this.project.getColaborators().contains(currentUser) || this.project.getOwner().equals(currentUser)) {
		    this.project = loaded.getProject();
	        if (deleteSubcards) {
	            for (Card sub : loaded.getSubcards()) {
	                repository.remove(sub);
	            }
	        } else {
	            for (Card sub : loaded.getSubcards()) {
	                sub.setParent(null);
	                repository.update(sub);
	            }
	        }
	        repository.remove(loaded);
	        return "ok";
		} else {
			return "invalid";
		}

	}
}
