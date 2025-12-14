package com.example.machineproblem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class GroupPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_page)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MyGroupsFragment())
                .commit()
        }
    }
}

class MyGroupsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GroupsAdapter
    private lateinit var firestore: FirebaseFirestore
    private val groupsList = mutableListOf<Group>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_groups, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = GroupsAdapter(groupsList) { group ->
            onJoinGroupClicked(group)
        }
        recyclerView.adapter = adapter

        // Load groups from Firestore
        loadGroupsFromFirestore()
    }

    private fun loadGroupsFromFirestore() {
        firestore.collection("groups")
            .get()
            .addOnSuccessListener { documents ->
                groupsList.clear()
                for (document in documents) {
                    val group = Group(
                        id = document.id,
                        name = document.getString("name") ?: "",
                        description = document.getString("description") ?: "",
                        imageUrl = document.getString("imageUrl") ?: ""
                    )
                    groupsList.add(group)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Error loading groups: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun onJoinGroupClicked(group: Group) {
        // For now, just show a toast message
        Toast.makeText(context, "Joined ${group.name}!", Toast.LENGTH_SHORT).show()

        // Later, you can navigate to another page like this:
        // val intent = Intent(requireContext(), GroupDetailsActivity::class.java)
        // intent.putExtra("group_id", group.id)
        // startActivity(intent)
    }
}

data class Group(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val imageUrl: String = ""
)