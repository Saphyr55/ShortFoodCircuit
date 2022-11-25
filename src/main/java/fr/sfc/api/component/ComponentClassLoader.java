package fr.sfc.api.component;

import com.google.common.collect.Sets;

import java.util.Set;

public class ComponentClassLoader {

    private final Set<String> componentPackages;

    public ComponentClassLoader(final String... componentPackages) {
        this.componentPackages = Sets.newHashSet(componentPackages);
    }

    public ComponentFactory createComponentFactory() {
        return new ComponentFactory(this);
    }

    public Set<String> getComponentPackages() {
        return componentPackages;
    }

}
