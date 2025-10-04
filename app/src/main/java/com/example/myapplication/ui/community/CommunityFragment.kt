package com.example.myapplication.ui.community

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.database.data.Booking
import com.example.myapplication.ui.booking.BookingViewModel
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CommunityFragment : Fragment() {

    // Shared VM so Community + Booking fragments talk to the same DB
    private val bookingVm: BookingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_community, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val rv = view.findViewById<RecyclerView>(R.id.communityRecycler)
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = CommunityPostAdapter(dummyPosts()) { post, action ->
            when (action) {
                "open"     -> showPostDetails(post)
                "share"    -> toast("Share tapped")
                "location" -> toast("Location tapped")
                "comment"  -> toast("Comment tapped")
                "close"    -> toast("Closed")
            }
        }
    }

    // ----- Popup dialog -----
    private fun showPostDetails(post: CommunityPost) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_post_details)
        dialog.window?.apply {
            setBackgroundDrawableResource(android.R.color.transparent)
            setLayout((resources.displayMetrics.widthPixels * 0.90f).toInt(),
                WindowManager.LayoutParams.WRAP_CONTENT)
            setGravity(Gravity.CENTER)
            setDimAmount(0.35f)
        }

        val img        = dialog.findViewById<ImageView>(R.id.img)
        val tvBody     = dialog.findViewById<TextView>(R.id.tvBody)
        val tvPhone    = dialog.findViewById<TextView>(R.id.tvPhone)
        val ratingBar  = dialog.findViewById<RatingBar>(R.id.ratingBar)
        val btnClose   = dialog.findViewById<ImageButton>(R.id.btnClose)
        val btnShare   = dialog.findViewById<ImageButton>(R.id.btnShare)
        val btnLoc     = dialog.findViewById<ImageButton>(R.id.btnLocation)
        val btnBook    = dialog.findViewById<MaterialButton>(R.id.btnBook)

        img.setImageResource(post.imageRes)
        tvBody.text = post.body
        tvPhone.text = post.phone
        ratingBar.rating = post.defaultRating

        btnClose.setOnClickListener { dialog.dismiss() }
        btnShare.setOnClickListener { toast("Shared!") }
        btnLoc.setOnClickListener { toast("Open maps soon…") }
        tvPhone.setOnClickListener {
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${post.phone}")))
        }

        btnBook.setOnClickListener {
            // Build a booking from current user + current time
            val booking = Booking(
                bookingNumber = generateBookingNumber(),
                userName      = currentUserNameOrPlaceholder(),  // swap to your DAO-backed name
                restaurantName = "ABC Restaurant plc",           // demo value
                dateTimeText  = nowAsText(),
                phone         = post.phone,
                imageResName  = resources.getResourceEntryName(post.imageRes),
                status        = "ACTIVE"
            )
            bookingVm.addBooking(booking)
            toast("Booking created ✔")
            dialog.dismiss()
        }

        dialog.setCancelable(true)
        dialog.show()
    }

    // ----- Helpers -----

    private fun generateBookingNumber(): String {
        // Simple timestamp-based id (last 5 digits)
        val ts = System.currentTimeMillis() % 100000
        return ts.toString().padStart(5, '0')
    }

    private fun nowAsText(): String {
        // Safe for all APIs (java.time alternative)
        val fmt = SimpleDateFormat("dd.MM.yyyy, h:mma", Locale.getDefault())
        return fmt.format(Date()).lowercase(Locale.getDefault())
    }

    private fun currentUserNameOrPlaceholder(): String {
        // TODO: Replace with your actual UserDao query for the logged-in user.
        // e.g., AppDatabase.get(requireContext()).userDao().getLoggedInUserName()
        return "Januk Dissanayake"
    }

    private fun toast(msg: String) =
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()

    // Dummy feed items for now
    private fun dummyPosts(): List<CommunityPost> = listOf(
        CommunityPost(
            body = "here are many variations of passages opsum avaianthing embarrassing hidden in the middle",
            imageRes = R.drawable.special
        ),
        CommunityPost(
            body = "fresh deal near you—share location to see stores",
            imageRes = R.drawable.menu  // use any second drawable you’ve added
        ),
        CommunityPost(
            body = "crispy bites and quick service—worth the wait",
            imageRes = R.drawable.special
        )
    )
}
