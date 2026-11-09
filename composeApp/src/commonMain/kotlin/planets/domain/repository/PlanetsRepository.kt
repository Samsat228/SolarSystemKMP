package planets.domain.repository

import planets.domain.domain.Planet
import Result

interface PlanetsRepository {
    fun getPlanets(): Result<List<Planet>>
    fun getPlanetById(planetId: Int): Planet?
}