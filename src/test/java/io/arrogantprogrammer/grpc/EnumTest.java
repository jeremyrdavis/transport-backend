package io.arrogantprogrammer.grpc;

import io.arrogantprogrammer.domain.MenuItem;
import io.arrogantprogrammer.proto.MenuItemProto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnumTest {

    @Test
    public void testMarshallingFromProtobuf() {

        io.arrogantprogrammer.proto.MenuItemProto menuItemProto = io.arrogantprogrammer.proto.MenuItemProto.SMALL_COFFEE;

        MenuItem menuItem = MenuItem.get(menuItemProto.getNumber());
        assertEquals(MenuItem.SMALL_COFFEE, menuItem);

        io.arrogantprogrammer.proto.MenuItemProto largeCoffeeProto = MenuItemProto.LARGE_COFFEE;
        MenuItem largeCoffee = MenuItem.values()[largeCoffeeProto.getNumber()];
        assertEquals(MenuItem.LARGE_COFFEE, largeCoffee);

    }
}
