package br.com.caelum.calopsita.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.CollectionOfElements;

import br.com.caelum.calopsita.infra.vraptor.Inject;
import br.com.caelum.calopsita.repository.CardTypeRepository;

@Entity
public class CardType implements FromProject {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@ManyToOne
	private Project project;

	@CollectionOfElements
	@Enumerated(EnumType.STRING)
	private List<Gadgets> gadgets;

	@Transient
	private CardTypeRepository repository;

	public CardType(CardTypeRepository repository) {
		this.repository = repository;
	}
	public CardType() {
	}

	@Inject
    public void setRepository(CardTypeRepository repository) {
		this.repository = repository;
	}

    private CardTypeRepository getRepository() {
    	if (repository == null) {
    		throw new IllegalStateException("Repository was not set. You should inject it first");
    	}
    	return repository;
    }

    public void save() {
    	getRepository().save(this);
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

	public List<Gadgets> getGadgets() {
		return gadgets;
	}

	public void setGadgets(List<Gadgets> gadgets) {
		this.gadgets = gadgets;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}
