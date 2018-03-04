package es.elb4t.calculadoratesting;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by eloy on 4/3/18.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void onClickButtonOneShouldAddOneToOperationsView() {
        //Clic en el botón 1
        onView(withId(R.id.bt1)).perform(click());
        //Comprueba si la pantalla de operaciones muestra el 1
        onView(withId(R.id.operations)).check(matches(withText("1")));
    }
}
