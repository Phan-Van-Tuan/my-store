package com.jpatrick.mystore.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.jpatrick.mystore.R
import com.jpatrick.mystore.data.model.Feedback
import com.jpatrick.mystore.utils.LoadFormat

class CommentAdapter(
    private var list: List<Feedback>,
    private val onFeedbackClick: (feedbackId: String) -> Unit // Callback xử lý sự kiện click
) : RecyclerView.Adapter<CommentAdapter.ItemViewHolder>() {

    // ViewHolder là nơi ánh xạ các thành phần trong layout item_view.xml
    class ItemViewHolder(feedbackView: View) : RecyclerView.ViewHolder(feedbackView) {
        private val avatar: ImageView = itemView.findViewById(R.id.feedback_user_avatar)
        private val username: TextView = itemView.findViewById(R.id.feedback_username)
        private val comment: TextView = itemView.findViewById(R.id.feedback_comment)
        private val time: TextView = itemView.findViewById(R.id.feedback_time)
        private val stars = listOf(
            itemView.findViewById<ImageView>(R.id.star1),
            itemView.findViewById<ImageView>(R.id.star2),
            itemView.findViewById<ImageView>(R.id.star3),
            itemView.findViewById<ImageView>(R.id.star4),
            itemView.findViewById<ImageView>(R.id.star5)
        )

        private fun setStars(rating: Int, stars: List<ImageView>) {
            for (i in 0 until 5) {
                if (i < rating) {
                    stars[i].setImageResource(R.drawable.baseline_star_rate_full_24)  // Tô màu vàng
                } else {
                    stars[i].setImageResource(R.drawable.baseline_star_rate_empty_24) // Không tô màu
                }
            }
        }

        fun bind(feedback: Feedback, onFeedbackClick: (feedbackId: String) -> Unit) {
            val loadFormat = LoadFormat()
            loadFormat.loadImage(feedback.userAvatar, avatar)
            username.text = feedback.userName
            comment.text = feedback.comment
            time.text = feedback.createdAt
            setStars(feedback.rating.toInt(), stars)
            // Thiết lập sự kiện click cho item
            itemView.setOnClickListener {
                onFeedbackClick(feedback._id) // Gọi hàm callback khi click vào item
            }
        }
    }

    // Tạo ViewHolder cho mỗi item của RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_feedback, parent, false)
        return ItemViewHolder(view)
    }

    // Gắn dữ liệu vào ViewHolder
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(list[position], onFeedbackClick)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}