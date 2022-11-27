package fr.sfc.api.component;

import com.google.common.collect.Sets;
import javafx.scene.Node;

import java.util.List;
import java.util.Set;

public final class ComponentClassLoader {

    private final Set<Class<? extends Component>> componentClasses;
    private final List<Node> nodes;

    @SafeVarargs
    public ComponentClassLoader(final List<Node> nodes, final Class<? extends Component>... componentClasses) {
        this.componentClasses = Sets.newHashSet(componentClasses);
        this.nodes = nodes;
    }

    public ComponentManager createComponentFactory() {
        return new ComponentManager(this);
    }

    public Set<Class<? extends Component>> getComponentPackages() {
        return componentClasses;
    }

    public List<Node> getNodes() {
        return nodes;
    }
}
