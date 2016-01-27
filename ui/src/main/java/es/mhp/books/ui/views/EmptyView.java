package es.mhp.books.ui.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.CssLayout;

@SpringView(name = EmptyView.VIEW_NAME)
public class EmptyView extends CssLayout implements View {
    public final static String VIEW_NAME = "";

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
