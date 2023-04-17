# ThisWeekTVShows

This week TV Shows app

Screen 1: This week most popular shows screen:
It should display a list or a grid of cards with image poster of the show and brief details about the show,
such as name.
API: https://developers.themoviedb.org/3/trending/get-trending
Search feature
At the top bar the app should offer a search option that will allow the user to search through their favorite
shows. The results from the searched should be displayed as user inputs query text. When the search query
field is empty it should display the shows that are trending this week.
API: https://developers.themoviedb.org/3/search/search-tv-shows

Screen 2: Details screen
Once clicked on the tv show card the app should navigate to new screen where the details of the shows
should be displayed. Details page should also offer an action button at the right corner to add the tv show
to favorite shows that is persisted only locally. Once the TV show is added to favorites it should also show
an indicator on the first screen, in form of a visual cue (display a heart or star over the card) that the show
is favorited or removed, respectively.
Details screen should also display a list of all seasons of this show and number of episodes. List of the
seasons should not be clickable. Pressing back on this screen should navigate back to this week top shows
screen.
At the bottom of the details screen you should display similarshows in a horizontal scrollable.

Optional:
If the user clicks on the similar shows card it should navigate to new instance of the details screen. To retain
logical navigation flow, pressing back should navigate to previous details screen.

Similar shows should be fetched from this end point: https://developers.themoviedb.org/3/tv/get-similar-
tv-shows
