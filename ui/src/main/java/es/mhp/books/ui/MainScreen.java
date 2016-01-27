package es.mhp.books.ui;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import es.mhp.books.ui.components.Menu;
import es.mhp.books.ui.views.products.ProductView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by afuentes on 27/01/16.
 */
@SpringComponent
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class MainScreen extends CssLayout {
    @Autowired
    private Menu menu;

    private MainUI ui;
    private CssLayout contentContainer;

    public MainScreen(MainUI ui) {
        this.ui = ui;
    }

    @PostConstruct
    public void postConstruct() {
        setSizeFull();
        this.addComponent(createScreen());
    }

    private CssLayout createScreen() {
        CssLayout uiLayout = new CssLayout();
        uiLayout.addStyleName("ui-container");
        uiLayout.addComponent(this.createHeader());
        uiLayout.addComponent(this.createViewContainer());
        uiLayout.addComponent(this.createFooter());

        return uiLayout;
    }

    private Component createViewContainer() {
        CssLayout viewContainer = new CssLayout();
        viewContainer.addStyleName("ui-view");
        viewContainer.addComponent(this.createSideBar());
        viewContainer.addComponent(this.createContentContainer());

        return viewContainer;
    }

    private Component createHeader() {
        CssLayout headerContainer = new CssLayout();
        headerContainer.addStyleName("ui-header");
        return headerContainer;
    }

    private Component createSideBar() {
        if (menu.getDatasource().isEmpty()) {
            Map<String, Runnable> datasource = new HashMap<>();
            datasource.put("Products", () -> getUi().getNavigator().navigateTo(ProductView.VIEW_NAME));

            menu.setDatasource(datasource);
        }

        return menu;
    }

    private Component createContentContainer() {
        contentContainer = new CssLayout();
        contentContainer.addStyleName("content-container");
        return contentContainer;
    }

    private Component createFooter() {
        CssLayout footerContainer = new CssLayout();
        footerContainer.addStyleName("footer-container");
        return footerContainer;
    }

    public MainUI getUi() {
        return ui;
    }

    public CssLayout getContentContainer() {
        return contentContainer;
    }
}
