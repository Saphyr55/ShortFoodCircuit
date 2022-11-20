package fr.sfc.component;

public interface IComponent<T extends IComponent<?>> {

    T getSelf();

}
