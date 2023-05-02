package top.umair.tecjaunttask.screens

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import top.umair.tecjaunttask.R
import top.umair.tecjaunttask.components.CircularProgressBar
import top.umair.tecjaunttask.components.InputField
import top.umair.tecjaunttask.components.PickProfileImage
import top.umair.tecjaunttask.models.InnovatortEntity
import top.umair.tecjaunttask.utils.getFireBaseAuth
import top.umair.tecjaunttask.viewModel.InnovatorViewModel

@SuppressLint("SuspiciousIndentation")
@Composable
fun InnovatorAddScreen(vieModel:InnovatorViewModel = hiltViewModel()) {
    val innovatorsPojo = InnovatortEntity()
    val context = LocalContext.current

    var selectImage by remember { mutableStateOf(false) }
    val imageUri = remember {

        mutableStateOf<Uri?>(null)
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    }


    val scrollState = rememberScrollState()
    ConstraintLayout(
        portraitConstraints(margin = 16.dp), modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {


        Box(
            modifier = Modifier
                .layoutId("profile_image")
                .clickable {
                    selectImage = true
                }
                .size(100.dp)
                .clip(CircleShape)
                .border(width = 2.dp, color = Color.Gray, CircleShape),
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = imageUri.value)
                        .apply(block = fun ImageRequest.Builder.() {
                            crossfade(false)
                            error(R.drawable.icon_profile)
                            placeholder(R.drawable.icon_profile)
                        }).build()
                ),
                contentDescription = "description",
                contentScale = ContentScale.Crop

            )
        }

        Column(
            modifier = Modifier
                .layoutId("column")
        ) {

            val progressBar = remember {
                mutableStateOf(false)
            }

            CircularProgressBar(isDisplayed = progressBar.value)



            if (selectImage) {

                PickProfileImage(onImageSelected = {
                    imageUri.value = it
                    if (imageUri.value != null) {
                        selectImage = false

                    }


                }, onError = {
                    showToast("Error selecting image: $it")
                    selectImage = false


                })

            }
            InnovatorsSingUpForm(innovatorsPojo)


//            list.forEach{
//                InputText(text = it)
//
//            }




            Button(
                onClick = {

                    progressBar.value =true

                    if (
                        validations(innovatorsPojo)) {
                        if (getFireBaseAuth().currentUser != null) {
                            innovatorsPojo.email = getFireBaseAuth().currentUser?.email
                            innovatorsPojo.uuid = getFireBaseAuth().currentUser?.uid

                        }

                        imageUri.value?.let {
                            vieModel.addInnovatorProfile(innovatorsPojo, it){

                                if (it){
                                    progressBar.value = false
                                    Toast.makeText(context, "Innovator is Added", Toast.LENGTH_SHORT).show()

                                }else{
                                    Toast.makeText(context, "Sorry Something is wrong", Toast.LENGTH_SHORT).show()
                                }

                            }
                        }


                    } else {

                        Toast.makeText(
                            context,
                            "fields or image missing",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("TAG", "InvestorSignUp: missing values")
                    }
                }, shape = RoundedCornerShape(25.dp), modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
            ) {
                Text(text = "Login")

            }
        }


    }

}

private fun portraitConstraints(margin: Dp): ConstraintSet {

    return ConstraintSet {
        val column = createRefFor("column")
        val row = createRefFor("row")
        val profileImage = createRefFor("profile_image")


        constrain(profileImage) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(column) {
            top.linkTo(profileImage.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        constrain(row) {
            top.linkTo(column.bottom)
            start.linkTo(parent.start)

        }

    }

}




@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun InnovatorsSingUpForm(innovatortEntity: InnovatortEntity): Boolean {
    val keyboardController = LocalSoftwareKeyboardController.current
    var checkEmpty = false

    var firstNAmeState = remember {
        mutableStateOf("")
    }
    var lastNAmeState = remember {
        mutableStateOf("")
    }
    var mobileNumberState = remember {
        mutableStateOf("")
    }
    var collegeState = remember {
        mutableStateOf("")
    }

    var ageState = remember {
        mutableStateOf("")
    }
    var addressState = remember {
        mutableStateOf("")
    }
    var countryState = remember {
        mutableStateOf("")
    }
    var websiteState = remember {
        mutableStateOf("")
    }
    val descriptionState = remember {
        mutableStateOf("")
    }
    InputField(valueState = firstNAmeState,
        labelId = "First Name",
        enabled = true,
        isSingleLine = true,
        onAction = KeyboardActions {


            keyboardController?.hide()
        }

    ) {

//        checkEmpty = it.value.isNotEmpty()

        innovatortEntity.firstName = it.value
        checkEmpty = innovatortEntity.firstName?.isNotEmpty() == true

    }
    InputField(valueState = lastNAmeState,
        labelId = "Last Name",
        enabled = true,
        isSingleLine = true,
        onAction = KeyboardActions {

            keyboardController?.hide()
        }
    ) {
//        checkEmpty = it.value.isNotEmpty()
        Log.d("TAG", "InnovatorsSingUpForm: $checkEmpty")

        innovatortEntity.lastName = it.value
        checkEmpty = innovatortEntity.lastName?.isNotEmpty() == true

    }



    InputField(valueState = ageState, labelId = "Age", enabled = true, isSingleLine = true,
        onAction = KeyboardActions {

            keyboardController?.hide()
        }
    ) {
//        checkEmpty = it.value.isNotEmpty()
        Log.d("TAG", "InnovatorsSingUpForm: $checkEmpty")

        innovatortEntity.age = it.value.trim()
        checkEmpty = innovatortEntity.age?.isNotEmpty() == true


    }



    InputField(valueState = countryState, labelId = "Country", enabled = true, isSingleLine = true,
        onAction = KeyboardActions {


            keyboardController?.hide()
        }
    ) {
//        checkEmpty = it.value.isNotEmpty()

        innovatortEntity.country = it.value.trim()
        checkEmpty = innovatortEntity.country?.isNotEmpty() == true

    }

    InputField(valueState = collegeState, labelId = "College", enabled = true, isSingleLine = true,
        onAction = KeyboardActions {


            keyboardController?.hide()
        }
    ) {
//        checkEmpty = it.value.isNotEmpty()

        innovatortEntity.college = it.value.trim()
        checkEmpty = innovatortEntity.college?.isNotEmpty() == true

    }


    InputField(valueState = descriptionState,
        labelId = "Description",
        enabled = true,
        isSingleLine = true,
        onAction = KeyboardActions {

            keyboardController?.hide()
        }


    ) {
//        checkEmpty = it.value.isNotEmpty()

        innovatortEntity.description = it.value.trim()
        checkEmpty = innovatortEntity.description?.isNotEmpty() == true

    }


    return checkEmpty


}

private fun validations(innovatortEntity: InnovatortEntity): Boolean {
    return innovatortEntity.run {
        firstName?.isNotEmpty() == true &&
                lastName?.isNotEmpty() == true &&
                age?.isNotEmpty() == true &&
                country?.isNotEmpty() == true &&
                description?.isNotEmpty() == true &&
                college?.isNotEmpty() == true

    }
}
