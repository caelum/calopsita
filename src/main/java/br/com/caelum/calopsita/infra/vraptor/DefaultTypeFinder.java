package br.com.caelum.calopsita.infra.vraptor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.vidageek.mirror.dsl.Mirror;
import br.com.caelum.vraptor.http.ParameterNameProvider;
import br.com.caelum.vraptor.http.route.TypeFinder;
import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
/**
 * Discover parameter types
 * @author Lucas Cavalcanti
 *
 */
@ApplicationScoped
@Component
public class DefaultTypeFinder implements TypeFinder {

	private final ParameterNameProvider provider;
	public DefaultTypeFinder(ParameterNameProvider provider) {
		this.provider = provider;
	}
	public Map<String, Class<?>> getParameterTypes(Method method, String[] parameterPaths) {
		Map<String,Class<?>> result = new HashMap<String, Class<?>>();
		String[] parameterNamesFor = provider.parameterNamesFor(method);
		for (String path : parameterPaths) {
			for (int i = 0; i < parameterNamesFor.length; i++) {
				String name = parameterNamesFor[i];
				if (path.startsWith(name + ".") || path.equals(name)) {
					String[] items = path.split("\\.");
					Class<?> type = method.getParameterTypes()[i];
					for (int j = 1; j < items.length; j++) {
						String item = items[j];
						try {
							type = new Mirror().on(type).reflect().field(item).getType();
						} catch (Exception e) {
							throw new IllegalArgumentException("Parameters paths are invalid: " + Arrays.toString(parameterPaths) + " for method " + method, e);
						}
					}
					result.put(path, type);
				}
			}
		}
		return result;
	}

}
