package es.mhp.books.ui.views.products;

import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import es.mhp.books.ui.views.AbstractView;
import es.mhp.books.ui.views.Presenter.Presenter;
import es.mhp.books.ui.views.products.crud.ProductEditor;
import es.mhp.books.ui.views.products.crud.ProductsList;

/**
 * Created by afuentes on 14/12/15.
 */
@SpringView(name = ProductView.VIEW_NAME)
public class ProductView extends AbstractView {
    public final static String VIEW_NAME = "Product";

    @Autowired
    public void setEditor(ProductEditor editor) {
        this.editor = editor;
    }

    @Autowired
    public void setList(ProductsList list) {
        this.list = list;
    }

    @Override
    @Autowired
    @Qualifier(ProductPresenter.PRESENTER_NAME)
    protected void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public Button getNewButton() {
        return newButton;
    }

    public Button getListButton() {
        return listButton;
    }

}