package es.mhp.books.ui.views.crud;

import com.vaadin.ui.Component;

/**
 * Created by afuentes on 27/12/15.
 */
public interface EditorViewComponent<BEAN> extends Component {
    void setDatasource(BEAN bean);
    void setValidationVisible(boolean isVisiable);
}
