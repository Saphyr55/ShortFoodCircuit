package fr.sfc.components;

import org.w3c.dom.Node;

public interface IComponent<T extends IComponent<?>> {

    T getSelf();

}
