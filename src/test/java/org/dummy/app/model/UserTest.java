package org.dummy.app.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class UserTest
{
    @Test
    void equalsAndHashCodeTest()
    {
        // Arrange
        User u1 = new User(1,"nsalez");
        User u2 = new User(3,"nsalez");
        User u3 = new User(3,"naysson");

        // Act && Assert
        assertNotEquals(u1, u2);
        assertNotEquals( u1.hashCode(), u2.hashCode());

        assertNotEquals(u1, u3);
        assertNotEquals( u1.hashCode(), u3.hashCode());

        assertEquals(u2, u3);
        assertEquals( u2.hashCode(), u3.hashCode());


    }
}
