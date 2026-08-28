package dev.koalit.powersave.ejercicios

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.koalit.powersave.R
import dev.koalit.powersave.ui.theme.PowerSaveTheme

@Composable
fun ChatItem(
    name: String,
    message: String,
    time: String,
    image: String,
    isPinned: Boolean,
    hasHighlight: Boolean,
    unreadMessages: Int,
    onChatClick: () -> Unit,
    onHighlightClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val timeStyle = if (unreadMessages > 0) {
        MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.Bold
        )
    } else {
        MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.Light
        )
    }
    val timeColor = if (unreadMessages > 0) {
        MaterialTheme.colorScheme.onSecondaryContainer
    } else {
        MaterialTheme.colorScheme.onSurface
    }
    Row(
        modifier = modifier
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box() {
            Surface(
                modifier = Modifier
                    .size(48.dp)
                    .then(
                        if (hasHighlight) {
                            Modifier.border(
                                width = 3.dp,
                                color = MaterialTheme.colorScheme.tertiaryContainer,
                                shape = CircleShape
                            )
                        } else {
                            Modifier
                        }
                    )
                    ,
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer
            ) { }
        }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = time,
                    style = timeStyle,
                    color = timeColor
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium
                )
                Row (
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (isPinned) {
                        Icon(
                            painter = painterResource(R.drawable.ic_inclusive),
                            contentDescription = "Chat pineado"
                        )
                    }
                    if (unreadMessages > 0) {
                        Surface(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = CircleShape,
                            modifier = Modifier.size(24.dp)
                        ) {
                            Box {
                                Text(
                                    text = unreadMessages.toString(),
                                    modifier = Modifier.align(Alignment.Center),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun PreviewChatItem() {
    PowerSaveTheme {
        Surface {
            ChatItem(
                name = "Juan Carlos",
                message = "Mañana nos vamos",
                time = "3:00 AM",
                isPinned = true,
                unreadMessages = 3,
                image = "",
                hasHighlight = true,
                onChatClick = {},
                onHighlightClick = {}
            )
        }
    }
}

data class ChatEntity(
    val name: String,
    val message: String,
    val time: String,
    val image: String,
    val isPinned: Boolean,
    val hasHighlight: Boolean,
    val unreadMessages: Int
)


@Composable
private fun PreviewChatList() {
    val mockChats = listOf(
        ChatEntity(
            name = "Juan Carlos",
            message = "Responde tu pinche telefono",
            time = "12:00 PM",
            image = "",
            isPinned = true,
            hasHighlight = false,
            unreadMessages = 9
        ),
        ChatEntity(
            name = "María Elena",
            message = "¿Vas a ir por Mike?",
            time = "2:10 PM",
            image = "",
            isPinned = false,
            hasHighlight = true,
            unreadMessages = 1
        ),
        ChatEntity(
            name = "Alessandro",
            message = "vas a venir?",
            time = "9:21 AM",
            image = "",
            isPinned = false,
            hasHighlight = false,
            unreadMessages = 0
        )
    )
    PowerSaveTheme {
        Surface {
            LazyColumn() {
                items(mockChats) { chat ->
                    ChatItem(
                        name = chat.name,
                        message = chat.message,
                        time = chat.time,
                        image = chat.image,
                        isPinned = chat.isPinned,
                        hasHighlight = chat.hasHighlight,
                        unreadMessages = chat.unreadMessages,
                        onChatClick = {},
                        onHighlightClick = {}
                    )
                }
            }
        }
    }
}

@Composable
fun NotificationsRoute(
    modifier: Modifier = Modifier
) {
    // Aqui se obtiene la informacion
    val hasNotificationsEnabled = true
    val notificationCount = 8
    NotificationsScreen(
        hasNotificationsEnabled = hasNotificationsEnabled,
        notificationCount = notificationCount,
        modifier = modifier
    )
}

@Composable
private fun NotificationsScreen(
    hasNotificationsEnabled: Boolean,
    notificationCount: Int,
    modifier: Modifier = Modifier
) {
    // Mostramos la informacion
    if (!hasNotificationsEnabled) {
        DisabledNotificationsScreenState(modifier)
    } else {
        if (notificationCount == 0) {
            EmptyScreenState(modifier)
        } else {
            MultipleNotificationScreenState(
                notificationCount = notificationCount,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun EmptyScreenState(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_inclusive),
            contentDescription = null,
            modifier = Modifier.size(96.dp)
        )
        Text(
            text = "Sin notificaciones",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = "No tienes ninguna notificación, regresa más tarde",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun DisabledNotificationsScreenState(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_apartment),
            contentDescription = null,
            modifier = Modifier.size(96.dp)
        )
        Text(
            text = "Notificaciones desactivadas",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = "Parece que no tienes activas tus notificaciones",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        FilledTonalButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {}
        ) {
            Text("Activar notificaciones")
        }
    }
}

@Composable
private fun MultipleNotificationScreenState(
    notificationCount: Int,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = notificationCount.toString(),
            style = MaterialTheme.typography.displayLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = "Con notificaciones",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Preview
@Composable
private fun PreviewMultipleNotificationsScreenState() {
    PowerSaveTheme {
        Surface {
            NotificationsScreen(
                hasNotificationsEnabled = true,
                notificationCount = 10,
                modifier = Modifier.fillMaxSize().padding(16.dp)
            )
        }
    }
}