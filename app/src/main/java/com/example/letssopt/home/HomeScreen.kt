package com.example.letssopt.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.OndemandVideo
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.letssopt.ui.theme.LETSSOPTTheme

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val uiState = viewModel.uiState

    HomeScreenContent(
        uiState = uiState,
        onTabSelected = viewModel::selectTab
    )
}

@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
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
            .background(WatchaBackground),
        containerColor = WatchaBackground,
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF101014),
                tonalElevation = 0.dp
            ) {
                tabs.forEachIndexed { index, tab ->
                    NavigationBarItem(
                        selected = uiState.selectedTabIndex == index,
                        onClick = { onTabSelected(index) },
                        icon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.label,
                                tint = if (uiState.selectedTabIndex == index) Color.White else Color.Gray
                            )
                        },
                        label = {
                            Text(
                                text = tab.label,
                                color = if (uiState.selectedTabIndex == index) Color.White else Color.Gray,
                                fontSize = 11.sp
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
                    )
                }
            }
        }
    ) { innerPadding ->
        if (uiState.selectedTabIndex == 0) {
            WatchaMainTab(uiState = uiState, innerPadding = innerPadding)
        } else {
            TabListScreen(
                tabTitle = tabs[uiState.selectedTabIndex].label,
                innerPadding = innerPadding
            )
        }
    }
}

private data class BottomNavItem(
    val label: String,
    val icon: ImageVector
)

private val WatchaBackground = Color(0xFF090A0D)
private val WatchaMutedText = Color(0xFF8E9098)
private val WatchaPink = Color(0xFFFD2B77)
private val WatchaMint = Color(0xFF56F0D5)
private val PosterShape = RoundedCornerShape(14.dp)

@Composable
private fun WatchaMainTab(
    uiState: HomeUiState,
    innerPadding: PaddingValues
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(WatchaBackground),
        contentPadding = PaddingValues(top = 16.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { TopActionBar() }
        item { HeroText() }
        item { HeroStrip(items = uiState.heroPicks) }
        item { SectionHeader(title = "왓고리즘", subtitle = "예능부터 드라마까지!", showMore = true) }
        item { PosterRail(items = uiState.curatedPicks) }
        item { SectionHeader(title = "공개 예정 콘텐츠", showMore = true) }
        item { PosterRail(items = uiState.upcomingPicks) }
        item { SectionHeader(title = "왓챠 파티", showMore = true) }
        item { PartyRail(items = uiState.partyPicks) }
    }
}

@Composable
private fun TopActionBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(18.dp)) {
            Icon(Icons.Outlined.OndemandVideo, contentDescription = "watch", tint = Color.White, modifier = Modifier.size(27.dp))
            Icon(Icons.Outlined.NotificationsNone, contentDescription = "notification", tint = Color.White, modifier = Modifier.size(27.dp))
            Icon(Icons.Outlined.PersonOutline, contentDescription = "profile", tint = Color.White, modifier = Modifier.size(27.dp))
        }
    }
}

@Composable
private fun HeroText() {
    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text("방금 막 도착한 신상 콘텐츠", color = Color.White, fontSize = 17.sp, fontWeight = FontWeight.ExtraBold)
        Text("예능부터 드라마까지!", color = WatchaMutedText, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun HeroStrip(items: List<HeroItem>) {
    val initialHeroIndex = remember {
        val middle = Int.MAX_VALUE / 2
        middle - (middle % items.size)
    }
    val heroListState = rememberLazyListState(initialFirstVisibleItemIndex = initialHeroIndex)

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        state = heroListState,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        items(Int.MAX_VALUE) { index ->
            HeroMainCard(item = items[index % items.size])
        }
    }
}

@Composable
private fun HeroMainCard(item: HeroItem) {
    Box(
        modifier = Modifier
            .width(342.dp)
            .height(238.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Brush.verticalGradient(item.colors.toColors()))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0xCC0A0A0E)),
                        startY = 250f
                    )
                )
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(item.title, color = Color(0xFFE9E9EC), fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
            Text(item.subtitle, color = Color(0xFFCECFE0), fontSize = 12.sp, letterSpacing = 3.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    subtitle: String = "",
    showMore: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(title, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
            if (showMore) {
                Text("더보기", color = WatchaMutedText, fontSize = 12.sp)
            }
        }
        if (subtitle.isNotEmpty()) {
            Text(subtitle, color = WatchaMutedText, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun PosterRail(items: List<PosterItem>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        items(items) { item -> PosterCard(item = item) }
    }
}

@Composable
private fun PosterCard(item: PosterItem) {
    Box(
        modifier = Modifier
            .width(104.dp)
            .height(160.dp)
            .clip(PosterShape)
            .background(Brush.verticalGradient(item.colors.toColors()))
            .then(if (item.isHighlighted) Modifier.border(3.dp, WatchaMint, PosterShape) else Modifier)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0xCD101116)),
                        startY = 150f
                    )
                )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(item.title, color = Color(0xFFF1F2F4), fontSize = 10.sp, lineHeight = 12.sp, fontWeight = FontWeight.Bold)
            if (item.accentText.isNotEmpty()) {
                Text(item.accentText, color = WatchaPink, fontSize = 9.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun PartyRail(items: List<PartyItem>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        items(items) { item -> PartyCard(item = item) }
    }
}

@Composable
private fun PartyCard(item: PartyItem) {
    Box(
        modifier = Modifier
            .width(214.dp)
            .height(188.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(Color(0xFF111216))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
            ) {
                PartyPosterSlice(item.leftTitle, item.leftColors, Modifier.weight(1f))
                PartyPosterSlice(item.rightTitle, item.rightColors, Modifier.weight(1f))
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF202126))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(item.openTime, color = WatchaPink, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Text(item.hashTag, color = Color(0xFFF0F1F4), fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
            }
        }

        if (item.hasAlarm) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = 108.dp)
                    .size(34.dp)
                    .clip(RoundedCornerShape(17.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Notifications, contentDescription = "party alarm", tint = Color.Black, modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
private fun PartyPosterSlice(
    title: String,
    colors: List<Long>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors.toColors()))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0xD114151B)),
                        startY = 120f
                    )
                )
        )
        Text(
            text = title,
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(8.dp)
        )
    }
}

@Composable
private fun TabListScreen(
    tabTitle: String,
    innerPadding: PaddingValues
) {
    val contents = List(20) { "$tabTitle 아이템 ${it + 1}" }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(WatchaBackground)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        item {
            Text("$tabTitle 화면", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }

        items(contents) { item ->
            Text(
                text = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF202228))
                    .padding(18.dp),
                color = Color.White
            )
        }
    }
}

private fun List<Long>.toColors(): List<Color> = map { Color(it) }

@Preview(name = "Home Screen Preview", showBackground = true, backgroundColor = 0xFF0A0B0E)
@Composable
private fun HomeScreenPreview() {
    LETSSOPTTheme {
        HomeScreenContent(
            uiState = HomeUiState(
                curatedPicks = listOf(PosterItem("Preview", colors = listOf(0xFFC8A47A, 0xFF3A2C26))),
                upcomingPicks = listOf(PosterItem("Upcoming", colors = listOf(0xFFB30010, 0xFF1B0507))),
                partyPicks = listOf(PartyItem("Left", "Right", "오늘 21:13에 시작", "# preview", listOf(0xFF8D744A, 0xFF3D2F1F), listOf(0xFF5D8AB7, 0xFF1E293D))),
                heroPicks = listOf(HeroItem("트리거", "RETURNS", listOf(0xFF5F4331, 0xFF2F2620, 0xFF151316)))
            ),
            onTabSelected = {}
        )
    }
}
