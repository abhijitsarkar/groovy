package name.abhijitsarkar.moviemanager.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Stereotype;

// Define a stereotype for class level
@Stereotype
@Retention(RUNTIME)
@Target(TYPE)
// Here define all annotations you want to replace by this one.
// This stereotype define an alternative
@Alternative
public @interface MovieService {
	Category value();
}