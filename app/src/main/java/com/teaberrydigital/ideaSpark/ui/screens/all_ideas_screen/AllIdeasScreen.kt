package com.teaberrydigital.ideaSpark.ui.screens.all_ideas_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.teaberrydigital.ideaSpark.R
import com.teaberrydigital.ideaSpark.screens.navigation_drawer.NavDrawer
import kotlinx.coroutines.launch
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.teaberrydigital.ideaSpark.MainActivityViewModel
import com.teaberrydigital.ideaSpark.ui.screens.update_idea_screen.MyAlertDialog
import com.teaberrydigital.ideaSpark.ui.theme.*


@ExperimentalMaterialApi
@Composable
fun AllIdeasScreen(
    navigation: NavHostController?,
    viewModel: MainActivityViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
){
    val result by viewModel.readAllData.observeAsState(initial = emptyList())

    val state = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = state,
        modifier = Modifier.fillMaxSize(),
        topBar= {

            TopAppBar(
                title = { Text(text = "") },
                backgroundColor = Color.White,
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.Menu, contentDescription = "Menu Icon",
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable(onClick = {
                                coroutineScope.launch { state.drawerState.open() }
                            })
                    )
                },

                actions = {
                    Icon(
                        imageVector = Icons.Default.Delete, contentDescription = "Delete all items",
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable(onClick = {
                                // TODO{Add the delete functionality for deleting all items in the list}

                                viewModel.onShowDialogChange(true)

                            })

                    )
                },

                elevation = 0.dp



            )
        },

        drawerShape = RoundedCornerShape(topEnd = 24.dp,bottomEnd = 24.dp),

        drawerContent = {
            NavDrawer(state,coroutineScope,navigation)
        },

        bottomBar={},


    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(10.dp)

        ) {

            Text(
                text = stringResource(id = R.string.all_ideas),
                style = MaterialTheme.typography.h3,
                modifier = Modifier
                    .padding(start = 5.dp, top = 0.dp, end = 12.dp, bottom = 12.dp),
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.size(15.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                itemsIndexed(
                    items = result,

                    ){
                        index, item ->

                    ExpandableCard(title = item.title, description = item.description) {
                        navigation?.navigate("update_idea/${item.id}")

                    }
                }
            }


            MyAlertDialog(
                condition = viewModel.showDialog.value,
                title = "Delete confirmation" ,
                showText = "Are you sure you want to delete all idea?",
                confirmText = "Yes",
                dismissText = "No" ,
                dismissAction = {
                    viewModel.onShowDialogChange(false)
                    navigation?.navigate("all_ideas")
                },
                dismissRequest = {
                    viewModel.onShowDialogChange(false)
                }
            ) {
                viewModel.deleteAllIdeas()
                viewModel.onShowDialogChange(false)
                navigation?.navigate("all_ideas")

            }





        }

    }

}

@ExperimentalMaterialApi
@Composable
fun ExpandableCard(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
            .padding(5.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 0.dp,
        backgroundColor = Beige1,
        onClick = {
            expandedState = !expandedState
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(6f),
                    text = title,
                    fontSize =  MaterialTheme.typography.h6.fontSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(
                    modifier = Modifier
                        .weight(1f)
                        .alpha(ContentAlpha.medium)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }
            if (expandedState) {
                Text(
                    text = description,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    fontWeight =  FontWeight.Normal,
//                    maxLines = 6,
                    overflow = TextOverflow.Ellipsis,

                )

                Spacer(modifier = Modifier.padding(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ){
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Idea",
                        modifier = Modifier
                            .padding(5.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .clickable {
                                onClick.invoke()
                            }
                    )
                }
            }


        }
    }
}


