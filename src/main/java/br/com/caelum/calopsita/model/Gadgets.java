package br.com.caelum.calopsita.model;

public enum Gadgets {
	PRIORITIZATION {
		@Override
		public Gadget createGadgetFor(Card card) {
			return new PrioritizableCard(card);
		}
	};

	public abstract Gadget createGadgetFor(Card card);
}
