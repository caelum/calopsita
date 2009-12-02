package br.com.caelum.calopsita.model;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.calopsita.plugins.planning.PlanningCard;
import br.com.caelum.calopsita.plugins.prioritization.PrioritizableCard;
import br.com.caelum.calopsita.plugins.roi.ValuableCard;

public enum Gadgets {
	PRIORITIZATION {
		@Override
		public PrioritizableCard createGadgetFor(Card card) {
			return PrioritizableCard.of(card);
		}

		@Override
		public Class<? extends Gadget> gadgetClass() {
			return PrioritizableCard.class;
		}
	}, PLANNING {
		@Override
		public PlanningCard createGadgetFor(Card card) {
			return PlanningCard.of(card);
		}

		@Override
		public Class<? extends Gadget> gadgetClass() {
			return PlanningCard.class;
		}
	}, VALUABLE {

		@Override
		public Gadget createGadgetFor(Card card) {
			return ValuableCard.of(card);
		}

		@Override
		public Class<? extends Gadget> gadgetClass() {
			return ValuableCard.class;
		}
		
	};

	public abstract Gadget createGadgetFor(Card card);
	public abstract Class<? extends Gadget> gadgetClass();

	public static List<Gadgets> valueOf(List<? extends Gadget> gadgets) {
		List<Gadgets> result = new ArrayList<Gadgets>();
		for (Gadget gadget : gadgets) {
			for (Gadgets g : values()) {
				if (g.gadgetClass().isInstance(gadget)) {
					result.add(g);
				}
			}
		}
		return result;
	}
}
