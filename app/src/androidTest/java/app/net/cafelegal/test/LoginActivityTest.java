package app.net.cafelegal.test;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import udacitynano.com.br.cafelegal.*;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class LoginActivityTest {

    String mTest;

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setup(){
        mTest = "negative";
    }

    private static int getResourceId(String s) {
        Context targetContext = InstrumentationRegistry.getTargetContext();
        String packageName = targetContext.getPackageName();
        return targetContext.getResources().getIdentifier(s, "id", packageName);
    }


    @Test
    public void negativeLogin(){

        if(mTest.equals("negative")){
            onView(withId(udacitynano.com.br.cafelegal.R.id.editTextEmailId)).perform(typeText("mmiotto@gmail.com"));
            onView(withId(udacitynano.com.br.cafelegal.R.id.editTextPasswordId)).perform(typeText("9999156"));

            onView(withId(udacitynano.com.br.cafelegal.R.id.buttonSignInId)).perform(click());
        }
    }


    @Test
    public void positiveLogin(){
        if(mTest.equals("positive")) {
            onView(withId(udacitynano.com.br.cafelegal.R.id.editTextEmailId)).perform(typeText("mmiotto@gmail.com"));
            onView(withId(udacitynano.com.br.cafelegal.R.id.editTextPasswordId)).perform(typeText("123456"));

            onView(withId(udacitynano.com.br.cafelegal.R.id.buttonSignInId)).perform(click());
        }
    }



}