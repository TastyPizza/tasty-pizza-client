import android.view.View
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.tastypizzaclient.MainActivity
import com.example.tastypizzaclient.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testActivitySetup() {
        // Проверяем, что активность успешно создается
        Espresso.onView(withId(R.id.bottomNavigationView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testFragmentReplacement() {
        // Проверяем, что фрагмент успешно заменяется
        Espresso.onView(withId(R.id.bottomNavigationView))
            .perform(selectMenuItem(R.id.profile))

        // Проверяем, что фрагмент "profile" отображается
        Espresso.onView(withId(R.id.authorization_button_sign_in))
            .check(matches(isDisplayed()))
    }

    // Дополнительные тесты для других частей функции onCreate() могут быть добавлены здесь

    private fun selectMenuItem(menuItemId: Int) = object : ViewAction {
        override fun getConstraints() = null
        override fun getDescription() = "Выбор пункта меню с идентификатором $menuItemId"
        override fun perform(uiController: UiController?, view: View?) {
            view?.let {
                val menuItem = it.findViewById<View>(menuItemId)
                menuItem?.performClick()
            }
        }
    }
}
