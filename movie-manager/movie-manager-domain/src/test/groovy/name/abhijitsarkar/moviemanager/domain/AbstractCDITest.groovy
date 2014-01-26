package name.abhijitsarkar.moviemanager.domain

import javax.ejb.embeddable.EJBContainer

import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass

abstract class AbstractCDITest {
	protected static EJBContainer container;

	// GOTCHA ALERT: The following methods are guaranteed to be called before the corresponding ones in the subclasses
	// but ONLY IF the subclass methods have unique names. For example, if a subclass
	// declares a @Before method with the name bind, JUnit WILL NOT CALL the superclass method

	@BeforeClass
	public static void createContainer() {
		container = EJBContainer.createEJBContainer()
	}

	@Before
	public void bind() {
		container.getContext().bind('inject', this)
	}

	@After
	public void unbind() {
		container.getContext().unbind('inject')
	}

	@AfterClass
	public static void destroyContainer() {
		container.close()
	}
}