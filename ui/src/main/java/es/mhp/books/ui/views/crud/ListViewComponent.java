package es.mhp.books.ui.views.crud;

import com.vaadin.ui.Component;

/**
 * Created by afuentes on 27/12/15.
 */
public interface ListViewComponent<BEAN> extends Component {
    void refresh();

    BEAN getValue();
}
