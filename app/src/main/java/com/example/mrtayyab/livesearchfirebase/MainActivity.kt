package com.example.mrtayyab.livesearchfirebase

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_list.view.*

class MainActivity : AppCompatActivity() {

    lateinit var mSearchText : EditText
    lateinit var mRecyclerView : RecyclerView

    lateinit var mDatabase : DatabaseReference

    lateinit var FirebaseRecyclerAdapter : FirebaseRecyclerAdapter<User , UsersViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSearchText =findViewById(R.id.searchText)
        mRecyclerView = findViewById(R.id.list_view)


        mDatabase = FirebaseDatabase.getInstance().getReference("Users")


        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.setLayoutManager(LinearLayoutManager(this))




        mSearchText.addTextChangedListener(object  : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                val searchText = mSearchText.getText().toString().trim()

                loadFirebaseData(searchText)
            }
        } )

    }

    private fun loadFirebaseData(searchText : String) {

        if(searchText.isEmpty()){

            FirebaseRecyclerAdapter.cleanup()
            mRecyclerView.adapter = FirebaseRecyclerAdapter

        }else {


            val firebaseSearchQuery = mDatabase.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff")

            FirebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<User, UsersViewHolder>(

                    User::class.java,
                    R.layout.layout_list,
                    UsersViewHolder::class.java,
                    firebaseSearchQuery


            ) {
                override fun populateViewHolder(viewHolder: UsersViewHolder, model: User?, position: Int) {


                    viewHolder.mview.userName.setText(model?.name)
                    viewHolder.mview.userStatus.setText(model?.status)
                    Picasso.with(applicationContext).load(model?.image).into(viewHolder.mview.UserImageView)

                }

            }

            mRecyclerView.adapter = FirebaseRecyclerAdapter

        }
    }


    // // View Holder Class

    class UsersViewHolder(var mview : View) : RecyclerView.ViewHolder(mview) {

    }


}
