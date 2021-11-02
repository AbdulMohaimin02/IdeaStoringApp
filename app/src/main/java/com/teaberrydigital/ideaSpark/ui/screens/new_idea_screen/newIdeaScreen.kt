package com.teaberrydigital.ideaSpark.ui.screens.new_idea_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.teaberrydigital.ideaSpark.MainActivityViewModel
import com.teaberrydigital.ideaSpark.ui.theme.liquidGreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun NewIdeaScreen(
    viewModel: MainActivityViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navigation: NavController
) {

    val scope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .padding(20.dp)

        ) {

            Text(
                text = "Title",
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.h2,
                modifier = Modifier
                    .padding(top = 12.dp, end = 12.dp)
                    .align(Start)
            )

            Spacer(modifier = Modifier.size(24.dp))

            OutlinedTextField(
                value = viewModel.title.value,
                onValueChange = {
                    viewModel.onTitleChange(it)
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
                value = viewModel.description.value,
                onValueChange = {
                    viewModel.onDescriptionChange(it)
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

                            if (viewModel.insertToDatabase()) {
                                navigation.navigate("home_screen")
                            } else {
                                scope.launch {
                                    scaffoldState
                                        .snackbarHostState
                                        .showSnackbar("Please fill out all the fields",duration = SnackbarDuration.Short)
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


        }

    }


}