package es.mhp.books.ui.views.products.crud;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Grid;
import es.mhp.dtos.ProductListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import es.mhp.books.ui.views.Presenter.AbstractPresenter;
import es.mhp.books.ui.views.crud.AbstractListComponent;
import es.mhp.books.ui.views.products.ProductPresenter;

import java.util.Optional;

/**
 * Created by afuentes on 27/12/15.
 */
@SpringComponent
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ProductsList extends AbstractListComponent<ProductListDto> {

    @Override
    protected void editAction(Grid.RowReference rowReference) {
        presenter.edit((ProductListDto) rowReference.getItemId());
    }

    @Override
    protected void removeAction(Grid.RowReference rowReference) {
        presenter.remove((ProductListDto) rowReference.getItemId());
        presenter.showList();
    }

    @Override
    protected void configureGrid() {
    }

    @Override
    protected void configureGridColumns() {
        Optional.ofNullable(grid.getColumn("id")).ifPresent(c -> grid.removeColumn("id"));
        grid.setColumnOrder("name", "brand");
    }

    @Override
    @Autowired
    @Qualifier(ProductPresenter.PRESENTER_NAME)
    public void setPresenter(AbstractPresenter presenter) {
        super.setPresenter(presenter);
    }

}
