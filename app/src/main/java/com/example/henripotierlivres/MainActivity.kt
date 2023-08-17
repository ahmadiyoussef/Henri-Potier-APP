package com.example.henripotierlivres

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import coil.compose.rememberImagePainter
import com.example.henripotierlivres.api.RetrofitInstance
import com.example.henripotierlivres.models.BooksResponse
import com.example.henripotierlivres.repository.BooksRepository
import com.example.henripotierlivres.ui.theme.HenriPotierLivresTheme
import com.example.henripotierlivres.util.Resource

class MainActivity : ComponentActivity() {

    lateinit var viewModel : BooksViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val booksRepository = BooksRepository(RetrofitInstance.api)
        val viewModelProviderFactory = BooksViewModelProviderFactory(booksRepository)
        viewModel = ViewModelProvider(
            this, viewModelProviderFactory
        ).get(BooksViewModel::class.java)
        setContent {
            HenriPotierLivresTheme {
             App(bookViewModel = viewModel)
            }
        }
    }
}


@Composable
fun BookList(modifier: Modifier, books: List<BooksResponse>, addToCart: (BooksResponse) -> Unit, selectedBooks: MutableList<BooksResponse>) {
    LazyColumn(
        modifier = modifier
    ) {
        items(books) { book ->
            BookItem(book, addToCart, selectedBooks)

        }
    }
}

@Composable
fun BookItem(book: BooksResponse , addToCart: (BooksResponse) -> Unit, selectedBooks: MutableList<BooksResponse>) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Display book cover image
            Image(
                rememberImagePainter(
                    data = book.cover),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(MaterialTheme.shapes.medium)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Checkbox(
                checked = book.isSelected,
                onCheckedChange = {
                    book.isSelected = it
                    if (it) selectedBooks.add(book)
                    else selectedBooks.remove(book)
                }
            )

            Column {
                Text(
                    text = book.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "$${book.price}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Synopsis: ${book.synopsis.joinToString(", ").take(50)}",
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { addToCart(book) }) {
                    Text(text = "Ajouter au panier")
                }
            }
        }
    }


}

@Composable
fun App(bookViewModel: BooksViewModel) {

    val books = bookViewModel.books.observeAsState()
    var cart by remember { mutableStateOf(emptyList<BooksResponse>()) }
    val selectedBooks = remember { mutableStateListOf<BooksResponse>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when(books.value){
            is Resource.Error -> Unit
            is Resource.Loading -> Unit
            is Resource.Success -> {
                BookList(modifier = Modifier.weight(1f),  books = books.value!!.data!!, { book ->
                    cart = cart + book
                }, selectedBooks)
                Spacer(modifier = Modifier.height(16.dp))
                CartSummary(cart = cart)
            }
            null -> Unit
        }

    }
}



@Composable
fun CartSummary(cart: List<BooksResponse>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Récap du Panier",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(text = "Articles: ${cart.size}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Prix total: $${cart.sumOf { it.price }}")
    }
}








