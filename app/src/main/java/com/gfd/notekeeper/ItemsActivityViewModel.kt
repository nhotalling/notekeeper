package com.gfd.notekeeper

import androidx.lifecycle.ViewModel

class ItemsActivityViewModel : ViewModel() {
    // mutable property to store what user had last
    // selected from the navigation drawer
    var navDrawerDisplaySelection = R.id.nav_notes
}