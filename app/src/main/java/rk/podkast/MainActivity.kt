package rk.podkast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import rk.podkast.ui.MainNav
import rk.podkast.ui.mainGraph
import rk.podkast.ui.onboarding.OnBoardingNav
import rk.podkast.ui.onboarding.onboardingGraph
import rk.podkast.ui.theme.PodKastTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val startDestination = OnBoardingNav
            PodKastTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController = navController, startDestination = startDestination) {
                        onboardingGraph(onFinish = {
                            navController.navigate(MainNav) {
                                popUpTo<OnBoardingNav>()
                            }
                        })
                        mainGraph()
                    }
                }
            }
        }
    }
}

