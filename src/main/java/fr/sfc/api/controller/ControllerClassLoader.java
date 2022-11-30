package fr.sfc.api.controller;

import com.google.common.collect.Sets;

import java.util.Set;

public class ControllerClassLoader {

    private final Set<Class<? extends Controller>> componentClasses;
    
    @SafeVarargs
    public ControllerClassLoader(final Class<? extends Controller>... componentClasses) {
        this.componentClasses = Sets.newHashSet(componentClasses);
    }

    public ControllerManager createControllerFactory() {
        return new ControllerManager(this);
    }

    public Set<Class<? extends Controller>> getControllerPackages() {
        return componentClasses;
    }

}
