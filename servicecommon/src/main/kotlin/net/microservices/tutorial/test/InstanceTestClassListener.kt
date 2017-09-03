package net.microservices.tutorial.test

/**
 * Created by Gabriele Cardosi - gcardosi@cardosi.net on 29/05/17.
 */
interface InstanceTestClassListener {

    /**
     * Method called by <code>SpringInstanceTestClassRunner</code> inside the overriding <code>createTest</code> method
     */
    fun beforeClassSetup()

    /**
     * Method called by <code>SpringInstanceTestClassRunner</code> inside the overriding <code>run(RunNotifier)</code> method
     */
    fun afterClassSetup()
}