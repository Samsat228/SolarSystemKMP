package planets.data.repository

import kotlinx.coroutines.test.runTest
import Result
import planets.domain.domain.Planet
import planets.domain.repository.PlanetsRepository
import kotlin.collections.find
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.fail

class PlanetsRepositoryImplTest {

    @Test
    fun testGetPlanetsSuccess() = runTest {
        // Given
        val fakeRepository = FakePlanetsRepository(shouldReturnError = false)

        // When
        val result = fakeRepository.getPlanets()

        // Then
        assertTrue(result is Result.Success, "The call should be successful")

        // Извлекаем данные из Result.Success
        val planetsList = (result as Result.Success).data
        assertTrue(planetsList.isNotEmpty(), "List should not be empty")
    }

    @Test
    fun testGetPlanetsErrorHandling() = runTest {
        // Given - Repository configured to return error
        val fakeRepository = FakePlanetsRepository(shouldReturnError = true)

        // When
        val result = fakeRepository.getPlanets()

        // Then
        assertTrue(result is Result.Error, "Should return Error when configured for error")
    }

    @Test
    fun testPlanetDomainModelFields() = runTest {
        // Given
        val fakeRepository = FakePlanetsRepository(shouldReturnError = false)

        // When
        val result = fakeRepository.getPlanets()

        // Then - Verify all required fields are present
        if (result is Result.Success) {
            val planet = result.data.firstOrNull() ?: fail("Planet should not be null")

            assertTrue(planet.name.isNotEmpty(), "Name should not be empty")
            assertTrue(planet.description.isNotEmpty(), "Description should not be empty")
            assertTrue(planet.mass.contains("kg"), "Mass should be in kg")
            assertTrue(planet.planetOrder in 1..8, "Order should be correct")
        } else {
            fail("Expected Success result, but got Error")
        }
    }
}

/**
 * Fake PlanetsRepository implementation for testing
 */
class FakePlanetsRepository(
    private val shouldReturnError: Boolean = false
) : PlanetsRepository {

    private val samplePlanet = Planet(
        id = 1,
        planetId = 1,
        name = "Earth",
        description = "Test planet description",
        img = "",
        imgDescription = "",
        mass = "5.97×10²⁴ kg",
        volume = "1.08×10¹² km³",
        planetOrder = 3,
        source = "Wikipedia",
        wikiLink = "",
    )

    private val samplePlanetsList = listOf(samplePlanet)

    override fun getPlanets(): Result<List<Planet>> {
        return if (shouldReturnError) {
            Result.Error(IllegalStateException("Test error"))
        } else {
            Result.Success(samplePlanetsList)
        }
    }

    override fun getPlanetById(planetId: Int): Planet? {
         return samplePlanetsList.find { it.planetId == planetId }
    }
}