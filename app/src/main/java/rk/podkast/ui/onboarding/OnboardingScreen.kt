package rk.podkast.ui.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import rk.podkast.R
import rk.podkast.ui.interest.AnimatableBoxGradient
import rk.podkast.ui.interest.InterestScreen

@Composable
fun OnboardingScreen(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 3 })
    AnimatableBoxGradient(modifier = modifier) {
        HorizontalPager(
            state = pagerState, modifier =
            Modifier.fillMaxSize(),
            beyondViewportPageCount = 1
        ) { pageNumber ->
            PageContent(currentPage = pageNumber)
        }
    }


}

@Composable
private fun PageContent(
    currentPage: Int
) {
    when (currentPage) {
        in 0..1 -> {
            val titleRes = remember(currentPage) {
                if (currentPage == 0)
                    R.string.onboarding_screen_title1
                else
                    R.string.onboarding_screen_title2
            }
            val descRes = remember(currentPage) {
                if (currentPage == 0)
                    R.string.onboarding_screen_description1
                else
                    R.string.onboarding_screen_description2
            }
            val lottieComposition by rememberLottieComposition(
                if (currentPage == 0)
                    LottieCompositionSpec.RawRes(R.raw.onboard_image1)
                else
                    LottieCompositionSpec.RawRes(R.raw.onboard_image2)

            )
            val lottieProgress by
            animateLottieCompositionAsState(lottieComposition)
            OnBoardingPage(
                title = stringResource(id = titleRes),
                description = stringResource(id = descRes),
                lottieComposition = lottieComposition,
                lottieProgress = lottieProgress

            )
        }

        else -> {
            InterestScreen(
                modifier = Modifier.fillMaxSize(),
            )
        }

    }


}

@Composable
private fun OnBoardingPage(
    title: String,
    description: String,
    lottieComposition: LottieComposition?,
    lottieProgress: Float,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            LottieAnimation(
                modifier = Modifier
                    .width(250.dp)
                    .aspectRatio(1f),
                composition = lottieComposition,
                progress = { lottieProgress },
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = title, style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.padding(horizontal = 50.dp),
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }


    }
}
