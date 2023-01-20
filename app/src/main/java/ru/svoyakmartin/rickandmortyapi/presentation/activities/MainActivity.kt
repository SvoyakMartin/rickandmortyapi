package ru.svoyakmartin.rickandmortyapi.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.svoyakmartin.rickandmortyapi.R
import ru.svoyakmartin.rickandmortyapi.data.repository.UserPreferencesRepository
import ru.svoyakmartin.rickandmortyapi.presentation.fragments.SettingsFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragmentContainerView, SettingsFragment())
                .commit()
        }
    }
}