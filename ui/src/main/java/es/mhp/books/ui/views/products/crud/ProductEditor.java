package es.mhp.books.ui.views.products.crud;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import es.mhp.books.ui.views.products.ProductPresenter;
import es.mhp.dtos.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import es.mhp.books.ui.views.Presenter.AbstractPresenter;
import es.mhp.books.ui.views.crud.AbstractEditorComponent;

/**
 * Created by afuentes on 27/12/15.
 */
@SpringComponent
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ProductEditor extends AbstractEditorComponent<ProductDto> {
    private TextField name;
    private TextField brand;

    @Override
    @Autowired
    @Qualifier(ProductPresenter.PRESENTER_NAME)
    public void setPresenter(AbstractPresenter presenter) {
        super.setPresenter(presenter);
    }

    @Override
    protected void configureFields() {
        fieldGroup = new BeanFieldGroup<>(ProductDto.class);

        brand.setNullRepresentation("");
        name.setNullRepresentation("");

        super.configureFields(name, brand);
    }

    @Override
    protected void createFieldsComponents(CssLayout editorLayout) {
        name = new TextField("Name");
        brand = new TextField("Brand");

        editorLayout.addComponent(createFieldContainer(name));
        editorLayout.addComponent(createFieldContainer(brand));
    }

}