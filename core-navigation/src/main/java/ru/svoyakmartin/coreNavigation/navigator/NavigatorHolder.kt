package ru.svoyakmartin.coreNavigation.navigator

interface NavigatorHolder<T> {
    fun bind(navigator: T)
    fun unbind()
    fun executeCommand(command: T.() -> (Unit))
}