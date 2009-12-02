package br.com.caelum.calopsita.plugins.roi;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.Gadget;

@Entity
public class ValuableCard implements Gadget{

	@Id @GeneratedValue
	private Long id;
	
	@OneToOne
	private Card card;
	
	private int roiValue;

	
	public static ValuableCard of(Card card) {
		ValuableCard valuableCard = new ValuableCard();
		valuableCard.setCard(card);
		return valuableCard;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getRoiValue() {
		return roiValue;
	}
	
	public void setRoiValue(int value) {
		this.roiValue = value;
	}

	@Override
	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}
	
	@Override
	public String getHtml() {
		return "<span class=\"roi\" title=\"Return of Investment\">ROI: " + roiValue + "</span>";
	}

}
