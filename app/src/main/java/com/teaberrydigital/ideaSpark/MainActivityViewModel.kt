package com.teaberrydigital.ideaSpark

import android.app.Application
import android.text.TextUtils
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.teaberrydigital.ideaSpark.data.Idea
import com.teaberrydigital.ideaSpark.data.IdeaDatabase
import com.teaberrydigital.ideaSpark.data.IdeaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivityViewModel(application: Application): AndroidViewModel(application){


    // data package stuff
// ===========================================================================================

    val readAllData: LiveData<List<Idea>>
    val readAllFavourites: LiveData<List<Idea>>
    private val repository: IdeaRepository

    init {
        val userDao = IdeaDatabase.getDatabase(application).ideaDao()
        repository = IdeaRepository(userDao)
        readAllData = repository.readAllIdeas
        readAllFavourites = repository.readAllFavourites
    }

    fun addIdea(idea: Idea){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addIdea(idea)
        }
    }

    //Current Idea reference number
    private var _currentIdea = mutableStateOf(0)
    val currentIdea = _currentIdea


    //Function to change the current idea reference number
    fun onCurrentItemChange(it:Int){
        _currentIdea.value = it
    }



    // new_idea_screen
// ==========================================================================================

    // Mutable state for favouring check box
    private var _checkState =  mutableStateOf(false)
    val checkState = _checkState

    // Mutable state for adding the title
    private var _title = mutableStateOf("")
    val title =  _title

    // Mutable state for adding the description
    private var _description = mutableStateOf("")
    val description = _description

    // Function to change the value of the title field
    fun onTitleChange(entry:String){
        _title.value = entry
    }

    // Function for changing the value of the description field
    fun onDescriptionChange(it:String){
        description.value = it
    }

    // Function for changing the value of the checkbox check state
    fun onCheckChange(it:Boolean){
        _checkState.value = it
    }

// Data base functionality
// --------------------------------------------------------------------------------------

    // Function to add data from text fields into database
    fun insertToDatabase(): Boolean{
        val localTitle = title.value
        val localDescription = description.value
        val localFavourite = checkState.value

        if (checkData(title = localTitle, description =  localDescription)){
            // Create a User instance
            // Even though Room will make the primary key, we still need to specify a 0
            val idea = Idea(
                id = 0,
                title = localTitle.toString(),
                description = localDescription.toString(),
                isFavourite = localFavourite
            )

            addIdea(idea)

            return true
        } else {
            return false
        }

    }

    // Function to check if all the fields are filled out
    private fun checkData(title:String?, description:String?): Boolean {
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(description))
    }






    // update_idea_screen
//  =====================================================================================

    // Mutable state for title in update scree
    private var _updateTitle = mutableStateOf("")
    val updateTitle = _updateTitle

    // Mutable state for description in the update Screen
    private var _updateDescription = mutableStateOf("")
    val updateDescription = _updateDescription


    // Function for changing the value of the mutable state for the title
    fun onChangeUpdateTitle(it:String){
        _updateTitle.value = it
    }

    // Function for changing the value of the mutable state for the description
    fun onChangeUpdateDescription(it: String){
        _updateDescription.value = it
    }

    // Function to change to update the value of current item in the database
    private fun updateIdea(idea: Idea){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateIdea(idea)
        }
    }


    // Initial function to update values inside the database
    fun updateIdea(): Boolean{
        val localTitle = updateTitle.value
        val localDescription = updateDescription.value
        val localFavourite = checkState.value

        if (checkData(title = localTitle, description =  localDescription)){
            // Create a User instance
            // Even though Room will make the primary key, we still need to specify a 0
            val updateIdea = Idea(
                id = currentIdea.value,
                title = localTitle.toString(),
                description = localDescription.toString(),
                isFavourite = localFavourite
            )


            updateIdea(idea = updateIdea)

            return true
        } else {
            return false
        }

    }


    // Delete Ideas section
// ===============================================================================================

    fun deleteIdea(idea: Idea){
        viewModelScope.launch (Dispatchers.IO) {
            repository.deleteIdea(idea)
        }
    }

    fun deleteAllIdeas(){
        viewModelScope.launch (Dispatchers.IO) {
            repository.deleteAllIdeas()
        }
    }

    // Initial function called to delete the idea in the database
    fun deleteThisIdea(): Boolean{
        val localTitle = updateTitle.value
        val localDescription = updateDescription.value
        val localFavourite = checkState.value


        // Method for updating the user
        val ideaToDelete = Idea(
            id = currentIdea.value,
            title = localTitle.toString(),
            description = localDescription.toString(),
            isFavourite = localFavourite
        )

        deleteIdea(ideaToDelete)

        return true

    }

    // Mutable state for showing the dialog
    private var _showDialog = mutableStateOf(false)
    val showDialog = _showDialog


    // Function for changing the boolean for the alert dialog
    fun onShowDialogChange(it:Boolean){
        _showDialog.value = it
    }




}