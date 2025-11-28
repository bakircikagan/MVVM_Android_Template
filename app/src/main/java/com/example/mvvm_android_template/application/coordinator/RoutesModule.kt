package com.example.mvvm_android_template.application.coordinator

/**
 * RoutesModule is no longer needed!
 *
 * Each Routable now self-registers using its own inner Module interface.
 * See any Routable implementation for the pattern.
 *
 * To add a new route:
 * 1. Create your Routable class with @Singleton
 * 2. Add an inner Module interface with @Binds @IntoSet (see WelcomeRoutable for example)
 * 3. That's it! The route is automatically discovered and included.
 */
