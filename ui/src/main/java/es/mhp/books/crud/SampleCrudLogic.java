package es.mhp.books.crud;

import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringComponent;
import es.mhp.books.ProductService;
import es.mhp.books.SpringVaadinCRUDUI;
import es.mhp.dtos.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

/**
 * This class provides an interface for the logical operations between the CRUD
 * view, its parts like the product editor form and the data source, including
 * fetching and saving products.
 * <p>
 * Having this separate from the view makes it easier to test various parts of
 * the system separately, and to e.g. provide alternative views for the same
 * data.
 */
@SpringComponent
@Scope("session")
public class SampleCrudLogic {
    @Autowired
    private ProductService service;
    private SampleCrudView view;

    public SampleCrudLogic(SampleCrudView simpleCrudView) {
        view = simpleCrudView;
    }

    public void init() {
        editProduct(null);
        // Hide and disable if not admin
        if (!SpringVaadinCRUDUI.get().getAccessControl().isUserInRole("admin")) {
            view.setNewProductEnabled(false);
        }

        view.showProducts(service.findAll());
    }

    public void cancelProduct() {
        setFragmentParameter("");
        view.clearSelection();
        view.editProduct(null);
    }

    /**
     * Update the fragment without causing navigator to change view
     */
    private void setFragmentParameter(String productId) {
        String fragmentParameter;
        if (productId == null || productId.isEmpty()) {
            fragmentParameter = "";
        } else {
            fragmentParameter = productId;
        }

        Page page = SpringVaadinCRUDUI.get().getPage();
        page.setUriFragment("!" + SampleCrudView.VIEW_NAME + "/"
                + fragmentParameter, false);
    }

    public void enter(String productId) {
        if (productId != null && !productId.isEmpty()) {
            if (productId.equals("new")) {
                newProduct();
            } else {
                // Ensure this is selected even if coming directly here from
                // login
                try {
                    long pid = Long.parseLong(productId);
                    ProductDto product = findProduct(pid);
                    view.selectRow(product);
                } catch (NumberFormatException e) {
                }
            }
        }
    }

    private ProductDto findProduct(Long productId) {
        return service.findById(productId);
    }

    public void saveProduct(ProductDto product) {
        view.showSaveNotification(product.getName() + " (" + product.getId() + ") updated");
        view.clearSelection();
        view.editProduct(null);
        view.refreshProduct(product);
        setFragmentParameter("");
    }

    public void deleteProduct(ProductDto product) {
        service.delete(product.getId());
        view.showSaveNotification(product.getName() + " (" + product.getId() + ") removed");

        view.clearSelection();
        view.editProduct(null);
        view.removeProduct(product);
        setFragmentParameter("");
    }

    public void editProduct(ProductDto product) {
        if (product == null) {
            setFragmentParameter("");
        } else {
            setFragmentParameter(product.getId() + "");
        }
        view.editProduct(product);
    }

    public void newProduct() {
        view.clearSelection();
        setFragmentParameter("new");
        view.editProduct(new ProductDto());
    }

    public void rowSelected(ProductDto product) {
        if (SpringVaadinCRUDUI.get().getAccessControl().isUserInRole("admin")) {
            view.editProduct(product);
        }
    }

    public ProductService getService() {
        return service;
    }

}
