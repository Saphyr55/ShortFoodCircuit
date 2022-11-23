package fr.sfc.api.component;

public interface IComponent<T extends IComponent<?>> {

    T getSelf();

}
