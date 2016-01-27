package es.mhp.books.ui.views.products;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import es.mhp.books.ProductService;
import es.mhp.books.Service;
import es.mhp.books.ui.MainUI;
import es.mhp.books.ui.views.AbstractView;
import es.mhp.books.ui.views.Presenter.AbstractPresenter;
import es.mhp.dtos.ProductDto;
import es.mhp.dtos.ProductListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Set;

/**
 * Created by afuentes on 27/12/15.
 */
@SpringComponent(ProductPresenter.PRESENTER_NAME)
@UIScope
public class ProductPresenter extends AbstractPresenter<ProductDto> {
    public final static String PRESENTER_NAME = "ProductPresenter";

    public ProductDto save(BeanFieldGroup fieldGroup) {
        ProductDto dto = (ProductDto) fieldGroup.getItemDataSource().getBean();
        try {
            getCurrentView().getEditor().setValidationVisible(!fieldGroup.isValid());
            fieldGroup.commit();
            dto = (ProductDto) fieldGroup.getItemDataSource().getBean();
            dto = getService().save(dto);
        } catch (FieldGroup.CommitException ex) {
            StringBuilder error = new StringBuilder("No se pudo guardar. Errores: ");
            Notification.show(error.toString(), Notification.Type.ERROR_MESSAGE);
        }

        return dto;
    }

    protected AbstractView getCurrentView() {
        return (AbstractView) ((MainUI) UI.getCurrent()).getViewProvider().getView(ProductView.VIEW_NAME);
    }

    public Set<ProductDto> findAll() {
        return getService().findAll();
    }

    public void showEditor() {
        edit(new ProductDto());
    }

    @Override
    public BeanItemContainer createDatasource() {
        return new BeanItemContainer<>(ProductListDto.class, ((ProductService) getService()).findAllForList());
    }

    @Override
    @Autowired
    @Qualifier(ProductService.SERVICE_NAME)
    public void setService(Service service) {
        this.service = service;
    }
}