package br.com.caelum.calopsita.mocks;

import java.util.Collection;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;

import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.View;
import br.com.caelum.vraptor.validator.Message;
import br.com.caelum.vraptor.validator.ValidationError;
import br.com.caelum.vraptor.validator.Validations;

public class MockValidator implements Validator {

	private final Mockery mockery;

	public MockValidator() {
		mockery =  new Mockery() {
			{
				setImposteriser(ClassImposteriser.INSTANCE);
			}
		};

		mockery.checking(new Expectations() {
			{
				ignoring(anything());
			}
		});
	}
	public void checking(Validations rules) {
		if (!rules.getErrors().isEmpty()) {
			throw new ValidationError(rules.getErrors());
		}
	}

	public void add(Collection<? extends Message> message) {
	}
	public boolean hasErrors() {
		return false;
	}
	public <T extends View> T onErrorUse(Class<T> view) {
		return new MockResult().use(view);
	}


}
