package es.mhp.examples;

import com.vaadin.spring.server.SpringVaadinServlet;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created by afuentes on 26/01/16.
 */
public class WebContextInitializer /*implements WebApplicationInitializer*/ {
    public WebContextInitializer() {
    }

    public void onStartup(javax.servlet.ServletContext servletContext)
            throws ServletException {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        servletContext.addListener(new ContextLoaderListener((XmlWebApplicationContext)context));
        registerServlet(servletContext);
    }

    private void registerServlet(ServletContext servletContext) {
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
                "vaadin", SpringVaadinServlet.class);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/*");
    }
}
