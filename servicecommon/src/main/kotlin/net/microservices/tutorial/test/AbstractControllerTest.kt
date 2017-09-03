package net.microservices.tutorial.test

import org.junit.Assert.assertNotNull
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders

/**
 * Abstract class to be extended by actual <b>Controller</b> Test classes. It extends <code>AbstractContextTest</code>
 * to provide implementation of method to be called only once for class (for example instantiation of object shared between methods)
 * Implementing class should provide the @ContextConfiguration annotation.
 * Tests will be executed under the <b>test</b> Spring profile
 *
 * Created by Gabriele Cardosi - gcardosi@cardosi.net on 29/05/17.
 * It instantiate a <code>MockMvc</code> to be used by <b>REST</b> test
 */
//@RunWith(SpringInstanceTestClassRunner::class)  // This is to use our custom <code>SpringJUnit4ClassRunner</code>
@WebAppConfiguration
//@ActiveProfiles("test")
abstract class AbstractControllerTest : AbstractContextTest() {

    var mockMvc: MockMvc? = null

    /**
     * Before class setup.
     */
    open fun beforeClassSetup(controller: Any) {
        super.beforeClassSetup()
        assertNotNull(controller)
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build()
        assertNotNull(mockMvc)
    }

}