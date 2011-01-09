package br.com.caelum.calopsita.plugins.kanban;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.Column;
import br.com.caelum.calopsita.model.Gadget;

@Entity
public class KanbanCard implements Gadget {

	@GeneratedValue
	@Id
	private Long id;
	
	@OneToOne
	private Card card;
	
	@ManyToOne
	private Column column;

	public static KanbanCard of(Card card) {
		KanbanCard kanbanCard = new KanbanCard();
		kanbanCard.setCard(card);
		return kanbanCard;
	}	
	
	private void setCard(Card card) {
		this.card = card;
	}

	@Override
	public Card getCard() {
		return this.card;
	}

	@Override
	public String getHtml() {
		return "";
	}

	public void setColumn(Column column) {
		this.column = column;
	}

	public Column getColumn() {
		return column;
	}

}
