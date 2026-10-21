package com.example

import com.example.data.repository.MuniTourRepository
import com.example.ui.viewmodel.AppTab
import com.example.ui.viewmodel.MuniTourViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class MuniTourAppTest {

    @Test
    fun repository_initializesDataSuccessfully() {
        val repo = MuniTourRepository()
        assertTrue(repo.destinations.isNotEmpty())
        assertEquals("cajica", repo.destinations.first().id)
        assertTrue(repo.places.value.isNotEmpty())
        assertTrue(repo.dishes.value.isNotEmpty())
        assertTrue(repo.events.value.isNotEmpty())
        assertTrue(repo.routes.value.isNotEmpty())
        assertTrue(repo.interMunicipalRoutes.value.isNotEmpty())
    }

    @Test
    fun repository_containsRealCajicaPlacesAndRoutes() {
        val repo = MuniTourRepository()
        val places = repo.places.value
        assertNotNull(places.find { it.id == "helados-san-jeronimo" })
        assertNotNull(places.find { it.id == "fundo-llanero" })
        assertNotNull(places.find { it.id == "level-up" })
        assertNotNull(places.find { it.id == "periland" })

        val interRoutes = repo.interMunicipalRoutes.value
        assertNotNull(interRoutes.find { it.id == "ruta-ferrea" })
        assertNotNull(interRoutes.find { it.id == "ruta-montana" })
    }

    @Test
    fun viewModel_managesAuthAndProfile() {
        val repo = MuniTourRepository()
        val vm = MuniTourViewModel(repo)

        assertTrue(vm.currentUser.value.isLoggedIn)
        vm.updateProfile("Viajero Sabanero", "Cajicá", "☕", "Chemex")
        assertEquals("Viajero Sabanero", vm.currentUser.value.name)
        assertEquals("☕", vm.currentUser.value.avatarEmoji)
        assertEquals("Chemex", vm.currentUser.value.favoriteCoffeeMethod)

        vm.logoutUser()
        assertTrue(!vm.currentUser.value.isLoggedIn)

        vm.loginUser("test@munitour.co", "Carlos Viajero")
        assertTrue(vm.currentUser.value.isLoggedIn)
        assertEquals("Carlos Viajero", vm.currentUser.value.name)
    }

    @Test
    fun viewModel_managesTabsAndFilters() {
        val repo = MuniTourRepository()
        val vm = MuniTourViewModel(repo)

        assertEquals(AppTab.INICIO, vm.currentTab.value)
        vm.setTab(AppTab.MAPA)
        assertEquals(AppTab.MAPA, vm.currentTab.value)

        vm.setMapLayer("cultura")
        assertEquals("cultura", vm.mapLayer.value)

        vm.selectDishCategory("cafe")
        assertEquals("cafe", vm.selectedDishCategory.value)

        val destination = vm.selectedDestination.value
        assertEquals("Cajicá", destination.name)
    }

    @Test
    fun viewModel_allowsBusinessRegistration() {
        val repo = MuniTourRepository()
        val vm = MuniTourViewModel(repo)

        val initialCount = repo.places.value.size
        vm.registerBusiness(
            name = "Café La Montaña",
            kind = "sabor",
            tag = "Café de origen",
            address = "Calle 5 # 3-12",
            coffeeMethods = listOf("V60 Dripper", "Chemex"),
            coffeeOrigin = "Cundinamarca",
            baristaSpecialty = "Tueste claro",
            tableServiceDynamics = "Servicio a la mesa",
            waitTime = "10 min",
            hours = "8am - 8pm",
            priceTier = "$$",
            ownerStory = "Emprendimiento familiar"
        )

        assertEquals(initialCount + 1, repo.places.value.size)
        val added = repo.places.value.find { it.name == "Café La Montaña" }
        assertNotNull(added)
        assertEquals(listOf("V60 Dripper", "Chemex"), added?.operative?.coffeeMethods)
    }
}
