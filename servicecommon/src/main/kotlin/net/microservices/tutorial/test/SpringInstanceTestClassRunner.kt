package net.microservices.tutorial.test

import org.junit.runner.notification.RunNotifier
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import java.lang.Exception

/**
 * Custom class extending <code>SpringJUnit4ClassRunner</code> to call our specific methods
 * defined in <code>InstanceTestClassListener</code>
 *
 * Created by Gabriele Cardosi - gcardosi@cardosi.net on 29/05/17.
 */
class SpringInstanceTestClassRunner(clazz: Class<*>) : SpringJUnit4ClassRunner(clazz) {

    private var instanceSetupListener: InstanceTestClassListener? = null

//    @Throws(InitializationError::class)
//    fun SpringInstanceTestClassRunner(clazz: Class<*>): ??? {
//        super(clazz)
//    }

    @Throws(Exception::class)
    override fun createTest(): Any {
        val test = super.createTest()
        // Note that JUnit4 will call this createTest() multiple times for each
        // test method, so we need to ensure to call "beforeClassSetup" only once.
        if (test is InstanceTestClassListener && instanceSetupListener == null) {
            instanceSetupListener = test as InstanceTestClassListener
            instanceSetupListener!!.beforeClassSetup()
        }
        return test

    }

    override fun run(notifier: RunNotifier) {
        super.run(notifier)
        if (instanceSetupListener != null)
            instanceSetupListener!!.afterClassSetup()
    }


}