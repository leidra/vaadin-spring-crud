package es.mhp.books.crud;

import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitEvent;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.FieldGroup.CommitHandler;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Field;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import es.mhp.dtos.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

/**
 * A form for editing a single product.
 * <p>
 * Using responsive layouts, the form can be displayed either sliding out on the
 * side of the view or filling the whole screen - see the theme for the related
 * CSS rules.
 */
@SpringComponent
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ProductForm extends ProductFormDesign {
    @Autowired
    private SampleCrudLogic viewLogic;

    private BeanFieldGroup<ProductDto> fieldGroup;

    public ProductForm(SampleCrudLogic sampleCrudLogic) {
        super();
        addStyleName("product-form");
        viewLogic = sampleCrudLogic;

        fieldGroup = new BeanFieldGroup<>(ProductDto.class);
        fieldGroup.bindMemberFields(this);

        // perform validation and enable/disable buttons while editing
        ValueChangeListener valueListener = event -> formHasChanged();
        for (Field f : fieldGroup.getFields()) {
            f.addValueChangeListener(valueListener);
        }

        fieldGroup.addCommitHandler(new CommitHandler() {

            @Override
            public void preCommit(CommitEvent commitEvent) throws CommitException {
            }

            @Override
            public void postCommit(CommitEvent commitEvent) throws CommitException {
                viewLogic.getService().save(fieldGroup.getItemDataSource().getBean());
            }
        });

        save.addClickListener(event -> {
                try {
                    fieldGroup.commit();

                    // only if validation succeeds
                    ProductDto product = fieldGroup.getItemDataSource().getBean();
                    viewLogic.saveProduct(product);
                } catch (CommitException e) {
                    Notification n = new Notification("Please re-check the fields", Type.ERROR_MESSAGE);
                    n.setDelayMsec(500);
                    n.show(getUI().getPage());
                }
        });

        cancel.addClickListener((ClickListener) event -> viewLogic.cancelProduct());

        delete.addClickListener((ClickListener) event -> {
            ProductDto product = fieldGroup.getItemDataSource().getBean();
            viewLogic.deleteProduct(product);
        });
    }

    public void editProduct(ProductDto product) {
        if (product == null) {
            product = new ProductDto();
        }
        fieldGroup.setItemDataSource(new BeanItem<>(product));

        // before the user makes any changes, disable validation error indicator
        // of the product name field (which may be empty)
        name.setValidationVisible(false);

        // Scroll to the top
        // As this is not a Panel, using JavaScript
        String scrollScript = "window.document.getElementById('" + getId() + "').scrollTop = 0;";
        Page.getCurrent().getJavaScript().execute(scrollScript);
    }

    private void formHasChanged() {
        // show validation errors after the user has changed something
        name.setValidationVisible(true);

        // only products that have been saved should be removable
        boolean canRemoveProduct = false;
        BeanItem<ProductDto> item = fieldGroup.getItemDataSource();
        if (item != null) {
            ProductDto product = item.getBean();
            canRemoveProduct = product.getId() != null && product.getId() != -1;
        }
        delete.setEnabled(canRemoveProduct);
    }
}
