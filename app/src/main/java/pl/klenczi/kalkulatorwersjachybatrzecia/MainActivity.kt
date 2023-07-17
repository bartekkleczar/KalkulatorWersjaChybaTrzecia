package pl.klenczi.kalkulatorwersjachybatrzecia

import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import android.view.RoundedCorner
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import pl.klenczi.kalkulatorwersjachybatrzecia.ui.theme.KalkulatorWersjaChybaTrzeciaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val constraints = ConstraintSet {
        val boxTop = createRefFor("boxTop")
        val boxBot = createRefFor("boxBot")
        val guideTop = createGuidelineFromTop(0f)
        val guideBot = createGuidelineFromBottom(0f)

        constrain(boxTop) {
            top.linkTo(guideTop)
        }
        constrain(boxBot) {
            bottom.linkTo(guideBot)
        }
    }
    val constraintsCalc = ConstraintSet {
        val firstList = createRefFor("first")
        val secondList = createRefFor("second")
        val thirdList = createRefFor("third")
        val fourthList = createRefFor("fourth")
        val fifthList = createRefFor("fifth")

        val guide0 = createGuidelineFromTop(0f)
        val guide25 = createGuidelineFromTop(0.2f)
        val guide50 = createGuidelineFromTop(0.4f)
        val guide75 = createGuidelineFromTop(0.6f)
        val guide1 = createGuidelineFromTop(0.8f)

        constrain(firstList) {
            top.linkTo(guide0)
        }
        constrain(secondList) {
            top.linkTo(guide25)
        }
        constrain(thirdList) {
            top.linkTo(guide50)
        }
        constrain(fourthList) {
            top.linkTo(guide75)
        }
        constrain(fifthList) {
            top.linkTo(guide1)
        }
    }

    val clickedChars = remember { mutableStateOf(emptyList<String>()) }
    ConstraintLayout(
        constraints, modifier = Modifier.fillMaxSize()
    ) {
        val bottomBoxHeight = 0.5f
        Box( // TOP
            modifier = Modifier
                .layoutId("boxTop")
                .border(1.dp, Color.DarkGray)
                .fillMaxWidth()
                .fillMaxHeight(1f - bottomBoxHeight)
                .background(color = Color.Black)
        ) {
            Text(
                text = clickedChars.value,
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        }
        Box( // BOTTOM
            modifier = Modifier
                .layoutId("boxBot")
                .border(1.dp, Color.DarkGray)
                .fillMaxWidth()
                .fillMaxHeight(bottomBoxHeight)
                .background(color = Color.Black)
        ) {
            ConstraintLayout(constraintsCalc, modifier = Modifier.fillMaxSize()) {
                Buttons(listOf("C", "CE", "%", "/"), modifier = Modifier.layoutId("first")){
                    clickedChars.value += it
                }
                Buttons(listOf("7", "8", "9", "X"), modifier = Modifier.layoutId("second")){
                    clickedChars.value += it
                }
                Buttons(listOf("4", "5", "6", "-"), modifier = Modifier.layoutId("third")){
                    clickedChars.value += it
                }
                Buttons(listOf("1", "2", "3", "+"), modifier = Modifier.layoutId("fourth")){
                    clickedChars.value += it
                }
                Buttons(listOf("N", "0", ",", "="), modifier = Modifier.layoutId("fifth")){
                    //if(it == "=") calculate() else clickedChars.value += it
                }
            }
        }
    }
}

@Composable
fun Buttons(list: List<String>, modifier: Modifier, onClick: (String) -> Unit) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.15f)
    ) {
        itemsIndexed(
            list
        ) { index, item ->
            val equalSignColor = if(item == "=") Color(255, 105, 0) else Color.Transparent
            Box(
                modifier = Modifier
                    .width(102.dp)
                    .padding(horizontal = 5.dp)
                    .background(color = equalSignColor, shape = CircleShape)
                    .clickable {
                        onClick(item)
                    }
            ) {
                val signColor = if (item in setOf("1","2" ,"3" , "4", "5", "6", "7", "8", "9", "0", ",", "=")) {
                    Color.White
                } else {
                    Color(255, 105, 0)
                }
                Text(
                    text = item,
                    fontSize = 45.sp,
                    color = signColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
