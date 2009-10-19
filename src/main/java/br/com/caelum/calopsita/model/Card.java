package br.com.caelum.calopsita.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.picocontainer.annotations.Inject;

import br.com.caelum.calopsita.repository.CardRepository;

import com.google.common.collect.Iterators;

@Entity
public class Card implements Identifiable, FromProject {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@Column(length=1024)
	private String description;

	@ManyToOne
	private User creator;

	@ManyToOne
	private Project project;

	@ManyToOne
	private Iteration iteration;

	@ManyToOne
	private Card parent;

	@OneToMany(mappedBy = "parent")
	private List<Card> subcards;

	@Enumerated(EnumType.STRING)
	private Status status = Status.TODO;

	public static enum Status {
		TODO, DONE
	}

	@Transient
	private CardRepository repository;

	@Inject
	public void setRepository(CardRepository repository) {
		this.repository = repository;
	}

	private CardRepository getRepository() {
		if (repository == null) {
			throw new IllegalStateException("Repository was not set. You should inject it first");
		}
		return repository;
	}

	public Card refresh() {
		return getRepository().refresh(this);
	}

	@Transient
	private List<Gadget> gadgets;

	public List<Gadget> getGadgets() {
		if (gadgets == null) {
			gadgets = getRepository().listGadgets(this);
		}
		return gadgets;
	}

	public <T extends Gadget> T getGadget(Class<T> type) {
		Iterator<T> iterator = Iterators.filter(getGadgets().iterator(),type);
		return iterator.hasNext() ? iterator.next() : null;
	}

	public void update() {
		getRepository().update(this);
	}
	public Card load() {
		Card loaded = getRepository().load(this);
		loaded.setRepository(getRepository());
		return loaded;
	}

	public void updateGadgets(List<Gadgets> gadgets) {
		getRepository().updateGadgets(this, gadgets);
	}

	public List<Long> deleteSubCards() {
		List<Long> ids = new ArrayList<Long>();
		for (Card sub : getSubcards()) {
			ids.add(sub.getId());
            getRepository().remove(sub);
        }
		return ids;
	}

	public void detachSubCards() {
		for (Card sub : getSubcards()) {
            sub.setParent(null);
            repository.update(sub);
        }
	}

	public void delete() {
		repository.remove(this);
	}

	public void addGadgets(List<Gadgets> gadgets) {
		for (Gadgets gadget : gadgets) {
			repository.add(gadget.createGadgetFor(this));
		}
	}
	public List<Card> getSubcards() {
		if (subcards == null) {
			return getRepository().listSubcards(this);
		}
		return subcards;
	}

	public void save() {
		getRepository().add(this);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Inject
	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public void setIteration(Iteration iteration) {
		this.iteration = iteration;
	}

	public Iteration getIteration() {
		return iteration;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}

	public void setParent(Card parent) {
		this.parent = parent;
	}

	@Inject
	public Card getParent() {
		return parent;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public User getCreator() {
		return creator;
	}

}
