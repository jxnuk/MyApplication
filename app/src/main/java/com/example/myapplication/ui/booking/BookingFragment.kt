package com.example.myapplication.ui.booking

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import android.widget.TextView

class BookingFragment : Fragment() {

    private val vm: BookingViewModel by activityViewModels()
    private lateinit var adapter: BookingAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_booking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val rv = view.findViewById<RecyclerView>(R.id.bookingRecycler)
        rv.layoutManager = LinearLayoutManager(requireContext())

        adapter = BookingAdapter(emptyList()) { booking ->
            // user tapped "Cancel" on a card
            showCancelPopup(booking)
        }
        rv.adapter = adapter

        vm.bookings.observe(viewLifecycleOwner) { list ->
            adapter.submit(list)
        }
    }

    private fun showCancelPopup(booking: com.example.myapplication.database.data.Booking) {
        // Remove from DB first (or do it after OK if you prefer)
        vm.cancelBooking(booking)

        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_booking_cancelled)
        dialog.window?.apply {
            setBackgroundDrawableResource(android.R.color.transparent)
            setLayout(
                (resources.displayMetrics.widthPixels * 0.85f).toInt(),
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            setGravity(Gravity.CENTER)
            setDimAmount(0.35f)
        }

        dialog.findViewById<TextView>(R.id.tvBookingNumber)?.text =
            "Booking number - ${booking.bookingNumber}"

        dialog.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnOk)
            ?.setOnClickListener { dialog.dismiss() }

        dialog.setCancelable(true)
        dialog.show()
    }
}
