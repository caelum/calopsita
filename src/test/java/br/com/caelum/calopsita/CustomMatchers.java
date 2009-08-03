package br.com.caelum.calopsita;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
			public void describeTo(Description description) {
				description.appendText("an empty collection");
			}

		};
	}

	public static <T> Matcher<List<? super T>> hasItemsInThisOrder(final T... items) {
		return new TypeSafeMatcher<List<? super T>>() {
			@Override
			public boolean matchesSafely(List<? super T> item) {
				int i = 0;
				for (Object object : item) {
					if (items[i].equals(object)) {
						i++;
					}
				}
				return i == items.length;
			}

			public void describeTo(Description description) {
				description.appendText("a list containing items in this order: ");
				description.appendText(Arrays.toString(items));
			}
		};
	}

}
