package com.teaberrydigital.ideaSpark.screens.navigation_drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.teaberrydigital.ideaSpark.ui.theme.*
import kotlinx.coroutines.CoroutineScope

@Composable
fun NavDrawer(state: ScaffoldState, coroutineScope: CoroutineScope, navigation: NavHostController?) {
    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        NavDrawerSections()

        Spacer(modifier = Modifier.padding(12.dp))

        SectionsButton(title = "Home Screen") {
            navigation?.navigate("home_screen")
        }

        SectionsButton(title = "All Ideas") {
            navigation?.navigate("all_ideas")
        }



        Spacer(modifier = Modifier.padding(vertical = 240.dp))

        SectionsButton(title = "Trash") {
        }


    }
}

@Composable
fun SectionsButton(title: String, onClick: () -> Unit){

    Box(
        modifier = Modifier
            .padding(vertical = 6.dp, horizontal = 3.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                onClick.invoke()
            }
            .background(liquidGreen)
            .padding(horizontal = 10.dp, vertical = 20.dp)




    ) {

        androidx.compose.material.Text(
            text = title,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h6,

            )

    }
}

@Composable
fun NavDrawerSections(){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)

    ) {

        androidx.compose.material.Text(
            text = "Sections",
            style = MaterialTheme.typography.h4,


            fontWeight = FontWeight.Bold
        )

        Icon(
            imageVector = Icons.Default.Add ,
            contentDescription = "Button for adding a section",
            modifier = Modifier
                .size(35.dp)
        )



    }
}


