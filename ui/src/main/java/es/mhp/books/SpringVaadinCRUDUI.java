package es.mhp.books;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.UI;
import es.mhp.books.authentication.AccessControl;
import es.mhp.books.authentication.BasicAccessControl;
import es.mhp.books.authentication.LoginScreen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

@Theme("valo")
@SpringUI
public class SpringVaadinCRUDUI extends UI {

    private AccessControl accessControl = new BasicAccessControl();

    @Autowired
    private ProductService service;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Responsive.makeResponsive(this);
        setLocale(vaadinRequest.getLocale());
        if (!accessControl.isUserSignedIn()) {
            setContent(new LoginScreen(accessControl, () -> showMainView() ));
        } else {
            showMainView();
        }
    }

    private void showMainView() {
        setContent(new MainScreen(SpringVaadinCRUDUI.this));
        getNavigator().navigateTo(getNavigator().getState());
    }

    public static SpringVaadinCRUDUI get() {
        return (SpringVaadinCRUDUI) UI.getCurrent();
    }

    public AccessControl getAccessControl() {
        return accessControl;
    }

    @WebServlet(urlPatterns = "/*", name = "SpringVaadinCRUDUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = SpringVaadinCRUDUI.class, productionMode = false)
    public static class SpringVaadinCRUDUIServlet extends SpringVaadinServlet {
    }

    @WebListener
    public static class MyContextLoaderListener extends ContextLoaderListener {
    }

    @Configuration
    @EnableVaadin
    public static class MyConfiguration {
    }
}
