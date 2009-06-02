package br.com.caelum.calopsita.model;

import java.util.ArrayList;
import java.util.List;

public enum Gadgets {
	PRIORITIZATION {
		@Override
		public Gadget createGadgetFor(Card card) {
			return new PrioritizableCard(card);
		}

		@Override
		public Class<? extends Gadget> gadgetClass() {
			return PrioritizableCard.class;
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
