package org.chiuxah.fakebili.ui.main

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
//import io.kamel.image.KamelImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.chiuxah.fakebili.logic.network.api.ApiResult
import org.chiuxah.fakebili.logic.network.bean.SearchBean
import org.chiuxah.fakebili.logic.utils.MultiPlatUtils
import org.chiuxah.fakebili.ui.utils.RowHorizal
import org.chiuxah.fakebili.viewmodel.NetworkViewModel
import androidx.compose.runtime.*
import androidx.compose.foundation.Image
import com.seiko.imageloader.rememberImagePainter
import org.chiuxah.fakebili.logic.network.bean.SearchResponse

//import io.kamel.image.KamelImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
//    val text = Encrypt.getBiliTicket()
//    var ticket by remember { mutableStateOf("") }
//
//    CoroutineScope(Job()).launch {
//        ticket = NetWork.parseJson<BiliTicketResponse>(text).data.ticket
//    }
    var expanded by rememberSaveable { mutableStateOf(false) }
    var queryStr by rememberSaveable { mutableStateOf("") }

    val scale by animateFloatAsState(
        targetValue = if(expanded) 1f else 0f,
        animationSpec = tween(durationMillis = 500),
        label = ""
    )
    var loading by remember { mutableStateOf(true) }


    val networkViewModel = remember { NetworkViewModel() }
    val searchState by networkViewModel.searchState.collectAsState()
    LaunchedEffect(Unit) {
        launch {
            async { networkViewModel.fetchBiliTicket() }.await()
            async {
                launch(Dispatchers.Main) {
                    networkViewModel.ticketState.collect { result ->
                        when(result) {
                            is ApiResult.Success -> {
                                 networkViewModel.ticket = result.data.data.ticket
                            }
                            is ApiResult.Error -> {
                                println("错误")
                            }
                        }
                    }
                }
            }
        }
    }

    MaterialTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text("Fake Bili")
                    }
                )
            }
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {

                RowHorizal {
                    SearchBar(
                        modifier = Modifier.weight(1f).padding(15.dp),
                        inputField = {
                            SearchBarDefaults.InputField(
                                onSearch = { expanded = false },
                                expanded = expanded,
                                onExpandedChange = { expanded = it },
                                placeholder = { Text("搜索哔哩哔哩视频") },
                                trailingIcon = {
                                    IconButton(
                                        onClick = { expanded = !expanded }
                                    ) {
                                        if(expanded) {
                                            Icon(Icons.Filled.Close, contentDescription = null)
                                        } else {
                                            Icon(Icons.Filled.Search, contentDescription = null)
                                        }
                                    }
                                },
                                query = queryStr,
                                onQueryChange = { queryStr = it },
                            )
                        },
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        shape = RoundedCornerShape(10.dp),
                        content = {
                            if(expanded && queryStr != "") {

                                //先显示加载，执行网络请求，然后解析，解析好后在这里显示
//                                LaunchedEffect(Unit) {
//                                    launch {
//                                        async {
//                                            loading = true
//                                            networkViewModel.searchVideos(queryStr)
//                                        }.await()
//                                        async {
//                                            launch(Dispatchers.Main) {
//                                                networkViewModel.searchState.collect { result ->
//                                                    when(result) {
//                                                        is ApiResult.Success -> {
//                                                            val resList = result.data.data.result
//                                                            networkViewModel.videoSearchList =  resList.last().data
//                                                            loading = false
//                                                        }
//                                                        is ApiResult.Error -> {
//                                                            println("错误")
//                                                        }
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
                                LaunchedEffect(queryStr) {
                                    loading = true
                                    networkViewModel.searchVideos(queryStr)
                                }
                                when (searchState) {
                                    is ApiResult.Success -> {
                                        val resList = (searchState as ApiResult.Success<SearchResponse>).data.data.result
                                        networkViewModel.videoSearchList = resList.last().data
                                        loading = false
                                    }
                                    is ApiResult.Error -> {
                                        println("错误")
                                        loading = false
                                    }
                                    else -> {
                                        // Idle 或加载中状态
                                    }
                                }

                                if(loading) {
                                    Box(modifier = Modifier.fillMaxSize()) {
                                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                                    }
                                } else {
                                    val data = getSearchList(networkViewModel)
                                    LazyColumn {
                                        items(data.size) { index ->
                                            val item = data[index]
                                            ListItem(
                                                headlineContent = {
                                                    Text(item.title)
                                                },
                                                overlineContent = {
                                                    item.tag?.let { Text(it) }
                                                },
                                                supportingContent = {
                                                    Text("UP主 " + item.author + " | 时长 ${item.duration}")
                                                },
                                                modifier = Modifier.clickable {
                                                    MultiPlatUtils().startUrl(item.arcurl)
                                                },
                                                leadingContent = {
                                                    val painter = item.imgUrl?.let { rememberImagePainter("https:$it") }
                                                    painter?.let {
                                                        Image(
                                                            painter = it,
                                                            contentDescription = null,
                                                            modifier = Modifier.size(120.dp)
                                                        )
                                                    }
                                                }
                                            )
                                            Divider()
                                        }
                                    }
                                }
                            } else {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    Icon(
                                        Icons.Filled.Search,
                                        null,
                                        modifier = Modifier
                                            .size(100.dp)
                                            .align(Alignment.Center)
                                            .scale(scale)
                                        ,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun SearchList() {

}

fun getSearchList(networkViewModel: NetworkViewModel): List<SearchBean> {
    val data = networkViewModel.videoSearchList
    try {
        for (item in data) {
            // 假设 item.title 是包含 HTML 标签的字符串
            item.title =  item.title
                .replace("</em>", "")
                .replace("<em class=\"keyword\">", "")
        }
    } catch (e: Exception) {
        // 捕获异常（如果有的话），可以进行日志记录或其他操作
        e.printStackTrace()
    }
    return data
}
