package br.com.caelum.calopsita.plugins;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;

import br.com.caelum.vraptor.ioc.Component;

@Component
public class PluginResultTransformer implements ResultTransformer {

	private final Session session;
	private final List<Transformer> transformers;

	public PluginResultTransformer(Session session, List<Transformer> transformers) {
		this.session = session;
		this.transformers = transformers;
	}
	public List transformList(List list) {
		if (list.isEmpty()) {
			return list;
		}

		for (Transformer<?> transformer : transformers) {
			if (transformer.accepts(list.get(0).getClass())) {
				list = transformer.transform(list, session);
			}
		}
		return list;
	}

	public Object transformTuple(Object[] arg0, String[] arg1) {
		return arg0[0];
	}

}
