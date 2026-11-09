package planets.data.repository

import planets.data.service.PlanetsService
import planets.domain.domain.Planet
import planets.domain.repository.PlanetsRepository
import Result

class PlanetsRepositoryImpl(
    private val service: PlanetsService,
) : PlanetsRepository {

    override fun getPlanets(): Result<List<Planet>> {
        return try {
            val planets = service.getPlanets()
            planets
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override fun getPlanetById(planetId: Int): Planet? {
        return service.getPlanetById(planetId)
    }

}