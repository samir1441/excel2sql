package fr.yacini.exceltosql.springframework.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * @author Samir
 *
 */
public class ExcelToSqlApplicationInitializer implements WebApplicationInitializer {

	/** {@inheritDoc} */
	@Override
	public void onStartup(final ServletContext servletContext) throws ServletException {
		// Create the 'root' Spring application context
		final AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(SpringConfiguration.class);

		// Manage the lifecycle of the root application context
		servletContext.addListener(new ContextLoaderListener(rootContext));
	}

}
