package com.gfd.notekeeper

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import androidx.navigation.ui.AppBarConfiguration
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_items.*
import kotlinx.android.synthetic.main.content_items.*

// Navigation Drawer
// Parent layout: activity_items
// Tutorial shows that its content should be layout/content_items but I assume that is outdated b/c we don't have that file.
// I'm assuming this is activity_main_drawer.
class ItemsActivity : AppCompatActivity(),  NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration

    // It's a good practice to make any property with a 'this' reference
    // into a lazy property
    // private val noteLayoutManager = LinearLayoutManager(this)
    private val noteLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    // must delay creation of NoteRecyclerAdapter until after onCreate is called
    // when using vals, you can create lazy property.
    // 'by lazy' will not created until it is used
    // This original implementation will not work - system services not available
    // to Activities before onCreate (I think 'this' is not available yet)
    //private val noteRecyclerAdapter = NoteRecyclerAdapter(this, DataManager.notes)
    private val noteRecyclerAdapter by lazy {
        NoteRecyclerAdapter(this, DataManager.notes)
    }

    private val courseLayoutManager by lazy {
        GridLayoutManager(this, 2)
    }

    private val courseRecyclerAdapter by lazy {
        CourseRecyclerAdapter(this, DataManager.courses.values.toList())
    }

    // This viewModel will not be enough to persist state if the activity is destroyed
    // by viewModels is a lazy property, which is what we want
    private val viewModel: ItemsActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            startActivity(Intent(this, NoteActivity::class.java))
        }

        // deprecated - use constructors of ViewModelProvider directly
        // val vm = ViewModelProviders.of(this)

        // Use the 'by viewModels()' Kotlin property delegate
        // from the activity-ktx artifact
        // val vm: ItemsActivityViewModel by viewModels()
        // extract vm as a property to be accessed elsewhere

        if(savedInstanceState != null) {
            viewModel.navDrawerDisplaySelection = savedInstanceState.getInt(viewModel.navDrawerDisplaySelectionName)
        }

        handleDisplaySelection(viewModel.navDrawerDisplaySelection)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navView: NavigationView = findViewById(R.id.nav_view)
        // ItemsActivity must extend NavigationView.OnNavigationItemSelectedListener in order to set the item selected listener
        navView.setNavigationItemSelectedListener(this)

        // remove boilerplate
//
//        // When this class was generated, it appears that nav_host_fragment was not created - it's showing as red here.
//        // We may not have a NavController.
//        val navController = findNavController(R.id.nav_host_fragment)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
//            ), drawerLayout
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
    }

    // stores viewmodel values inside state bundle so state
    // can be restored if activity is destroyed and re-created
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        if(outState != null) {
            outState.putInt(viewModel.navDrawerDisplaySelectionName, viewModel.navDrawerDisplaySelection)
        }
    }

    private fun displayNotes() {
        // listItems = new RecyclerView added to content_items (old one is named 'listitems' lowercase i)
        listItems.layoutManager = noteLayoutManager
        listItems.adapter = noteRecyclerAdapter

        // set active selection on Navigation View
        nav_view.menu.findItem(R.id.nav_notes).isChecked = true
    }

    private fun displayCourses() {
        listItems.layoutManager = courseLayoutManager
        listItems.adapter = courseRecyclerAdapter
        nav_view.menu.findItem(R.id.nav_courses).isChecked = true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.items, menu)
        return true
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment)
//        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
//    }

    override fun onResume() {
        super.onResume()
        listItems.adapter?.notifyDataSetChanged()
    }

    // single method that gets called when user makes a change
    // inside the NavigationView
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        handleDisplaySelection(item.itemId)
        viewModel.navDrawerDisplaySelection = item.itemId

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun handleDisplaySelection(itemId: Int) {
        when (itemId) {
            R.id.nav_notes -> {
                displayNotes()
            }
            R.id.nav_courses -> {
                displayCourses()
            }
        }
    }

    private fun handleSection(message: String) {
        Snackbar.make(listItems, message, Snackbar.LENGTH_LONG).show()
    }
}