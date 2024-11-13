package it.unibo.mvc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.controller.DrawNumberControllerImpl;
import it.unibo.mvc.model.DrawNumberImpl;

/**
 * Application entry-point.
 */
public final class LaunchApp {
    private static final String VIEWS_PACKAGE_NAME = "it.unibo.mvc.view";
    private static final Set<String> VIEW_CLASSES = Set.of(
        "DrawNumberStdoutView",
        "DrawNumberSwingView"
    );
    private static final int VIEW_CLASS_INSTANCES = 3;

    private LaunchApp() { }

    /**
     * Runs the application.
     *
     * @param args ignored
     * @throws ClassNotFoundException if the fetches class does not exist
     * @throws NoSuchMethodException if the 0-ary constructor do not exist
     * @throws InvocationTargetException if the constructor throws exceptions
     * @throws InstantiationException if the constructor throws exceptions
     * @throws IllegalAccessException in case of reflection issues
     * @throws IllegalArgumentException in case of reflection issues
     */
    public static void main(final String... args) 
    throws 
        ClassNotFoundException,
        NoSuchMethodException,
        InstantiationException,
        IllegalAccessException,
        InvocationTargetException {
        final var model = new DrawNumberImpl();
        final DrawNumberController app = new DrawNumberControllerImpl(model);
        for (final String className : VIEW_CLASSES) {
            final String fullName = VIEWS_PACKAGE_NAME + "." + className;
            final Class<?> rawClass = Class.forName(fullName);
            if (!List.of(rawClass.getInterfaces()).contains(DrawNumberView.class)) {
                throw new IllegalArgumentException(
                    fullName + "does not implement" + DrawNumberView.class.getName()
                );
            }
            @SuppressWarnings("unchecked") // Implementation of interface is checked before
            final Class<DrawNumberView> viewClass = (Class<DrawNumberView>)rawClass;
            Constructor<DrawNumberView> emptyConstructor = viewClass.getConstructor();
            for (int i = 0; i < VIEW_CLASS_INSTANCES; i++) {
                app.addView(emptyConstructor.newInstance());
            }
        }
    }
}
