package br.com.caelum.calopsita.model;

import javax.persistence.Id;
import javax.persistence.OneToOne;

public class PrioritizableCard implements Gadget {

	@Id
	@OneToOne
	private Card card;

	private int priority;

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}


}
