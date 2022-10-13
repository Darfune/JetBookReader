package com.example.jetbookreader.screens.details

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.jetbookreader.data.Resource
import com.example.jetbookreader.model.MBook
import com.example.jetbookreader.model.googleapimodel.Item
import com.example.jetbookreader.navigation.ReaderScreens
import com.example.jetbookreader.screens.splash.ReaderAppBar
import com.example.jetbookreader.screens.splash.RoundedButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@Composable
fun BookDetailsScreen(
    navController: NavController, bookId: String,
    viewModel: DetailsScreenViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        ReaderAppBar(
            title = "Book Details",
            icon = Icons.Default.ArrowBack,
            showProfile = false,
            navController = navController
        ) {
            navController.navigate(ReaderScreens.SearchScreen.name)
        }
    }) {
        Surface(
            modifier = Modifier
                .padding(3.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(top = 12.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val bookInfo = produceState<Resource<Item>>(initialValue = Resource.Loading()) {
                    value = viewModel.getBookInfo(bookId)
                }.value
                Log.d("Deets", "BookDetailsScreen: ${bookInfo.data.toString()}")
                if (bookInfo.data == null) {
                    Row() {
                        LinearProgressIndicator()
                        Text(text = "Loading...")
                    }
                } else
                    ShowBookDetails(bookInfo, navController)
            }
        }
    }
}

@Composable
fun ShowBookDetails(bookInfo: Resource<Item>, navController: NavController) {
    val bookData = bookInfo.data?.volumeInfo
    val googleBookId = bookInfo.data?.id

    Card(
        modifier = Modifier.padding(34.dp),
        shape = CircleShape,
        elevation = 4.dp
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = bookData!!.imageLinks.thumbnail.toString()),
            contentDescription = "Book image",
            modifier = Modifier
                .width(90.dp)
                .height(90.dp)
                .padding(1.dp)
        )
    }
    Text(
        text = bookData!!.title,
        style = MaterialTheme.typography.h6,
        maxLines = 19,
        overflow = TextOverflow.Ellipsis
    )
    Text(
        text = "Authors: ${bookData.authors.toString()}",
        style = MaterialTheme.typography.body1
    )
    Text(
        text = "Page Count: ${bookData.pageCount.toString()}",
        style = MaterialTheme.typography.body1
    )
    Text(
        text = "Categories: ${bookData.categories.toString()}",
        style = MaterialTheme.typography.subtitle1,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis
    )
    Text(
        text = "Published: ${bookData.publishedDate.toString()}",
        style = MaterialTheme.typography.subtitle1
    )
    Spacer(modifier = Modifier.height(5.dp))
    val cleanDescription = HtmlCompat.fromHtml(
        bookData!!.description,
        HtmlCompat.FROM_HTML_MODE_LEGACY
    )
    val localDims = LocalContext.current.resources.displayMetrics
    Surface(
        modifier = Modifier
            .height(localDims.heightPixels.dp.times(0.09f))
            .padding(4.dp),
        shape = RectangleShape,
        border = BorderStroke(1.dp, Color.DarkGray)
    ) {
        LazyColumn(modifier = Modifier.padding(3.dp)) {
            item {
                Text(text = cleanDescription.toString())
            }
        }
    }
    Row(
        modifier = Modifier.padding(top = 6.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        RoundedButton(label = "Save", radius = 29) {
            val book = MBook(
                title = bookData.title.toString(),
                authors = bookData.authors.toString(),
                description = bookData.description.toString(),
                categories = bookData.categories.toString(),
                notes = "", // bookData.title.toString(),
                photoUrl = bookData.imageLinks.thumbnail.toString(),
                publishedDate = bookData.publishedDate.toString(),
                pageCount = bookData.pageCount.toString(),
                rating = 0.0, // bookData.title.toString(),
                googleBookId = googleBookId,
                userId = FirebaseAuth.getInstance().currentUser?.uid.toString()

            )
            saveToFireBase(book, navController)
        }
        Spacer(modifier = Modifier.width(30.dp))
        RoundedButton(label = "Cancel", radius = 29) {
            navController.popBackStack()
        }
    }
}

fun saveToFireBase(book: MBook, navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    val dbCollection = db.collection("books")
    if (book.toString().isNotEmpty()) {
        dbCollection.add(book)
            .addOnSuccessListener { documentRef ->
                dbCollection.document(documentRef.id)
                    .update(hashMapOf("id" to documentRef.id) as Map<String, Any>)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            navController.popBackStack()
                        }
                    }
                    .addOnFailureListener {
                        Log.w("Error", "SaveToFireBase: Error updating doc")
                    }
            }
    } else {

    }
}
