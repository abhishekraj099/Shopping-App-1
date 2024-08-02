package com.geniusapk.shopping.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.geniusapk.shopping.domain.models.BannerDataModels
import com.geniusapk.shopping.domain.models.CategoryDataModels
import com.geniusapk.shopping.domain.models.ProductDataModels
import com.geniusapk.shopping.presentation.viewModels.ShoppingAppViewModel
import com.geniusapk.shopping.ui.theme.SweetPink

@Preview(showBackground = true)
@Composable
fun HomeScreenUi(
    viewModel: ShoppingAppViewModel = hiltViewModel(),
) {
    val homeState by viewModel.homeScreenState.collectAsStateWithLifecycle()

    if (homeState.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    } else if (homeState.errorMessage != null) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(text = homeState.errorMessage!!)
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            SearchBar()
            homeState.categories?.let { CategoriesSection(it) }
           // homeState.banners?.let { BannerSection() }
            BannerSection()
            homeState.products?.let { FlashSaleSection(it) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar() {
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
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        IconButton(onClick = { /* Handle notification click */ }) {
            Icon(Icons.Default.Notifications, contentDescription = null)
        }
    }
}

@Composable
fun CategoriesSection(categories: List<CategoryDataModels>) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Categories", style = MaterialTheme.typography.titleMedium)
            Text("See more", color = SweetPink, style = MaterialTheme.typography.bodyMedium)
        }
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(categories) { category ->
                CategoryItem(
                    ImageUrl = category.categoryImage,
                    Category = category.name
                )
            }
        }
    }
}

@Composable
fun CategoryItem(
    ImageUrl: String,
    Category: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(end = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(Color.LightGray, CircleShape)
        ) {
            AsyncImage(
                model = ImageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
        }
        Text(Category, style = MaterialTheme.typography.bodyMedium)
    }
}


@Composable
fun BannerSection() {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(vertical = 16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(5) { banner ->
            AsyncImage(
                model = "https://picsum.photos/250/200",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillParentMaxHeight()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}

@Composable
fun FlashSaleSection(products: List<ProductDataModels>) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Flash Sale", style = MaterialTheme.typography.titleMedium)
            Text("See more", color = SweetPink, style = MaterialTheme.typography.bodyMedium)
        }
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(products) { product ->
                ProductCard(product)
            }
        }
    }
}

@Composable
fun ProductCard(product: ProductDataModels) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .aspectRatio(0.7f),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            AsyncImage(
                model = product.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(product.name, style = MaterialTheme.typography.bodyMedium)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "₹${product.price}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "₹${product.price}",
                        style = MaterialTheme.typography.bodySmall,
                        textDecoration = TextDecoration.LineThrough,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

