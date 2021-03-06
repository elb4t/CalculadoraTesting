package es.elb4t.calculadoratesting;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by eloy on 4/3/18.
 */

@RunWith(JUnitParamsRunner.class)
public class MathCalculatorActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    @Parameters(method = "getValidOperandButtonData")
    public void onClickButtonShouldAddExpectedValueToOperationsViews(int buttonId, String expectedValue) {
        //Click en el botón
        onView(withId(buttonId)).perform(click());
        //Comprobamos si la pantalla de operaciones muestra el valor esperado
        onView(withId(R.id.operations)).check(matches(withText(expectedValue)));
    }

    private static Object[] getValidOperandButtonData() {
        return new Object[]{
                new Object[]{R.id.bt1, "1"},
                new Object[]{R.id.bt2, "2"},
                new Object[]{R.id.bt3, "3"},
                new Object[]{R.id.bt4, "4"},
                new Object[]{R.id.bt5, "5"},
                new Object[]{R.id.bt6, "6"},
                new Object[]{R.id.bt7, "7"},
                new Object[]{R.id.bt8, "8"},
                new Object[]{R.id.bt9, "9"},
                new Object[]{R.id.bt0, "0"},
                new Object[]{R.id.bt00, "00"},
                new Object[]{R.id.bt_addition, " + "},
                new Object[]{R.id.bt_subtraction, " - "},
                new Object[]{R.id.bt_multiplication, " x "},
                new Object[]{R.id.bt_division, " / "},
                new Object[]{R.id.bt_exponentiation, " ^ "},
                new Object[]{R.id.bt_factorial, " fact ("},
                new Object[]{R.id.bt_square_root, " sqrt ("},
                new Object[]{R.id.bt_dot, "."},
                new Object[]{R.id.bt_parenthesis_start, " ("},
                new Object[]{R.id.bt_parenthesis_end, ") "}
        };
    }

    public static ViewAction setText(final String value) {
        return new ViewAction() {
            @SuppressWarnings("unchecked")
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isDisplayed(), isAssignableFrom(TextView.class));
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((TextView) view).setText(value);
            }

            @Override
            public String getDescription() {
                return "replace text";
            }
        };
    }

    @Test(timeout = 100)
    @Parameters(method = "getValidOperationsData")
    public void onOperationsViewChangedShouldUpdateResultsView(
            String operations, String result) {
        onView(withId(R.id.operations)).perform(setText(operations));
        onView(allOf(withParent(withId(R.id.result)), isCompletelyDisplayed()))
                .check(matches(withText(result)));
    }

    private static Object[] getValidOperationsData() {
        return new Object[]{
                new Object[]{"2+2", "4"},
                new Object[]{"5/2", "2.5"},
                new Object[]{"3+(4x3)", "15"}};
    }

    @Test
    @Parameters(method = "getValidRemoveLastButtonData")
    public void removeLastButtonViewChangedShouldUpdateResultsView(
            String operations, String result) {
        onView(withId(R.id.operations)).perform(setText(operations));
        onView(withId(R.id.bt_remove_last)).perform(click());
        onView(allOf(withParent(withId(R.id.result)), isCompletelyDisplayed()))
                .check(matches(withText(result)));
    }

    private static Object[] getValidRemoveLastButtonData() {
        return new Object[]{
                new Object[]{"2+2", "2"},
                new Object[]{"4-2+1", "2"},
                new Object[]{"5/2", "5"},
                new Object[]{"3+(4x3)", "15"}};
    }

    @Test
    @Parameters(method = "getValidClearButtonData")
    public void clearButtonViewChangedShouldUpdateResultsView(
            String operations, String result) {
        onView(withId(R.id.operations)).perform(setText(operations));
        onView(withId(R.id.bt_clear)).perform(click());
        onView(allOf(withParent(withId(R.id.result)), isCompletelyDisplayed()))
                .check(matches(withText(result)));
    }

    private static Object[] getValidClearButtonData() {
        return new Object[]{
                new Object[]{"2+2", ""},
                new Object[]{"4-2+1", ""},
                new Object[]{"5/2", ""},
                new Object[]{"3+(4x3)", ""}};
    }
}
