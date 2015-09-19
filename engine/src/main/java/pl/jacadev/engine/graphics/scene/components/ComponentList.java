package pl.jacadev.engine.graphics.scene.components;

import pl.jacadev.engine.graphics.scene.Component;

import java.util.HashSet;

/**
 * @author Jaca777
 *         Created 2015-07-13 at 14
 */
public class ComponentList<T extends Component> extends HashSet<T> {

    public boolean addAll(T... t) {
        for(T e : t)
            if(!add(e)) return false;
        return true;
    }

    public boolean setAll(T... t) {
        clear();
        return addAll(t);
    }

}
