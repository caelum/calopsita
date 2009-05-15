package br.com.caelum.calopsita.infra.vraptor;

import br.com.caelum.calopsita.persistence.dao.IterationDao;
import br.com.caelum.calopsita.persistence.dao.ProjectDao;
import br.com.caelum.calopsita.persistence.dao.StoryDao;
import br.com.caelum.calopsita.persistence.dao.UserDao;
import br.com.caelum.calopsita.repository.IterationRepository;
import br.com.caelum.calopsita.repository.ProjectRepository;
import br.com.caelum.calopsita.repository.StoryRepository;
import br.com.caelum.calopsita.repository.UserRepository;
import br.com.caelum.vraptor.ComponentRegistry;
import br.com.caelum.vraptor.ioc.pico.PicoProvider;

public class CustomProvider extends PicoProvider {

    @Override
    protected void registerComponents(ComponentRegistry container) {
        super.registerComponents(container);
        container.register(ProjectRepository.class, ProjectDao.class);
        container.register(UserRepository.class, UserDao.class);
        container.register(StoryRepository.class, StoryDao.class);
        container.register(IterationRepository.class, IterationDao.class);
    }
    
}
