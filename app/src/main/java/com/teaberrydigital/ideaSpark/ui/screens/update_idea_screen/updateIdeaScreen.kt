package com.teaberrydigital.ideaSpark.ui.screens.update_idea_screen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.teaberrydigital.ideaSpark.MainActivityViewModel
import com.teaberrydigital.ideaSpark.ui.theme.liquidGreen
import kotlinx.coroutines.launch


@Composable
fun UpdateIdeaScreen(
    viewModel: MainActivityViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    ideaId: Int,
    navigation:NavHostController
){
    viewModel.onCurrentItemChange(ideaId)
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()


    //Fetching the item to display the correct idea and description on the update screen
    val result by viewModel.readAllData.observeAsState(initial = emptyList())
    for(item in result){
        if(item.id == ideaId){
            viewModel.onChangeUpdateTitle(item.title)
            viewModel.onChangeUpdateDescription(item.description)
            viewModel.onCheckChange(item.isFavourite)
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(20.dp)

        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Title",
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier
                        .padding(top = 12.dp, end = 12.dp)

                )

                Icon(
                    imageVector = Icons.Default.Delete,

                    contentDescription = "Delete Icon" ,

                    modifier = Modifier
                        .size(36.dp)
                        .clickable {
                            viewModel.onShowDialogChange(true)
                        }
                )
            }

//        Text(
//            text = "Title",
//            fontWeight = FontWeight.SemiBold,
//            style = MaterialTheme.typography.h2,
//            modifier = Modifier
//                .padding(top = 12.dp, end = 12.dp)
//                .align(Start)
//        )

            Spacer(modifier = Modifier.size(24.dp))

            OutlinedTextField(
                value = viewModel.updateTitle.value,
                onValueChange = {
                    viewModel.onChangeUpdateTitle(it)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Enter title here...")
                }
            )

            Spacer(modifier = Modifier.size(56.dp))

            Text(
                text = "Description",
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.h3,
                modifier = Modifier
                    .padding(top = 12.dp, end = 12.dp)
                    .align(Start)
            )

            Spacer(modifier = Modifier.size(36.dp))

            OutlinedTextField(
                value = viewModel.updateDescription.value,
                onValueChange = {
                    viewModel.onChangeUpdateDescription(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                placeholder = {
                    Text(text = "Enter description here...")
                }
            )

            Spacer(modifier = Modifier.size(72.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()

            ) {

                Box(
                    modifier = Modifier
                        .width(140.dp)
                        .height(46.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(liquidGreen)


                ) {

                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()

                    ) {
//                    val checkedState = remember{
//                        mutableStateOf(true)
//                    }
                        Checkbox(
                            checked = viewModel.checkState.value,
                            onCheckedChange = { viewModel.onCheckChange(it) },
                            modifier = Modifier
                                .padding(start = 5.dp, top = 10.dp, bottom = 5.dp, end = 5.dp)
                        )

                        // The below should be a heart that behaves like a checkbox
                        Text(
                            text = "Favourite",
                            modifier = Modifier
                                .padding(start = 5.dp, top = 10.dp, bottom = 5.dp, end = 7.dp),
                            fontWeight = FontWeight.Bold
                        )


                    }


                }

                Box(
                    modifier = Modifier
                        .width(108.dp)
                        .height(46.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .clickable {
                            if (viewModel.updateIdea()) {

                                navigation.navigate("all_ideas")

                            } else {
//                        Toast.makeText(composeContext,"Please fill all entry fields",Toast.LENGTH_LONG).show()
                                scope.launch {
                                    scaffoldState
                                        .snackbarHostState
                                        .showSnackbar(
                                            "Please fill out all the fields",
                                            duration = SnackbarDuration.Short
                                        )
                                }
                            }
                        }
                        .background(liquidGreen)



                ) {

                    Text(
                        text = "Save",
                        modifier = Modifier
                            .align(Center),
                        fontWeight = FontWeight.Bold
                    )

                }



            }



            MyAlertDialog(
                condition = viewModel.showDialog.value,
                title = "Delete confirmation" ,
                showText = "Are you sure you want to delete this idea?",
                confirmText = "Yes",
                dismissText = "No" ,
                dismissAction = {
                    viewModel.onShowDialogChange(false)
                    navigation.navigate("all_ideas")
                },
                dismissRequest = {
                    viewModel.onShowDialogChange(false)
                }
            ) {
                viewModel.deleteThisIdea()
                viewModel.onShowDialogChange(false)
                navigation.navigate("all_ideas")

            }

        }
    }


}

@Composable
fun MyAlertDialog(
    condition: Boolean?,
    title:String,
    showText: String,
    confirmText:String,
    dismissText:String,
    dismissAction: () -> Unit,
    dismissRequest: () -> Unit,
    confirmAction: () -> Unit
) {

    if (condition == true){
        MaterialTheme {
            Column {

                AlertDialog(

                    onDismissRequest = {
                        dismissRequest.invoke()

                    },
                    title = {
                        Text(text = title)
                    },
                    text = {
                        Text(showText)
                    },
                    confirmButton = {
                        Button(

                            onClick = {
                                confirmAction.invoke()
                            }) {
                            Text(confirmText)
                        }
                    },
                    dismissButton = {
                        Button(

                            onClick = {
                                dismissAction.invoke()
                            }) {
                            Text(dismissText)
                        }
                    }
                )


            }
        }

    }

}