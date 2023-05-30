import com.example.tastypizzaclient.model.request.CreateOrderRequest
import com.example.tastypizzaclient.model.request.OrderItemDto
import com.example.tastypizzaclient.service.MenuService

import org.junit.Before
import org.junit.Test
import org.junit.Assert

class MenuServiceTest {

    private lateinit var menuService: MenuService

    @Before
    fun setup() {
        menuService = MenuService()
    }

    @Test
    fun testGetMenuItems() {


        menuService.getMenuItems() {
            println(it)
            Assert.assertTrue("Меню не должно быть пустым!", it.size > 0)
        }

    }

    @Test
    fun testCheckMenuItem() {
        val menuItemOptionId = 1
        val restaurantId = 2

        menuService.checkMenuItem(menuItemOptionId, restaurantId) {
            Assert.assertTrue(it == 200 || it == 404)
        }

    }

    @Test
    fun testCreateOrder() {

        val listOfOrderItemDto = mutableListOf<OrderItemDto>()

        val orderItemDto = OrderItemDto(
            menuItemOptionId = 1,
            count = 2
        )

        listOfOrderItemDto.add(orderItemDto)

        val createOrderRequest = CreateOrderRequest(
            clientId = 69,
            restaurantId = 1,
            listOfOrderItemDto = listOfOrderItemDto
        )

        menuService.createOrder(createOrderRequest) { result ->
            val orderId = result.first
            val statusCode = result.second

            Assert.assertNotNull(orderId)
            Assert.assertTrue(statusCode == 200 || statusCode == 403 || statusCode == 404)
        }
    }
}