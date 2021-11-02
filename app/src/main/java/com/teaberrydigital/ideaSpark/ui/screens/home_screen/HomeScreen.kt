package com.teaberrydigital.ideaSpark.screens.home_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.teaberrydigital.ideaSpark.MainActivityViewModel
import com.teaberrydigital.ideaSpark.R
import com.teaberrydigital.ideaSpark.screens.navigation_drawer.NavDrawer
import com.teaberrydigital.ideaSpark.ui.screens.all_ideas_screen.ExpandableCard
import com.teaberrydigital.ideaSpark.ui.theme.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


@ExperimentalMaterialApi
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyHomeScreen(navigation: NavHostController?, viewModel: MainActivityViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {

    val currentDateTime = LocalDateTime.now()
    val state = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val result by viewModel.readAllData.observeAsState(initial = emptyList())
    val favouriteResults by viewModel.readAllFavourites.observeAsState(initial = emptyList())


    Scaffold(
        scaffoldState = state,
        topBar= {
            AppBarRightIcon(title = "", icon = Icons.Default.Menu, description = "App Bar" ) {
                coroutineScope.launch { state.drawerState.open() }
            }
        },

        drawerShape = RoundedCornerShape(topEnd = 24.dp,bottomEnd = 24.dp),

        drawerContent = {
            NavDrawer(state,coroutineScope,navigation)
        },

        bottomBar={

        },
        modifier = Modifier.fillMaxSize()

    ) {

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()


        ) {


            Text(
                text = stringResource(id = R.string.home_title),
                style = MaterialTheme.typography.h3,
                modifier = Modifier
                    .padding(start = 5.dp, top = 0.dp, end = 12.dp, bottom = 12.dp),
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.size(6.dp))

            Text(
                text = currentDateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)),
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .padding(start = 5.dp, top = 6.dp, end = 12.dp, bottom = 24.dp),

//                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.size(24.dp))

            Box(
               contentAlignment = Alignment.Center,
               modifier = Modifier
                   .padding(vertical = 12.dp, horizontal = 5.dp)
                   .fillMaxWidth()
                   .clip(RoundedCornerShape(10.dp))
                   .clickable {
                       navigation?.navigate("new_idea")
                   }
                   .background(liquidGreen)
                   .padding(horizontal = 5.dp, vertical = 25.dp)
                   .align(alignment = Alignment.CenterHorizontally)

            ) {

                Text(
                    text = "New Idea",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6,


                )

            }

            Spacer(modifier = Modifier.size(24.dp))

            Text(
                text = stringResource(id = R.string.favourite),
                style = MaterialTheme.typography.h4,
                modifier = Modifier
                    .padding(start = 20.dp, top = 12.dp, end = 12.dp, bottom = 12.dp),
//                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.padding(10.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                itemsIndexed(
                    items = favouriteResults,

                    ){
                        index, item ->

                    ExpandableCard(title = item.title, description = item.description) {
                        navigation?.navigate("update_idea/${item.id}")

                    }
                }
            }




        }
    }

}

@Composable
fun AppBarRightIcon(
    title: String,
    icon: ImageVector,
    description: String,
    iconClickAction: () -> Unit
) {
    TopAppBar(
        title = { Text(text = title) },
        backgroundColor = Color.White,




//The navigation Icon parameter is used to place a parameter to the left(ie menu)
        navigationIcon = {
            Icon(
                imageVector = icon, contentDescription = description,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable(onClick = { iconClickAction.invoke() })
            )
        },
        elevation = 0.dp



    )
}

@Composable
fun AppBarLeftIcon(
    title: String,
    icon: ImageVector,
    description: String,
    iconSize : Int = 25,
    iconClickAction: () -> Unit,

){

    TopAppBar(
        title = { Text(text = title) },
        backgroundColor = Color.White,
        elevation = 0.dp,

        // The actions is used to place an Icon to the right (ie a delete button)
        actions = {
                Icon(
                    imageVector = icon, contentDescription = description,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable(onClick = { iconClickAction.invoke() })
                        .size(iconSize.dp)
                )
        }



    )


}


