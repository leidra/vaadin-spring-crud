package es.mhp.books.ui.views.Presenter;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import es.mhp.books.Service;
import es.mhp.dtos.Dto;

import java.util.Set;

/**
 * Created by afuentes on 28/12/15.
 */
public interface Presenter<BEAN extends Dto> {
    BEAN save(BeanFieldGroup fieldGroup);

    Set<BEAN> findAll();

    void showEditor();

    void showList();

    void setService(Service<BEAN> service);

    Service<BEAN> getService();
}
