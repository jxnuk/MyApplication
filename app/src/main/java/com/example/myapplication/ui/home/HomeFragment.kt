package com.example.myapplication.ui.home

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recycler = view.findViewById<RecyclerView>(R.id.reviewsRecycler)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = ReviewAdapter(dummyReviews()) { _, _ ->
            showSubmissionPopup("Successfully Submitted")
        }
    }

    private fun dummyReviews(): List<Review> = listOf(
        Review(
            title = "Where can I get some?",
            body = "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration...",
            author = "Karin Seal",
            imageRes = R.drawable.special
        ),
        Review(
            title = "Nice variety, quick service",
            body = "here are many variations of passages of Lorem Ipsum avaianthing embarrassing hidden in the middle...",
            author = "Karin Seal"
        ),
        Review(
            title = "Not my taste",
            body = "here are many variations of passages opsum avaianthing embarrassing hidden in the middle...",
            author = "Yahan y222"
        ),
        Review(
            title = "Fresh and colorful",
            body = "The generated Lorem Ipsum is therefore always free from repetition, injected humour...",
            author = "Karin Seal",
            imageRes = R.drawable.special
        )
    )

    private fun showSubmissionPopup(message: String) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_success) // from earlier step
        dialog.window?.apply {
            setBackgroundDrawableResource(android.R.color.transparent)
            setLayout(
                (resources.displayMetrics.widthPixels * 0.85f).toInt(),
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setGravity(Gravity.CENTER)
        }
        dialog.findViewById<TextView>(R.id.tvMessage)?.text = message
        dialog.setCancelable(false)
        dialog.show()
        Handler(Looper.getMainLooper()).postDelayed({ if (dialog.isShowing) dialog.dismiss() }, 1400)
    }
}
