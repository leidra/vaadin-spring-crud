package es.mhp.books.crud;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.MethodProperty;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Grid;
import es.mhp.dtos.ProductDto;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

import java.util.Collection;

/**
 * Grid of products, handling the visual presentation and filtering of a set of
 * items. This version uses an in-memory data source that is suitable for small
 * data sets.
 */
@SpringComponent
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ProductGrid extends Grid {

    public ProductGrid() {
        setSizeFull();

        setSelectionMode(SelectionMode.SINGLE);

        BeanItemContainer<ProductDto> container = new BeanItemContainer<>(ProductDto.class);
        setContainerDataSource(container);
        setColumnOrder("id", "name", "price");
    }

    /**
     * Filter the grid based on a search string that is searched for in the
     * product name, availability and category columns.
     *
     * @param filterString string to look for
     */
    public void setFilter(String filterString) {
        getContainer().removeAllContainerFilters();
        if (filterString.length() > 0) {
            SimpleStringFilter nameFilter = new SimpleStringFilter("name", filterString, true, false);
            getContainer().addContainerFilter(new Or(nameFilter));
        }
    }

    private BeanItemContainer<ProductDto> getContainer() {
        return (BeanItemContainer<ProductDto>) super.getContainerDataSource();
    }

    @Override
    public ProductDto getSelectedRow() throws IllegalStateException {
        return (ProductDto) super.getSelectedRow();
    }

    public void setProducts(Collection<ProductDto> products) {
        getContainer().removeAllItems();
        getContainer().addAll(products);
    }

    public void refresh(ProductDto product) {
        // We avoid updating the whole table through the backend here so we can
        // get a partial update for the grid
        BeanItem<ProductDto> item = getContainer().getItem(product);
        if (item != null) {
            // Updated product
            MethodProperty p = (MethodProperty) item.getItemProperty("id");
            p.fireValueChange();
        } else {
            // New product
            getContainer().addBean(product);
        }
    }

    public void remove(ProductDto product) {
        getContainer().removeItem(product);
    }
}
