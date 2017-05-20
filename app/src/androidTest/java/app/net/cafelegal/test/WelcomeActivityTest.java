package app.net.cafelegal.test;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import udacitynano.com.br.cafelegal.WelcomeActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import udacitynano.com.br.cafelegal.R;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class WelcomeActivityTest {


    @Rule
    public ActivityTestRule<WelcomeActivity> mActivityRule =
            new ActivityTestRule<>(WelcomeActivity.class);

    private static int getResourceId(String s) {
        Context targetContext = InstrumentationRegistry.getTargetContext();
        String packageName = targetContext.getPackageName();
        return targetContext.getResources().getIdentifier(s, "id", packageName);
    }


    @Test
    public void advogadoPerfilFlow(){
        onView(withId(R.id.welcome_button_sou_advogado)).perform(click());
        onView(withId(R.id.welcome_button_aviso_perfil)).perform(click());
    }


    @Test
    public void advogadoConviteFlow(){
        onView(withId(R.id.welcome_button_sou_advogado)).perform(click());
        onView(withId(R.id.welcome_button_aviso_perfil_depois)).perform(click());
    }


}