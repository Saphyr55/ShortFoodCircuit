package fr.sfc.api.component;

import com.google.common.collect.Sets;
import fr.sfc.api.RuntimeApplication;

import java.util.Set;

public final class ComponentClassLoader {

    private final Set<Class<? extends Component>> componentClasses;
    
    @SafeVarargs
    public ComponentClassLoader(final Class<? extends Component>... componentClasses) {
        this.componentClasses = Sets.newHashSet(componentClasses);
    }

    public ComponentFactory createComponentFactory() {
        return new ComponentFactory(this);
    }

    public Set<Class<? extends Component>> getComponentPackages() {
        return componentClasses;
    }

}
