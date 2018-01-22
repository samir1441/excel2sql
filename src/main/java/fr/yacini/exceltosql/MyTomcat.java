package fr.yacini.exceltosql;

import java.io.File;

import javax.servlet.ServletException;

import org.apache.catalina.Context;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.ContextConfig;
import org.apache.catalina.startup.Tomcat;
import org.apache.log4j.Logger;

public class MyTomcat extends Tomcat {

	private static Logger logger = Logger.getLogger(MyTomcat.class);

	public MyTomcat() {
		super();
	}

	@Override
	public Context addWebapp(final String contextPath, final String docBase) throws ServletException {

		Context context = null;
		try {
			context = new StandardContext();
			context.setName(contextPath);
			context.setPath(contextPath);
			context.setDocBase(docBase);
			context.setRealm(this.getHost().getRealm());
			final ContextConfig contextConfig = new ContextConfig();
			context.addLifecycleListener(contextConfig);
			if (new File(docBase + "/WEB-INF/web.xml").exists()) {
				contextConfig.setDefaultWebXml(docBase + "/WEB-INF/web.xml");
			} else {
				contextConfig.setDefaultWebXml("org/apache/catalina/startup/NO_DEFAULT_XML");
			}
			this.host.addChild(context);
		} catch (final Exception e) {
			MyTomcat.logger.error("Errore deploying webapp", e);
		}
		return context;
	}

}
