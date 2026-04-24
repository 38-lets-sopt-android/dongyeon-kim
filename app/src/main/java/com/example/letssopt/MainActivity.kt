package com.example.letssopt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
            .background(WatchaBackground),
        containerColor = WatchaBackground,
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF101014),
                tonalElevation = 0.dp
            ) {
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
                        label = {
                            Text(
                                text = tab.label,
                                color = if (selectedTabIndex == index) Color.White else Color.Gray,
                                fontSize = 11.sp
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        if (selectedTabIndex == 0) {
            WatchaMainTab(innerPadding = innerPadding)
        } else {
            TabListScreen(
                tabTitle = tabs[selectedTabIndex].label,
                innerPadding = innerPadding
            )
        }
    }
}

private data class BottomNavItem(
    val label: String,
    val icon: ImageVector
)

private data class PosterItem(
    val title: String,
    val accentText: String = "",
    val colors: List<Color>,
    val isHighlighted: Boolean = false
)

private data class PartyItem(
    val leftTitle: String,
    val rightTitle: String,
    val openTime: String,
    val hashTag: String,
    val leftColors: List<Color>,
    val rightColors: List<Color>,
    val hasAlarm: Boolean = false
)

private data class HeroItem(
    val title: String,
    val subtitle: String,
    val colors: List<Color>
)

private val WatchaBackground = Color(0xFF090A0D)
private val WatchaMutedText = Color(0xFF8E9098)
private val WatchaPink = Color(0xFFFD2B77)
private val WatchaMint = Color(0xFF56F0D5)
private val PosterShape = RoundedCornerShape(14.dp)

private val curatedPicks = listOf(
    PosterItem(
        title = "이 사랑\n통역이\n되나요?",
        accentText = "NETFLIX",
        colors = listOf(Color(0xFFC8A47A), Color(0xFF3A2C26)),
        isHighlighted = true
    ),
    PosterItem(
        title = "STRANGER\nTHINGS 5",
        colors = listOf(Color(0xFFB30010), Color(0xFF1B0507))
    ),
    PosterItem(
        title = "PROJECT\nHAIL\nMARY",
        colors = listOf(Color(0xFF004F6D), Color(0xFF071621))
    ),
    PosterItem(
        title = "범죄도시\n비기닝",
        colors = listOf(Color(0xFF6B331F), Color(0xFF1C0E0B))
    ),
    PosterItem(
        title = "화이트\n노이즈",
        colors = listOf(Color(0xFF4D5B6B), Color(0xFF1D232C))
    )
)

private val upcomingPicks = listOf(
    PosterItem(
        title = "이 사랑\n통역이\n되나요?",
        colors = listOf(Color(0xFFC8A47A), Color(0xFF3A2C26))
    ),
    PosterItem(
        title = "STRANGER\nTHINGS 5",
        colors = listOf(Color(0xFFB30010), Color(0xFF1B0507))
    ),
    PosterItem(
        title = "PROJECT\nHAIL\nMARY",
        colors = listOf(Color(0xFF004F6D), Color(0xFF071621))
    ),
    PosterItem(
        title = "미드나잇\n로맨스",
        colors = listOf(Color(0xFF845B4D), Color(0xFF271B19))
    ),
    PosterItem(
        title = "인사이드\n더 월",
        colors = listOf(Color(0xFF4D6179), Color(0xFF1B2534))
    )
)

private val partyPicks = listOf(
    PartyItem(
        leftTitle = "왕과 사는 남자",
        rightTitle = "왕자와 신하",
        openTime = "오늘 21:13에 시작",
        hashTag = "# 왕과사는 남자",
        leftColors = listOf(Color(0xFF8D744A), Color(0xFF3D2F1F)),
        rightColors = listOf(Color(0xFF5D8AB7), Color(0xFF1E293D)),
        hasAlarm = true
    ),
    PartyItem(
        leftTitle = "파묘",
        rightTitle = "헌트",
        openTime = "오늘 22:22에 시작",
        hashTag = "# 파묘",
        leftColors = listOf(Color(0xFF5D8894), Color(0xFF1A2C35)),
        rightColors = listOf(Color(0xFF43332F), Color(0xFF161010))
    ),
    PartyItem(
        leftTitle = "아포칼립스",
        rightTitle = "버티고",
        openTime = "오늘 23:10에 시작",
        hashTag = "# 서바이벌",
        leftColors = listOf(Color(0xFF356783), Color(0xFF162634)),
        rightColors = listOf(Color(0xFF4E2020), Color(0xFF1E0C0C))
    ),
    PartyItem(
        leftTitle = "런어웨이",
        rightTitle = "좀비 나이트",
        openTime = "내일 00:05에 시작",
        hashTag = "# 액션",
        leftColors = listOf(Color(0xFF7F6338), Color(0xFF2F2418)),
        rightColors = listOf(Color(0xFF0B5175), Color(0xFF0C2030))
    ),
    PartyItem(
        leftTitle = "심야식당",
        rightTitle = "마이 네임",
        openTime = "내일 00:50에 시작",
        hashTag = "# 스릴러",
        leftColors = listOf(Color(0xFF866744), Color(0xFF302418)),
        rightColors = listOf(Color(0xFF2E4C6B), Color(0xFF131F31))
    )
)

private val heroPicks = listOf(
    HeroItem(
        title = "트리거",
        subtitle = "RETURNS",
        colors = listOf(Color(0xFF5F4331), Color(0xFF2F2620), Color(0xFF151316))
    ),
    HeroItem(
        title = "대홍수",
        subtitle = "COMING SOON",
        colors = listOf(Color(0xFF1A4257), Color(0xFF132633), Color(0xFF0E1117))
    ),
    HeroItem(
        title = "콘크리트 타운",
        subtitle = "NEW EPISODE",
        colors = listOf(Color(0xFF6D2D25), Color(0xFF2C1413), Color(0xFF121012))
    ),
    HeroItem(
        title = "블루 미러",
        subtitle = "WATCH NOW",
        colors = listOf(Color(0xFF3A5477), Color(0xFF1C2A3E), Color(0xFF121218))
    )
)

@Composable
private fun WatchaMainTab(innerPadding: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(WatchaBackground),
        contentPadding = PaddingValues(top = 16.dp, bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TopActionBar()
        }
        item {
            HeroText()
        }
        item {
            HeroStrip()
        }
        item {
            SectionHeader(
                title = "왓고리즘",
                subtitle = "예능부터 드라마까지!",
                showMore = true
            )
        }
        item {
            PosterRail(items = curatedPicks)
        }
        item {
            SectionHeader(
                title = "공개 예정 콘텐츠",
                showMore = true
            )
        }
        item {
            PosterRail(items = upcomingPicks)
        }
        item {
            SectionHeader(
                title = "왓챠 파티",
                showMore = true
            )
        }
        item {
            PartyRail(items = partyPicks)
        }
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
            Icon(
                imageVector = Icons.Outlined.OndemandVideo,
                contentDescription = "watch",
                tint = Color.White,
                modifier = Modifier.size(27.dp)
            )
            Icon(
                imageVector = Icons.Outlined.NotificationsNone,
                contentDescription = "notification",
                tint = Color.White,
                modifier = Modifier.size(27.dp)
            )
            Icon(
                imageVector = Icons.Outlined.PersonOutline,
                contentDescription = "profile",
                tint = Color.White,
                modifier = Modifier.size(27.dp)
            )
        }
    }
}

@Composable
private fun HeroText() {
    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = "방금 막 도착한 신상 콘텐츠",
            color = Color.White,
            fontSize = 17.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            text = "예능부터 드라마까지!",
            color = WatchaMutedText,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun HeroStrip() {
    val initialHeroIndex = remember {
        val middle = Int.MAX_VALUE / 2
        middle - (middle % heroPicks.size)
    }
    val heroListState = rememberLazyListState(
        initialFirstVisibleItemIndex = initialHeroIndex
    )

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        state = heroListState,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        items(Int.MAX_VALUE) { index ->
            HeroMainCard(item = heroPicks[index % heroPicks.size])
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
            .background(
                Brush.verticalGradient(
                    item.colors
                )
            )
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
            Text(
                text = item.title,
                color = Color(0xFFE9E9EC),
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = item.subtitle,
                color = Color(0xFFCECFE0),
                fontSize = 12.sp,
                letterSpacing = 3.sp,
                fontWeight = FontWeight.SemiBold
            )
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
            Text(
                text = title,
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.ExtraBold
            )
            if (showMore) {
                Text(
                    text = "더보기",
                    color = WatchaMutedText,
                    fontSize = 12.sp
                )
            }
        }
        if (subtitle.isNotEmpty()) {
            Text(
                text = subtitle,
                color = WatchaMutedText,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
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
        items(items) { item ->
            PosterCard(item = item)
        }
    }
}

@Composable
private fun PosterCard(item: PosterItem) {
    Box(
        modifier = Modifier
            .width(104.dp)
            .height(160.dp)
            .clip(PosterShape)
            .background(Brush.verticalGradient(item.colors))
            .then(
                if (item.isHighlighted) {
                    Modifier.border(3.dp, WatchaMint, PosterShape)
                } else {
                    Modifier
                }
            )
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
            Text(
                text = item.title,
                color = Color(0xFFF1F2F4),
                fontSize = 10.sp,
                lineHeight = 12.sp,
                fontWeight = FontWeight.Bold
            )
            if (item.accentText.isNotEmpty()) {
                Text(
                    text = item.accentText,
                    color = WatchaPink,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                )
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
        items(items) { item ->
            PartyCard(item = item)
        }
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
                PartyPosterSlice(
                    title = item.leftTitle,
                    colors = item.leftColors,
                    modifier = Modifier.weight(1f)
                )
                PartyPosterSlice(
                    title = item.rightTitle,
                    colors = item.rightColors,
                    modifier = Modifier.weight(1f)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF202126))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = item.openTime,
                    color = WatchaPink,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = item.hashTag,
                    color = Color(0xFFF0F1F4),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold
                )
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
                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = "party alarm",
                    tint = Color.Black,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
private fun PartyPosterSlice(
    title: String,
    colors: List<Color>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors))
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
                    .background(Color(0xFF202228))
                    .padding(18.dp),
                color = Color.White
            )
        }
    }
}

@Preview(
    name = "Main Screen Preview",
    showBackground = true,
    backgroundColor = 0xFF0A0B0E
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
    backgroundColor = 0xFF0A0B0E
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

@Preview(
    name = "Watcha Main Preview",
    showBackground = true,
    backgroundColor = 0xFF0A0B0E
)
@Composable
private fun WatchaMainPreview() {
    LETSSOPTTheme {
        WatchaMainTab(innerPadding = PaddingValues())
    }
}
