package es.mhp.books.ui.components;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import org.springframework.context.annotation.Scope;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by afuentes on 9/01/16.
 */
@SpringComponent
//@Scope(WebApplicationContext.SCOPE_APPLICATION)
@UIScope
public class Menu extends CssLayout {
    protected Map<String, Runnable> datasource = new HashMap<>();

    public Menu(Map<String, Runnable> datasource) {
        this();
        this.datasource = datasource;
    }

    public Menu() {
        this.addStyleName("ui-menu");
    }

    @PostConstruct
    protected void postConstruct() {
        buildComponent();
    }

    public Map<String, Runnable> getDatasource() {
        return datasource;
    }

    public void setDatasource(Map<String, Runnable> datasource) {
        this.datasource = datasource;
        buildComponent();
    }

    protected void buildComponent() {
        Assert.notNull(datasource);
        datasource.entrySet().stream()
                .forEach(itemMenu -> this.addComponent(new Button(itemMenu.getKey(), e -> itemMenu.getValue().run())));
    }
}