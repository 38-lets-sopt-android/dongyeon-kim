package com.example.letssopt.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class HomeUiState(
    val selectedTabIndex: Int = 0,
    val curatedPicks: List<PosterItem> = emptyList(),
    val upcomingPicks: List<PosterItem> = emptyList(),
    val partyPicks: List<PartyItem> = emptyList(),
    val heroPicks: List<HeroItem> = emptyList()
)

data class PosterItem(
    val title: String,
    val accentText: String = "",
    val colors: List<Long>,
    val isHighlighted: Boolean = false
)

data class PartyItem(
    val leftTitle: String,
    val rightTitle: String,
    val openTime: String,
    val hashTag: String,
    val leftColors: List<Long>,
    val rightColors: List<Long>,
    val hasAlarm: Boolean = false
)

data class HeroItem(
    val title: String,
    val subtitle: String,
    val colors: List<Long>
)

class HomeViewModel : ViewModel() {
    var uiState by mutableStateOf(
        HomeUiState(
            curatedPicks = curatedPicks,
            upcomingPicks = upcomingPicks,
            partyPicks = partyPicks,
            heroPicks = heroPicks
        )
    )
        private set

    fun selectTab(index: Int) {
        uiState = uiState.copy(selectedTabIndex = index)
    }
}

private val curatedPicks = listOf(
    PosterItem("이 사랑\n통역이\n되나요?", "NETFLIX", listOf(0xFFC8A47A, 0xFF3A2C26), true),
    PosterItem("STRANGER\nTHINGS 5", colors = listOf(0xFFB30010, 0xFF1B0507)),
    PosterItem("PROJECT\nHAIL\nMARY", colors = listOf(0xFF004F6D, 0xFF071621)),
    PosterItem("범죄도시\n비기닝", colors = listOf(0xFF6B331F, 0xFF1C0E0B)),
    PosterItem("화이트\n노이즈", colors = listOf(0xFF4D5B6B, 0xFF1D232C))
)

private val upcomingPicks = listOf(
    PosterItem("이 사랑\n통역이\n되나요?", colors = listOf(0xFFC8A47A, 0xFF3A2C26)),
    PosterItem("STRANGER\nTHINGS 5", colors = listOf(0xFFB30010, 0xFF1B0507)),
    PosterItem("PROJECT\nHAIL\nMARY", colors = listOf(0xFF004F6D, 0xFF071621)),
    PosterItem("미드나잇\n로맨스", colors = listOf(0xFF845B4D, 0xFF271B19)),
    PosterItem("인사이드\n더 월", colors = listOf(0xFF4D6179, 0xFF1B2534))
)

private val partyPicks = listOf(
    PartyItem("왕과 사는 남자", "왕자와 신하", "오늘 21:13에 시작", "# 왕과사는 남자", listOf(0xFF8D744A, 0xFF3D2F1F), listOf(0xFF5D8AB7, 0xFF1E293D), true),
    PartyItem("파묘", "헌트", "오늘 22:22에 시작", "# 파묘", listOf(0xFF5D8894, 0xFF1A2C35), listOf(0xFF43332F, 0xFF161010)),
    PartyItem("아포칼립스", "버티고", "오늘 23:10에 시작", "# 서바이벌", listOf(0xFF356783, 0xFF162634), listOf(0xFF4E2020, 0xFF1E0C0C)),
    PartyItem("런어웨이", "좀비 나이트", "내일 00:05에 시작", "# 액션", listOf(0xFF7F6338, 0xFF2F2418), listOf(0xFF0B5175, 0xFF0C2030)),
    PartyItem("심야식당", "마이 네임", "내일 00:50에 시작", "# 스릴러", listOf(0xFF866744, 0xFF302418), listOf(0xFF2E4C6B, 0xFF131F31))
)

private val heroPicks = listOf(
    HeroItem("트리거", "RETURNS", listOf(0xFF5F4331, 0xFF2F2620, 0xFF151316)),
    HeroItem("대홍수", "COMING SOON", listOf(0xFF1A4257, 0xFF132633, 0xFF0E1117)),
    HeroItem("콘크리트 타운", "NEW EPISODE", listOf(0xFF6D2D25, 0xFF2C1413, 0xFF121012)),
    HeroItem("블루 미러", "WATCH NOW", listOf(0xFF3A5477, 0xFF1C2A3E, 0xFF121218))
)
