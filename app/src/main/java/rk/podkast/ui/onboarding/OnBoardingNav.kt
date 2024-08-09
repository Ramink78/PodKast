package rk.podkast.ui.onboarding

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable

@Serializable
data object OnBoardingNav

@Serializable
data object OnBoard

fun NavGraphBuilder.onboardingGraph(onFinish: () -> Unit) {
    navigation<OnBoardingNav>(startDestination = OnBoard) {
        composable<OnBoard> {
            OnboardingScreen(Modifier.fillMaxSize(), onFinish = onFinish)
        }
    }
}