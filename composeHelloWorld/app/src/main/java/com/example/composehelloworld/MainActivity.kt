package com.example.composehelloworld

import android.content.ClipData.Item
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.composehelloworld.ui.theme.ComposeHelloWorldTheme
import kotlinx.coroutines.isActive

class MainActivity : ComponentActivity() {
    val TAG = "MainActivity"
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        super.onCreate(savedInstanceState)
        setContent {

            ComposeHelloWorldTheme {
                Box (Modifier.fillMaxSize(), Alignment.BottomEnd){
                    LazyList()
                }
            }
        }
    }


    @Composable
    fun LazyList() {
        val scrollState = rememberLazyListState()
        val context = LocalContext.current
        val texts = remember { mutableStateOf(listOf<ContentDataModel>()) }
        viewModel.dataList.observe(this) { item ->
            Log.d(TAG, item.toString())
            texts.value = item
        }
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
            LazyColumn(state = scrollState, horizontalAlignment = Alignment.CenterHorizontally, userScrollEnabled = true) {
                items(texts.value.size){
                        index ->
                    ItemCompose(texts.value[index].data, index)
                }


            }
        }
        Box(contentAlignment = Alignment.BottomStart){
            SmallFloatingActionButton(
                onClick = {
                    Toast("FAB", context)

                    viewModel.addItem("test")
                    Log.d(TAG, "${texts.value}")
                },
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(all = 24.dp)
            ) {
                Icon(Icons.Filled.Add, "Small floating action button.")
            }
        }
    }
    @Composable
    fun CreateFAB() {
        val context = LocalContext.current
        SmallFloatingActionButton(
            onClick = {
                Toast("FAB", context)
                      },
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .padding(all = 24.dp)
        ) {
            Icon(Icons.Filled.Add, "Small floating action button.")
        }
    }

    @Composable
    fun ItemCompose(data: String, index : Int) {
        val context = LocalContext.current
        Box(
            Modifier
                .fillMaxWidth()
                .background(Color(209, 191, 241, 255)),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable() {

            }) {
                Checkbox(checked = false, onCheckedChange = {
                    Toast(data, context)
                    viewModel.delItem(index)

                })
                Text(text = "index : $data")

            }
        }
    }


    fun Toast(massage: String, context: Context) {
        Toast.makeText(context, massage, Toast.LENGTH_SHORT).show()
    }


}