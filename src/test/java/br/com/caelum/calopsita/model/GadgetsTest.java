package br.com.caelum.calopsita.model;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import br.com.caelum.calopsita.plugins.prioritization.PrioritizableCard;

public class GadgetsTest {

	@Test
	public void testingValueOf() throws Exception {
		List<Gadgets> gadgets = Gadgets.valueOf(Arrays.asList(new PrioritizableCard()));

		assertThat(gadgets.size(), is(1));

		assertThat(gadgets, hasItem(Gadgets.PRIORITIZATION));
	}
}
