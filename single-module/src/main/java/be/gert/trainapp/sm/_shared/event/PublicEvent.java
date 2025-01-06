package be.gert.trainapp.sm._shared.event;


import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate events which may be processed in another module.
 * Though only when using @ApplicationModuleListener (async/after commit)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(TYPE)
public @interface PublicEvent {
}
