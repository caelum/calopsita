package br.com.caelum.calopsita.model;

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
}
