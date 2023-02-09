package ru.svoyakmartin.coreNavigation.navigator

import androidx.fragment.app.FragmentActivity
import ru.svoyakmartin.coreDi.di.scope.AppScope
import javax.inject.Inject

@AppScope
class ActivityNavigatorHolder @Inject constructor() : BaseNavigatorHolder<FragmentActivity>()