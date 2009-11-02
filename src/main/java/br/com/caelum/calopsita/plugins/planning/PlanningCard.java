package br.com.caelum.calopsita.plugins.planning;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.Gadget;

@Entity
public class PlanningCard implements Gadget {

	@Id
	@GeneratedValue(generator = "custom")
	@GenericGenerator(name = "custom", strategy = "foreign", parameters = @Parameter(name = "property", value = "card"))
	private Long id;

	@OneToOne
	@PrimaryKeyJoinColumn
	private Card card;

	public PlanningCard() {
	}

	public static PlanningCard of(Card card) {
		PlanningCard pcard = new PlanningCard();
		pcard.setCard(card);
		return pcard;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getHtml() {
		return "";
	}

}
