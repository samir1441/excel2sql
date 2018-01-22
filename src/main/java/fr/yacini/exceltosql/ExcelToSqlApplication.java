package fr.yacini.exceltosql;

import java.io.File;

import javax.servlet.ServletException;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

/**
 * @author Samir
 *
 */
public class ExcelToSqlApplication {

	/**
	 * @param args
	 * @throws LifecycleException
	 * @throws InterruptedException
	 * @throws ServletException
	 */
	public static void main(final String[] args) throws LifecycleException, InterruptedException, ServletException {

		final String docBase = "src/main/webapp/";

		final MyTomcat tomcat = new MyTomcat();
		tomcat.setPort(8082);

		final StandardContext context = (StandardContext) tomcat.addWebapp("/", new File(docBase).getAbsolutePath());

		// declare an alternate location for "WEB-INF/classes" dir
		final File additionWebInfClasses = new File("target/classes");
		final WebResourceRoot resources = new StandardRoot(context);
		resources.addPreResources(
				new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClasses.getAbsolutePath(), "/"));
		context.setResources(resources);

		tomcat.start();
		tomcat.getServer().await();
	}
}
