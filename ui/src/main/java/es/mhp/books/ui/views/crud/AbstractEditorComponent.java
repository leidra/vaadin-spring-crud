package es.mhp.books.ui.views.crud;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.*;
import es.mhp.dtos.Dto;

import java.util.stream.Stream;

/**
 * Created by afuentes on 28/12/15.
 */
public abstract class AbstractEditorComponent<BEAN extends Dto> extends AbstractComponent<BEAN> implements EditorViewComponent<BEAN> {
    protected BeanFieldGroup<BEAN> fieldGroup;

    @Override
    protected CssLayout buildView() {
        CssLayout editorLayout = new CssLayout();
        editorLayout.addStyleName("editor");

        createFieldsComponents(editorLayout);
        createButtons(editorLayout);
        configureFields();

        return editorLayout;
    }

    protected CssLayout createFieldContainer(Component field) {
        CssLayout fieldContainer = new CssLayout();
        fieldContainer.addStyleName("editor_field");
        fieldContainer.addComponent(field);

        return fieldContainer;
    }

    protected void createButtons(CssLayout editorLayout) {
        CssLayout buttonsContainer = new CssLayout();
        buttonsContainer.addStyleName("editor_buttons");

        Button btnSave = new Button("Save", this::saveAction);
        btnSave.addStyleName("editor_button");
        btnSave.addStyleName("editor_button-save");
        buttonsContainer.addComponent(btnSave);

        Button btnCancel = new Button("Cancel", this::cancelAction);
        btnCancel.addStyleName("editor_button");
        btnCancel.addStyleName("editor_button-cancel");
        buttonsContainer.addComponent(btnCancel);

        editorLayout.addComponent(buttonsContainer);
    }

    protected abstract void configureFields();
    protected abstract void createFieldsComponents(CssLayout editorLayout);

    @Override
    public void setDatasource(BEAN dto) {
        this.bean = dto;

        fieldGroup.setItemDataSource(bean);
        fieldGroup.bindMemberFields(this);
    }

    protected void saveAction(Button.ClickEvent e) {
        bean = presenter.save(fieldGroup);

        if (fieldGroup.isValid()) {
            presenter.showList();
        }
    }

    protected void cancelAction(Button.ClickEvent e) {
        fieldGroup.discard();
        fieldGroup.clear();
        this.setValidationVisible(false);
        presenter.showList();
    }

    protected void configureFields(Field... fields) {
        Stream.of(fields).filter(f -> FieldEvents.BlurNotifier.class.isAssignableFrom(f.getClass()))
                .forEach(p -> configureField(p));
    }

    protected void configureField(Field p) {
        FieldEvents.BlurNotifier blurField = (FieldEvents.BlurNotifier) p;
        AbstractField formField = (AbstractField) p;
        formField.setValidationVisible(false);
        blurField.addBlurListener(ev -> formField.setValidationVisible(!formField.isValid()));
    }

    public void setValidationVisible(boolean isVisible) {
        Stream.of(this.getClass().getDeclaredFields())
            .filter(f -> AbstractField.class.isAssignableFrom(f.getType()))
            .forEach(p -> {
                try {
                    p.setAccessible(true);
                    ((AbstractField) p.get(this)).setValidationVisible(isVisible);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
    }
}
