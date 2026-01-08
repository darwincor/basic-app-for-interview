package com.example.basicappforinterview.presentation.videos.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.basicappforinterview.R
import com.example.basicappforinterview.domain.model.Video

@Composable
fun VideoItem(
    modifier: Modifier = Modifier,
    video: Video,
    isFavorite: Boolean = false,
    onToggleFavorite: (Video) -> Unit = {},
    onClick: () -> Unit = {}
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(width = 100.dp, height = 70.dp)
                    .clip(RoundedCornerShape(8.dp)),
            ) {
                AsyncImage(
                    model = video.thumbnail,
                    contentDescription = "Thumbnail",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = video.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(
                onClick = { onToggleFavorite(video) }
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.favorites),
                    tint = if (isFavorite) Color.Red else Color.Gray
                )
            }
        }
    }
}

@Preview
@Composable
fun VideoItemPreview() {
    VideoItem(
        video = Video(
            id = 0,
            title = "This is a title",
            thumbnail = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png"
        ),
        isFavorite = true
    )
}