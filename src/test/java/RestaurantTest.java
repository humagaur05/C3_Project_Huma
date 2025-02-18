import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})
class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    public void setup() {
        //Arrange
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("Tomato soup", 150);
    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE

    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //Arrange
        Restaurant spyRestaurant = Mockito.spy(restaurant);
        Mockito.doReturn(LocalTime.parse("10:35:00")).when(spyRestaurant).getCurrentTime();
        //Act
        Boolean isRestaurantOpen = spyRestaurant.isRestaurantOpen();
        //Assert
        assertTrue(isRestaurantOpen);

        //Arrange
        Mockito.doReturn(LocalTime.parse("21:55:00")).when(spyRestaurant).getCurrentTime();
        //Act
        isRestaurantOpen = spyRestaurant.isRestaurantOpen();
        //Assert
        assertTrue(isRestaurantOpen);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        //Arrange
        Restaurant spyRestaurant = Mockito.spy(restaurant);
        Mockito.doReturn(LocalTime.parse("10:00:00")).when(spyRestaurant).getCurrentTime();
        //Act
        Boolean isRestaurantOpen = spyRestaurant.isRestaurantOpen();
        //Assert
        assertFalse(isRestaurantOpen);

        //Arrange
        Mockito.doReturn(LocalTime.parse("22:05:00")).when(spyRestaurant).getCurrentTime();
        //Act
        isRestaurantOpen = spyRestaurant.isRestaurantOpen();
        //Assert
        assertFalse(isRestaurantOpen);
    }


    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>ORDER<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void calculate_order_value_when_zero_items_are_present_in_order() {
        //Arrange
        ArrayList<String> orderItems = new ArrayList<String>();
        //Act
        int valueOfOrder = restaurant.calculateOrderValue(orderItems);
        //Assert
        assertEquals(0, valueOfOrder);
    }

    @Test
    public void calculate_order_value_when_single_item_is_present_in_order() {
        //Arrange
        ArrayList<String> orderItems = new ArrayList<String>();
        orderItems.add("Sweet corn soup");
        //Act
        int valueOfOrder = restaurant.calculateOrderValue(orderItems);
        //Assert
        assertEquals(119, valueOfOrder);
    }

    @Test
    public void calculate_order_value_when_multiple_items_are_present_in_order() {
        //Arrange
        ArrayList<String> orderItems = new ArrayList<String>();
        orderItems.add("Tomato soup");
        orderItems.add("Vegetable lasagne");
        //Act
        int valueOfOrder = restaurant.calculateOrderValue(orderItems);
        //Assert
        assertEquals(419, valueOfOrder);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<ORDER>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}