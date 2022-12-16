package fr.sfc.container.common;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Popup;

public class SelectionBox<T> extends GridPane {

    private final Popup popup;
    private final TextField input;
    private final ListView<T> listView;
    private final Label hintLabel;
    private final Separator line;
    private final SVGPath svgIcon;

    public SelectionBox(String hint, String icon, ObservableList<T> list, ListCell<T> template){
        listView = new ListView<>(list);
        listView.setCellFactory(v -> template);
        listView.setMaxHeight(200);
        popup = new Popup();
        popup.getContent().add(listView);
        popup.setAutoHide(true);
        popup.setHideOnEscape(true);
        input = new TextField();
        input.setBackground(null);
        hintLabel = new Label(hint);
        line = new Separator();
        line.setBorder(new Border(
                new BorderStroke(
                        Color.LIGHTBLUE,
                        Color.TRANSPARENT,
                        Color.TRANSPARENT,
                        Color.TRANSPARENT,
                        BorderStrokeStyle.SOLID,
                        BorderStrokeStyle.NONE,
                        BorderStrokeStyle.NONE,
                        BorderStrokeStyle.NONE,
                        null, null, null)));
        svgIcon = new SVGPath();
        svgIcon.setContent(icon);
        addRow(0, svgIcon);
        addRow(1, line);
        setColumnSpan(line, 3);
        setHgrow(input, Priority.ALWAYS);
        input.textProperty().addListener(this::onTextChanged);
    }

    void onTextChanged(ObservableValue<? extends String> observable, String oldValue, String newValue){
        if(oldValue.equals(newValue)) return;
        if(!popup.isShowing()){
            var point = input.localToScreen(0.0, 0.0);
            listView.setMinWidth(input.getWidth());
            popup.show(input, point.getX(), point.getY() + getHeight());
        }
    }

}
