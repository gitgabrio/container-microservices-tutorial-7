package net.microservices.tutorial.test

import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.web.WebAppConfiguration
import org.junit.Assert.assertNotNull

/**
 * Abstract class to be extended by actual Test classes. It implements <code>InstanceTestClassListener</code>
 * to provide implementation of method to be called only once for class (for example instantiation of object shared between methods)
 * Implementing class should provide the @ContextConfiguration annotation.
 * Tests will be executed unxer the <b>test</b> Spring profile
 *
 * Created by Gabriele Cardosi - gcardosi@cardosi.net on 29/05/17.
 */
@RunWith(SpringInstanceTestClassRunner::class)  // This is to use our custom <code>SpringJUnit4ClassRunner</code>
@ActiveProfiles("test")
abstract class AbstractContextTest : InstanceTestClassListener {

    /**
     * The `Environment` env.
     */
    @Autowired
    protected var env: Environment? = null

    /**
     * Before class setup.
     */
    override fun beforeClassSetup() {
        assertNotNull(env)
    }

    /**
     * After class setup.
     */
    override fun afterClassSetup() {

    }
}