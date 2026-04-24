package com.example.letssopt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.letssopt.ui.theme.LETSSOPTTheme

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LETSSOPTTheme {
                MainScreen(mainViewModel)
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val uiState = viewModel.uiState
    MainScreenContent(
        selectedTabIndex = uiState.selectedTabIndex,
        onTabSelected = viewModel::selectTab
    )
}

@Composable
private fun MainScreenContent(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    val tabs = listOf(
        BottomNavItem("메인", Icons.Filled.Home),
        BottomNavItem("개별 구매", Icons.Filled.ShoppingBag),
        BottomNavItem("웹툰", Icons.Filled.GridView),
        BottomNavItem("찾기", Icons.Filled.Search),
        BottomNavItem("보관함", Icons.Filled.BookmarkBorder)
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212)),
        containerColor = Color(0xFF121212),
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF151515)) {
                tabs.forEachIndexed { index, tab ->
                    NavigationBarItem(
                        selected = selectedTabIndex == index,
                        onClick = { onTabSelected(index) },
                        icon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.label,
                                tint = if (selectedTabIndex == index) Color.White else Color.Gray
                            )
                        },
                        label = { Text(text = tab.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        TabListScreen(
            tabTitle = tabs[selectedTabIndex].label,
            innerPadding = innerPadding
        )
    }
}

private data class BottomNavItem(
    val label: String,
    val icon: ImageVector
)

@Composable
private fun TabListScreen(
    tabTitle: String,
    innerPadding: PaddingValues
) {
    val contents = List(20) { "${tabTitle} 아이템 ${it + 1}" }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(Color(0xFF121212))
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        item {
            Text(
                text = "$tabTitle 화면",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        items(contents) { item ->
            Text(
                text = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF232323))
                    .padding(18.dp),
                color = Color.White
            )
        }
    }
}

@Preview(
    name = "Main Screen Preview",
    showBackground = true,
    backgroundColor = 0xFF121212
)
@Composable
private fun MainScreenPreview() {
    LETSSOPTTheme {
        MainScreenContent(
            selectedTabIndex = 0,
            onTabSelected = {}
        )
    }
}

@Preview(
    name = "Tab List Preview",
    showBackground = true,
    backgroundColor = 0xFF121212
)
@Composable
private fun TabListScreenPreview() {
    LETSSOPTTheme {
        TabListScreen(
            tabTitle = "메인",
            innerPadding = PaddingValues()
        )
    }
}
