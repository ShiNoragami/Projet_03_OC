
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;


/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {


    private static int ITEMS_COUNT;
    private int POSITION_ITEM = 0;

    private ListNeighbourActivity mActivity;
    private NeighbourApiService mService;
    private List<Neighbour> neighbourList;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);


    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
        mService = DI.getNewInstanceApiService();
        neighbourList = mService.getNeighbours();
        ITEMS_COUNT = neighbourList.size();
    }



    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position selected
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));
        System.out.println("Nombre d'objet " + ITEMS_COUNT);
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT-1));
        System.out.println("Nombre d'objet:" + ITEMS_COUNT);
    }

    /**
     * Open Activity detail, when click on list element.
     */
    @Test
    public void myNeighboursList_onClickItem_shouldOpenDetailActivity() {
        //Resultat : Lancement page profile
        //Click sur l'item
        onView(withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(POSITION_ITEM, click()));
        //Apres : Verification de l'affichage du Prenom.
        onView(withId(R.id.profile_name)).check(matches(isDisplayed()));
    }

    /**
     * Check if the name in DetailActivity is the same as the item selected.
     */
    @Test
    public void detailNeighbourName_onDetailActivity_isCorrect() {
        Neighbour neighbour = neighbourList.get(POSITION_ITEM);

        //resultat : Le bon prenom dans le profile
        //quand : ouverture de profile
        onView(withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(POSITION_ITEM, click()));
        //apres : Verification si le prenom du profile correspond au prenom du voisin.
        onView(withId(R.id.profile_name)).check(matches(withText(neighbour.getName())));
    }

    /**
     * Check if favorite list contain items marked as favorite.
     */
    @Test
    public void favoritesList_onFavoriteTab_showFavoriteItems() {
        //Resultat : Liste de favoris dans l'onglet favoris.

        //quand : Ajoute 2 favoris a l'aide du bouton fab.
        onView(withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(POSITION_ITEM, click()));
        onView(withId(R.id.profile_favorite))
                .perform(click());
        pressBack();

        onView(withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(POSITION_ITEM + 1, click()));
        onView(withId(R.id.profile_favorite))
                .perform(click());
        pressBack();

        //glisse vers l'onglet favoris.
        onView(withId(R.id.container)).perform(swipeLeft());

        //apres : Verification du nombre de favoris ajouter (=2).
        onView(ViewMatchers.withId(R.id.fav_neighbours)).check(withItemCount(2));
    }

    /**
     * When we delete an item in favorite, the item is no more shown
     */
    @Test
    public void myNeighboursListFavorite_deleteAction_shouldRemoveItemFromFavorite() {
        // Resultat : Retrait d'un favoris.

        //Ajout de favoris.
        onView(withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(POSITION_ITEM, click()));
        onView(withId(R.id.profile_favorite))
                .perform(click());
        pressBack();
        onView(withId(R.id.container)).perform(swipeLeft());

        //verifier que la liste favoris n'est pas vide.
        onView(ViewMatchers.withId(R.id.fav_neighbours)).check(withItemCount(1));

        // quand: click sur l'icone pour enlever le favoris
        onView(ViewMatchers.withId(R.id.fav_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));
        // apres : verifie le nombre d'element dans la liste favoris est -1
        onView(ViewMatchers.withId(R.id.fav_neighbours)).check(withItemCount(0));
    }
}