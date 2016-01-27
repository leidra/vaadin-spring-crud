package es.mhp.books.ui;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.*;
import es.mhp.books.ui.authentication.AccessControl;
import es.mhp.books.ui.authentication.BasicAccessControl;
import es.mhp.books.ui.authentication.LoginScreen;
import es.mhp.books.ui.components.Menu;
import es.mhp.books.ui.views.AccessDeniedView;
import es.mhp.books.ui.views.ErrorView;
import es.mhp.books.ui.views.products.ProductView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by afuentes on 14/12/15.
 */
@SpringUI
@Theme(MainUI.THEME_NAME)
@PreserveOnRefresh
public class MainUI extends UI {
    public final static String THEME_NAME = "mytheme";

    private AccessControl accessControl = new BasicAccessControl();

    @Autowired
    private ApplicationContext context;

    @Autowired
    private SpringViewProvider viewProvider;

    @Override
    protected void init(VaadinRequest request) {
        // Let's register a custom error handler to make the 'access denied' messages a bit friendlier.
        setErrorHandler(createErrorHandler());
        if (!accessControl.isUserSignedIn()) {
            setContent(new LoginScreen(accessControl, () -> showMainView() ));
        } else {
            showMainView();
        }
    }

    private void showMainView() {
        MainScreen screen = context.getBean(MainScreen.class, this);
        this.setContent(screen);
        configureNavigator(screen);
        getNavigator().navigateTo(getNavigator().getState());
    }

    public void configureNavigator(MainScreen screen) {
        Navigator navigator = new Navigator(this, screen.getContentContainer());

        viewProvider.setAccessDeniedViewClass(AccessDeniedView.class);
        navigator.addProvider(viewProvider);
        navigator.setErrorView(ErrorView.class);
        this.setNavigator(navigator);
    }

    protected ErrorHandler createErrorHandler() {
        return new DefaultErrorHandler() {
            @Override
            public void error(com.vaadin.server.ErrorEvent event) {
                Notification.show("Sorry, you don't have access to do that.");
            }
        };
    }

    public SpringViewProvider getViewProvider() {
        return viewProvider;
    }

    @WebServlet(urlPatterns = "/*", name = "MainUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MainUI.class, productionMode = false)
    public static class MainUIServlet extends SpringVaadinServlet {
    }

    @WebListener
    public static class MyContextLoaderListener extends ContextLoaderListener {
    }

    @Configuration
    @EnableVaadin
    public static class MyConfiguration {
    }
}