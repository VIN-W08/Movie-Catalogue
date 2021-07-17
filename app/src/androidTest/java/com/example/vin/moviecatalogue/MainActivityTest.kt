package com.example.vin.moviecatalogue

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.vin.moviecatalogue.utils.Data
import com.example.vin.moviecatalogue.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainActivityTest{
    private val dummyMovies = Data.getMoviesData()
    private val dummyMovie = Data.getMovieById(dummyMovies[0].id)
    private val dummyTVShows = Data.getTVShowData()
    private val dummyTVShow = Data.getTVShowById(dummyTVShows[0].id)

    @Before
    fun setUp(){
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadMovies(){
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovies.size))
    }

    @Test
    fun loadMovieDetail(){
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.posterIV)).check(matches(isDisplayed()))

        onView(withId(R.id.titleTV)).check(matches(isDisplayed()))
        onView(withId(R.id.titleTV)).check(matches(withText(dummyMovie.title)))

        onView(withId(R.id.favoriteImage)).check(matches(isDisplayed()))

        onView(withId(R.id.releaseDataTV)).check(matches(isDisplayed()))
        onView(withId(R.id.releaseDataTV)).check(matches(withText(dummyMovie.releaseDate)))

        onView(withId(R.id.descTV)).check(matches(isDisplayed()))
        onView(withId(R.id.descTV)).check(matches(withText(dummyMovie.description)))
    }

    @Test
    fun loadTVShows(){
        onView(withText("TV Shows")).perform(click())
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTVShows.size))
    }

    @Test
    fun loadTVShowDetail(){
        onView(withText("TV Shows")).perform(click())
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.posterIV)).check(matches(isDisplayed()))

        onView(withId(R.id.titleTV)).check(matches(isDisplayed()))
        onView(withId(R.id.titleTV)).check(matches(withText(dummyTVShow.title)))

        onView(withId(R.id.favoriteImage)).check(matches(isDisplayed()))

        onView(withId(R.id.releaseDataTV)).check(matches(isDisplayed()))
        onView(withId(R.id.releaseDataTV)).check(matches(withText(dummyTVShow.releaseDate)))

        onView(withId(R.id.descTV)).check(matches(isDisplayed()))
        onView(withId(R.id.descTV)).check(matches(withText(dummyTVShow.description)))
    }

    @Test
    fun insertAndDeleteFavorite(){
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.favoriteImage)).perform(click())
        pressBack()

        onView(withText("TV Shows")).perform(click())
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.favoriteImage)).perform(click())
        pressBack()

        onView(withId(R.id.favorite)).perform(click())
        onView(withId(R.id.favoriteRecyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.favoriteRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.favoriteImage)).perform(click())
        pressBack()

        onView(withText("TV Shows")).perform(click())
        onView(withId(R.id.favoriteRecyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.favoriteRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.favoriteImage)).perform(click())
        pressBack()
    }

    @Test
    fun loadFavoriteDetail(){
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.favoriteImage)).perform(click())
        pressBack()

        onView(withText("TV Shows")).perform(click())
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.favoriteImage)).perform(click())
        pressBack()

        onView(withId(R.id.favorite)).perform(click())
        onView(withId(R.id.favoriteRecyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.favoriteRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.posterIV)).check(matches(isDisplayed()))
        onView(withId(R.id.titleTV)).check(matches(isDisplayed()))
        onView(withId(R.id.titleTV)).check(matches(withText(dummyMovie.title)))
        onView(withId(R.id.favoriteImage)).check(matches(isDisplayed()))
        onView(withId(R.id.releaseDataTV)).check(matches(isDisplayed()))
        onView(withId(R.id.releaseDataTV)).check(matches(withText(dummyMovie.releaseDate)))
        onView(withId(R.id.descTV)).check(matches(isDisplayed()))
        onView(withId(R.id.descTV)).check(matches(withText(dummyMovie.description)))
        onView(withId(R.id.favoriteImage)).perform(click())
        pressBack()

        onView(withText("TV Shows")).perform(click())
        onView(withId(R.id.favoriteRecyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.favoriteRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.posterIV)).check(matches(isDisplayed()))
        onView(withId(R.id.titleTV)).check(matches(isDisplayed()))
        onView(withId(R.id.titleTV)).check(matches(withText(dummyTVShow.title)))
        onView(withId(R.id.favoriteImage)).check(matches(isDisplayed()))
        onView(withId(R.id.releaseDataTV)).check(matches(isDisplayed()))
        onView(withId(R.id.releaseDataTV)).check(matches(withText(dummyTVShow.releaseDate)))
        onView(withId(R.id.descTV)).check(matches(isDisplayed()))
        onView(withId(R.id.descTV)).check(matches(withText(dummyTVShow.description)))
        onView(withId(R.id.favoriteImage)).perform(click())
        pressBack()
    }
}