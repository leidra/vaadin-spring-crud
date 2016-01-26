package es.mhp.examples;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

@Theme("mytheme")
@SpringUI
public class SpringVaadinCRUDUI extends UI {
    @Autowired
    private ProductService service;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);

        Button button = new Button("Click Me");
        button.addClickListener(event -> layout.addComponent(new Label("Thank you for clicking")));
        layout.addComponent(button);
    }

    @WebServlet(urlPatterns = "/*", name = "SpringVaadinCRUDUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = SpringVaadinCRUDUI.class, productionMode = false)
    public static class SpringVaadinCRUDUIServlet extends VaadinServlet {
    }

    @WebListener
    public class MyListener extends ContextLoaderListener {
        public MyListener() {
        }
    }
}
