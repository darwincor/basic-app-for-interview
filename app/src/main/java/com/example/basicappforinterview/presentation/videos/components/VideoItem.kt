package com.example.basicappforinterview.presentation.videos.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.basicappforinterview.domain.model.Video

@Composable
fun VideoItem(
    video: Video,
    onClick: () -> Unit = {}
) {
    Card (
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = CenterVertically
        ) {
            Box (
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
            ) {
                AsyncImage(
                    model = video.thumbnail,
                    contentDescription = "Thumbnail",
                    clipToBounds = true,
                )
            }

            Column {
                Text(text = video.title, fontWeight = FontWeight.Bold)
            }
        }
    }
}

fun crossfade(b: Boolean) {

}

@Preview
@Composable
fun VideoItemPreview() {
    VideoItem(
        Video(
            id = "Title",
            title = "This is a title",
            thumbnail = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png"
        )
    )
}