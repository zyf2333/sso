package indi.zyf.sso.core.webapp;

import org.jboss.logging.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


/**
 *
 */
@Component
public class InitListener  implements ApplicationListener<ContextRefreshedEvent> {
	Logger logger = Logger.getLogger(InitListener.class);
	

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		logger.debug("随项目启动");
	}

}
