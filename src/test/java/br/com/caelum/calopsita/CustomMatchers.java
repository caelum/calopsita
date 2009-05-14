package br.com.caelum.calopsita;

import java.util.Collection;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import br.com.caelum.calopsita.model.Identifiable;

public class CustomMatchers {

	@Factory
	public static <T extends Identifiable> Matcher<T> hasSameId(final T entity) {
		return new TypeSafeMatcher<T>() {

			@Override
			public boolean matchesSafely(T item) {
				return entity.getId().equals(item.getId());
			}
			@Override
			public void describeTo(Description description) {
				description.appendText("an entity with id ").appendValue(entity.getId());
			}
			
		};
	}
	@Factory
	public static <T extends Collection<?>> Matcher<T> isEmpty() {
		return new TypeSafeMatcher<T>() {
			
			@Override
			public boolean matchesSafely(T item) {
				return item.isEmpty();
			}
			@Override
			public void describeTo(Description description) {
				description.appendText("an empty collection");
			}
			
		};
	}
}
