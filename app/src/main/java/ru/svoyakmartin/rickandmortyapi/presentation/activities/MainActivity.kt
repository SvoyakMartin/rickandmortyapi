package ru.svoyakmartin.rickandmortyapi.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import ru.svoyakmartin.coreUi.R
import ru.svoyakmartin.rickandmortyapi.presentation.fragments.StartFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            goToFragment(StartFragment())
        }
    }

    private fun goToFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.base_fragment_container, fragment)
        }
    }
}