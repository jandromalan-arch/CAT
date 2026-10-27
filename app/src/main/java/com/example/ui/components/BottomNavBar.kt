package com.example.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.testTag
import com.example.viewmodel.NavigationTab

@Composable
fun BottomNavBar(
    currentTab: NavigationTab,
    onTabSelected: (NavigationTab) -> Unit
) {
    NavigationBar(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.navigationBars)
            .testTag("bottom_nav_bar"),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp
    ) {
        val items = listOf(
            Triple(NavigationTab.DASHBOARD, Icons.Filled.Dashboard, Icons.Outlined.Dashboard),
            Triple(NavigationTab.CURRICULUM, Icons.Filled.MenuBook, Icons.Outlined.MenuBook),
            Triple(NavigationTab.ASSESSMENTS, Icons.Filled.Quiz, Icons.Outlined.Quiz),
            Triple(NavigationTab.VIDEOS, Icons.Filled.PlayCircle, Icons.Outlined.PlayCircleOutline),
            Triple(NavigationTab.FORUM, Icons.Filled.Forum, Icons.Outlined.Forum),
            Triple(NavigationTab.GUIDES, Icons.Filled.PictureAsPdf, Icons.Outlined.PictureAsPdf)
        )

        items.forEach { (tab, selectedIcon, unselectedIcon) ->
            val isSelected = currentTab == tab
            NavigationBarItem(
                selected = isSelected,
                onClick = { onTabSelected(tab) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) selectedIcon else unselectedIcon,
                        contentDescription = tab.label
                    )
                },
                label = { Text(text = tab.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.testTag("nav_tab_${tab.name.lowercase()}")
            )
        }
    }
}
