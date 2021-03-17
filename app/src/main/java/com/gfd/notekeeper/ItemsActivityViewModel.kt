package com.gfd.notekeeper

import androidx.lifecycle.ViewModel

class ItemsActivityViewModel : ViewModel() {
    // mutable property to store what user had last
    // selected from the navigation drawer
    var navDrawerDisplaySelection = R.id.nav_notes

    // typically use fully qualified name to avoid collisions with other data
    var navDrawerDisplaySelectionName =
        "com.gfd.notekeeper.ItemsActivityViewModel.navDrawerDisplaySelection"
}