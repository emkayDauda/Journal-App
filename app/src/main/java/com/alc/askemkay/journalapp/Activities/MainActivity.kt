package com.alc.askemkay.journalapp.Activities

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.alc.askemkay.journalapp.models.JournalAdapter
import com.alc.askemkay.journalapp.models.JournalEntryModel
import com.alc.askemkay.journalapp.models.JournalViewModel
import com.alc.askemkay.journalapp.R
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import android.content.Intent
import android.view.View
import android.widget.TextView
import com.alc.askemkay.journalapp.models.RecyclerViewClickListenerInterface
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.journal_entry.view.*
import org.jetbrains.anko.*


class MainActivity : AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener, RecyclerViewClickListenerInterface{



    private lateinit var mAuth: FirebaseAuth
    private lateinit var mGoogleApiClient: GoogleApiClient
    private lateinit var mDatabaseReference: DatabaseReference

    private lateinit var userDisplayName: TextView
    private lateinit var userEmail: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var viewModel: JournalViewModel

    companion object {
        const val NEW_ENTRY_ACTIVITY_REQUEST_CODE = 1
        const val EDIT_ENTRY_ACTIVITY_REQUEST_CODE = 1234

        const val SERIAL_NUMBER_EXTRA = "serialNumber"

        const val JOURNAL_ENTRY_EXTRA = "journalEntry"

        const val ENTRY_EMOTION_EXTRA = "entryEmotion"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mAuth = FirebaseAuth.getInstance()

        viewManager = LinearLayoutManager(this)



        viewAdapter = JournalAdapter(this)

        viewModel = ViewModelProviders.of(this).get(JournalViewModel(application)::class.java)

        viewModel.getAllEntries().observe(this, Observer<List<JournalEntryModel>> {
            if (it != null) {
                (viewAdapter as JournalAdapter).setWords(it)
            } else toast("Null data")
        })

        mDatabaseReference = FirebaseDatabase.getInstance().reference

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }

        val headerView = findViewById<NavigationView>(R.id.nav_view).getHeaderView(0)
        userDisplayName = headerView.findViewById(R.id.user_display_name_tv)
        userEmail = headerView.findViewById(R.id.user_email_tv)

        if (intent.hasExtra("displayName")){
            if (intent.getStringExtra("displayName").isNotEmpty()){
                userDisplayName.text = intent.getStringExtra("displayName")
            }
        } else userDisplayName.text = ""

        if (intent.hasExtra("email")){
            if (intent.getStringExtra("email").isNotEmpty()){
                userEmail.text = intent.getStringExtra("email")
            }
        } else userEmail.text = ""


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()

        mGoogleApiClient.connect()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replaced", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()

            val intent = Intent(this@MainActivity, AddEntryActivity::class.java)
            startActivityForResult(intent, NEW_ENTRY_ACTIVITY_REQUEST_CODE)
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_ENTRY_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val entry = (data?.getParcelableExtra(AddEntryActivity.EXTRA_REPLY))
                    as JournalEntryModel
            toast("new entry req")
            viewModel.insert(entry)
        } else if (requestCode == EDIT_ENTRY_ACTIVITY_REQUEST_CODE
                && resultCode == Activity.RESULT_OK){
            toast("edit entry req")
            val entry = (data?.getParcelableExtra(AddEntryActivity.EXTRA_REPLY))
                    as JournalEntryModel


            if (data.hasExtra(AddEntryActivity.SERIAL_NUMBER_FOR_SELECTED_ENTRY)){
                val oldSerialNumber =
                        data.getStringExtra(AddEntryActivity.SERIAL_NUMBER_FOR_SELECTED_ENTRY)
                viewModel.deleteEntry(oldSerialNumber)
            }
            viewModel.insert(entry)
        }

        else {
            longToast("No entry was saved.")
        }
    }

    override fun onClick(v: View, position: Int) {
        val journalEntry = v.entry.text.toString()
        val entryEmotion = v.emotion.text.toString()
        val serialNumber= v.sn_text_view.text.toString()
        val editEntryIntent = Intent(this@MainActivity, AddEntryActivity::class.java)
        editEntryIntent.putExtra(JOURNAL_ENTRY_EXTRA, journalEntry)
        editEntryIntent.putExtra(SERIAL_NUMBER_EXTRA, serialNumber)
        editEntryIntent.putExtra(ENTRY_EMOTION_EXTRA, entryEmotion)

        startActivityForResult(editEntryIntent, EDIT_ENTRY_ACTIVITY_REQUEST_CODE)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {

            R.id.sync_firebase -> {
                toast("Yet to be implemented")
            }

            R.id.log_out -> {
                alert {
                    title = "Sure to sign out?"
                    message = "You are about to exit the app"
                    positiveButton("Exit"){
                        mAuth.signOut()
                        Auth.GoogleSignInApi.signOut(mGoogleApiClient)
                        finish()
                    }
                    negativeButton("Just Sign Out"){
                        mAuth.signOut()
                        Auth.GoogleSignInApi.signOut(mGoogleApiClient)

                        startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                        finish()
                    }

                    neutralPressed("Cancel"){
                        it.dismiss()
                    }
                }.show()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onLongClick(v: View, position: Int) {
        alert {
            title = "Confirmation"
            message = "Would you like to delete this entry?"
            yesButton {
                viewModel.deleteEntry(v.sn_text_view.text.toString())
            }
            cancelButton{
                it.dismiss()
            }
        }.show()
    }
}
