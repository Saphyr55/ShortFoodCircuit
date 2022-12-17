package fr.sfc.framework.controlling;

import javafx.css.Styleable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.ArrayList;
import java.util.List;

public final class ParkourNodes {

    public static List<Node> getAllNodes(final Styleable root) {
        final ArrayList<Node> nodes = new ArrayList<>();
        addAllDescendents(root, nodes);
        return nodes;
    }

    private static void addAllDescendents(final Styleable styleable, final ArrayList<Node> nodes) {
        if (styleable instanceof final Parent parent) {

            if (styleable instanceof final TabPane tabPane) {
                for (final Tab tab : tabPane.getTabs())
                    content(tab, nodes);
            }
            else if (styleable instanceof final ScrollPane scrollPane) {
                content(scrollPane, nodes);
            }
            else for (final Node node : parent.getChildrenUnmodifiable()) {
                addAll(node, nodes);
            }
        }
    }

    private static void content(final Tab tab, final ArrayList<Node> nodes) {
        addAll(tab.getContent(), nodes);
    }

    private static void content(final ScrollPane scrollPane, final ArrayList<Node> nodes) {
        addAll(scrollPane.getContent(), nodes);
    }

    private static void addAll(final Node node, final ArrayList<Node> nodes) {
        nodes.add(node);
        addAllDescendents(node, nodes);
    }

}

