package com.geniusapk.shopping.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.geniusapk.shopping.presentation.viewModels.ShoppingAppViewModel
import com.geniusapk.shopping.ui.theme.SweetPink


@Preview(showBackground = true)
@Composable
fun HomeScreenUi(
    viewModel: ShoppingAppViewModel = hiltViewModel(),
) {
    val categoryState = viewModel.categoryInLimitScreenState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(key1 = true){
        viewModel.getCategoriesInLimited()
    }

    if (categoryState.value.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }else if (categoryState.value.errorMessage != null) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(text = categoryState.value.errorMessage!!)
    }
    }else if( categoryState.value.categories != null){








    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Search") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { /* Handle filter click */ }) {
                Icon(Icons.Default.Notifications, contentDescription = null)
            }
        }




        Column {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Categories", style = MaterialTheme.typography.bodySmall)
                Text("See more", color = SweetPink)
            }

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {




                items(categoryState.value.categories!!) { category ->
                    CategoryItem(
                        ImageUrl = category.categoryImage,
                        Category = category.name
                    )
                }
            }
        }
    }
    }
}







@Composable
fun CategoryItem(
    ImageUrl : String,
    Category : String,

) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(end = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(Color.LightGray, CircleShape)
        ){
            AsyncImage(
                model =ImageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)


            )
        }
        Text(Category, style = MaterialTheme.typography.bodyMedium)
    }
}