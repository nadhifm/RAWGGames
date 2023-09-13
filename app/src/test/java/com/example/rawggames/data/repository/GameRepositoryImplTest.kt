package com.example.rawggames.data.repository

import app.cash.turbine.test
import com.example.rawggames.data.local.GameDao
import com.example.rawggames.data.local.entity.GameEntity
import com.example.rawggames.data.local.entity.GenreEntity
import com.example.rawggames.data.mapper.toGameDetail
import com.example.rawggames.data.mapper.toGameEntity
import com.example.rawggames.data.remote.network.APIService
import com.example.rawggames.data.remote.response.DeveloperResponse
import com.example.rawggames.data.remote.response.GameDetailResponse
import com.example.rawggames.data.remote.response.GenreResponse
import com.example.rawggames.domain.model.Developer
import com.example.rawggames.domain.model.GameDetail
import com.example.rawggames.domain.model.Genre
import com.example.rawggames.utils.MainCoroutineRule
import com.example.rawggames.utils.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GameRepositoryImplTest {
    @get:Rule
    val mainCoroutineScopeRule = MainCoroutineRule()

    @Mock
    private lateinit var apiService: APIService

    @Mock
    private lateinit var gameDao: GameDao

    private lateinit var gameRepositoryImpl: GameRepositoryImpl

    @Before
    fun setup() {
        gameRepositoryImpl = GameRepositoryImpl(apiService, gameDao)
    }

    @Test
    fun `when Get Games Success Should Return Paging Data Movies`() = runTest {
        gameRepositoryImpl.getGames("").test {
            val result = awaitItem()
            assertNotNull(result)
        }
    }

    @Test
    fun `when Get Game Detail Success Should Return Success and Game Detail`() = runTest {
        val expectedResponse = GameDetailResponse(
            id = 3328,
            name = "The Witcher 3: Wild Hunt",
            released = "2015-05-18",
            backgroundImage = "https://media.rawg.io/media/games/618/618c2031a07bbff6b4f611f10b6bcdbc.jpg",
            developers = listOf(
                DeveloperResponse(
                    id = 9023,
                    name = "CD PROJEKT RED",
                )
            ),
            genres = listOf(
                GenreResponse(
                    id = 4,
                    name = "Action",
                ),
                GenreResponse(
                    id = 3,
                    name = "Adventure",
                ),
                GenreResponse(
                    id = 5,
                    name = "RPG",
                ),
            ),
            descriptionRaw = "The third game in a series, it holds nothing back from the player. Open world adventures of the renowned monster slayer Geralt of Rivia are now even on a larger scale. Following the source material more accurately, this time Geralt is trying to find the child of the prophecy, Ciri while making a quick coin from various contracts on the side. Great attention to the world building above all creates an immersive story, where your decisions will shape the world around you.\n\nCD Project Red are infamous for the amount of work they put into their games, and it shows, because aside from classic third-person action RPG base game they provided 2 massive DLCs with unique questlines and 16 smaller DLCs, containing extra quests and items.\n\nPlayers praise the game for its atmosphere and a wide open world that finds the balance between fantasy elements and realistic and believable mechanics, and the game deserved numerous awards for every aspect of the game, from music to direction.",
        )

        Mockito.`when`(apiService.getGameDetail(id = 3328)).thenReturn(expectedResponse)

        gameRepositoryImpl.getGameDetail(3328).test {
            Mockito.verify(apiService).getGameDetail(id = 3328)

            var result = awaitItem()
            assertTrue(result is Resource.Loading)

            result = awaitItem()
            assertTrue(result is Resource.Success)
            assertNotNull(result.data)
            assertEquals(expectedResponse.toGameDetail(), result.data)

            awaitComplete()
        }
    }

    @Test
    fun `when Get Game Detail Success Should Return Success and Message`() = runTest {
        val expectedResponse = IOException("No Internet Connection")

        Mockito.`when`(apiService.getGameDetail(id = 3328)).then {
            throw expectedResponse
        }

        gameRepositoryImpl.getGameDetail(3328).test {
            Mockito.verify(apiService).getGameDetail(id = 3328)

            var result = awaitItem()
            assertTrue(result is Resource.Loading)

            result = awaitItem()
            assertTrue(result is Resource.Error)
            assertNotNull(result.message)
            assertEquals(expectedResponse.message, result.message)

            awaitComplete()
        }
    }

    @Test
    fun `when Save To Favorite Success Should true`() = runTest {
        val gameDetail = GameDetail(
            id = 3328,
            name = "The Witcher 3: Wild Hunt",
            released = "2015-05-18",
            backgroundImage = "https://media.rawg.io/media/games/618/618c2031a07bbff6b4f611f10b6bcdbc.jpg",
            developers = listOf(
                Developer(
                    id = 9023,
                    name = "CD PROJEKT RED",
                )
            ),
            genres = listOf(
                Genre(
                    id = 4,
                    name = "Action",
                ),
                Genre(
                    id = 3,
                    name = "Adventure",
                ),
                Genre(
                    id = 5,
                    name = "RPG",
                ),
            ),
            description = "The third game in a series, it holds nothing back from the player. Open world adventures of the renowned monster slayer Geralt of Rivia are now even on a larger scale. Following the source material more accurately, this time Geralt is trying to find the child of the prophecy, Ciri while making a quick coin from various contracts on the side. Great attention to the world building above all creates an immersive story, where your decisions will shape the world around you.\n\nCD Project Red are infamous for the amount of work they put into their games, and it shows, because aside from classic third-person action RPG base game they provided 2 massive DLCs with unique questlines and 16 smaller DLCs, containing extra quests and items.\n\nPlayers praise the game for its atmosphere and a wide open world that finds the balance between fantasy elements and realistic and believable mechanics, and the game deserved numerous awards for every aspect of the game, from music to direction.",
        )

        Mockito.`when`(gameDao.insertGames(gameDetail.toGameEntity())).thenReturn(Unit)

        gameRepositoryImpl.saveToFavorite(gameDetail).test {
            Mockito.verify(gameDao).insertGames(gameDetail.toGameEntity())

            val result = awaitItem()
            assertTrue(result)
            awaitComplete()
        }
    }

    @Test
    fun `when Remove To Favorite Success Should true`() = runTest {
        val gameDetail = GameDetail(
            id = 3328,
            name = "The Witcher 3: Wild Hunt",
            released = "2015-05-18",
            backgroundImage = "https://media.rawg.io/media/games/618/618c2031a07bbff6b4f611f10b6bcdbc.jpg",
            developers = listOf(
                Developer(
                    id = 9023,
                    name = "CD PROJEKT RED",
                )
            ),
            genres = listOf(
                Genre(
                    id = 4,
                    name = "Action",
                ),
                Genre(
                    id = 3,
                    name = "Adventure",
                ),
                Genre(
                    id = 5,
                    name = "RPG",
                ),
            ),
            description = "The third game in a series, it holds nothing back from the player. Open world adventures of the renowned monster slayer Geralt of Rivia are now even on a larger scale. Following the source material more accurately, this time Geralt is trying to find the child of the prophecy, Ciri while making a quick coin from various contracts on the side. Great attention to the world building above all creates an immersive story, where your decisions will shape the world around you.\n\nCD Project Red are infamous for the amount of work they put into their games, and it shows, because aside from classic third-person action RPG base game they provided 2 massive DLCs with unique questlines and 16 smaller DLCs, containing extra quests and items.\n\nPlayers praise the game for its atmosphere and a wide open world that finds the balance between fantasy elements and realistic and believable mechanics, and the game deserved numerous awards for every aspect of the game, from music to direction.",
        )

        Mockito.`when`(gameDao.deleteGames(gameDetail.toGameEntity())).thenReturn(Unit)

        gameRepositoryImpl.removeFromFavorite(gameDetail).test {
            Mockito.verify(gameDao).deleteGames(gameDetail.toGameEntity())

            val result = awaitItem()
            assertFalse(result)
            awaitComplete()
        }
    }

    @Test
    fun `when Id In Database Should true`() = runTest {
        val gameEntity = GameEntity(
            id = 3328,
            name = "The Witcher 3: Wild Hunt",
            released = "2015-05-18",
            backgroundImage = "https://media.rawg.io/media/games/618/618c2031a07bbff6b4f611f10b6bcdbc.jpg",
            genres = listOf(
                GenreEntity(
                    id = 4,
                    name = "Action",
                ),
                GenreEntity(
                    id = 3,
                    name = "Adventure",
                ),
                GenreEntity(
                    id = 5,
                    name = "RPG",
                ),
            ),
        )

        Mockito.`when`(gameDao.getGameById(3328)).thenReturn(gameEntity)

        gameRepositoryImpl.checkIsFavorite(3328).test {
            Mockito.verify(gameDao).getGameById(3328)

            val result = awaitItem()
            assertTrue(result)
            awaitComplete()
        }
    }

    @Test
    fun `when Id Not In Database Should true`() = runTest {
        Mockito.`when`(gameDao.getGameById(3328)).thenReturn(null)

        gameRepositoryImpl.checkIsFavorite(3328).test {
            Mockito.verify(gameDao).getGameById(3328)

            val result = awaitItem()
            assertFalse(result)
            awaitComplete()
        }
    }

    @Test
    fun `when Get Game Favorite Success Should Return List Game`() = runTest {
        val listGamesEntity = listOf(
            GameEntity(
                id = 3498,
                name = "Grand Theft Auto V",
                released = "2013-09-17",
                backgroundImage = "https://media.rawg.io/media/games/20a/20aa03a10cda45239fe22d035c0ebe64.jpg",
                genres = listOf(
                    GenreEntity(
                        id = 4,
                        name = "Action"
                    )
                )
            ),
            GameEntity(
                id = 3498,
                name = "Grand Theft Auto V",
                released = "2013-09-17",
                backgroundImage = "https://media.rawg.io/media/games/20a/20aa03a10cda45239fe22d035c0ebe64.jpg",
                genres = listOf(
                    GenreEntity(
                        id = 4,
                        name = "Action"
                    )
                )
            ),
            GameEntity(
                id = 3498,
                name = "Grand Theft Auto V",
                released = "2013-09-17",
                backgroundImage = "https://media.rawg.io/media/games/20a/20aa03a10cda45239fe22d035c0ebe64.jpg",
                genres = listOf(
                    GenreEntity(
                        id = 4,
                        name = "Action"
                    )
                )
            )
        )

        Mockito.`when`(gameDao.getGames()).thenReturn(
            flow {
                emit(listGamesEntity)
            }
        )

        gameRepositoryImpl.getGameFavorite().test {
            Mockito.verify(gameDao).getGames()
            val result = awaitItem()
            assertNotNull(result)
            assertEquals(listGamesEntity.size, result.size)

            awaitComplete()
        }
    }
}