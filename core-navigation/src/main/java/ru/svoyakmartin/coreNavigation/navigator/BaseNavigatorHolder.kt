package ru.svoyakmartin.coreNavigation.navigator

open class BaseNavigatorHolder<T> : NavigatorHolder<T> {

    protected var navigator: T? = null
    private val commands: MutableList<T.() -> Unit> = mutableListOf()

    override fun bind(navigator: T) {
        this.navigator = navigator
        commands.forEach(::executeCommand)
        commands.clear()
    }

    override fun unbind() {
        navigator = null
    }

    override fun executeCommand(command: T.() -> Unit) {
        navigator?.apply(command) ?: commands.add(command)
    }
}