package org.example;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

class MyClassTest {

    private final NumberGiver numberGiver = mock(NumberGiver.class);
    private final MyClass myClass = new MyClass(numberGiver);

    @Test
    void shouldDoX() {
        when(numberGiver.giveNumber(argThat(is2()))).thenReturn(new MyNumber(3));
        MyNumber actual = myClass.doIt();
        verify(numberGiver).giveNumber(argThat(is2()));
        assertThat(actual, is3());
    }

    private Matcher<MyNumber> is2() {
        return new TypeSafeMatcher<>() {
            @Override
            protected boolean matchesSafely(MyNumber myNumber) {
                return myNumber.value() == 2;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("isn't 2");

            }
        };
    }

    private Matcher<MyNumber> is3() {
        return new TypeSafeMatcher<>() {
            @Override
            protected boolean matchesSafely(MyNumber integer) {
                return integer.value() == 3;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("isn't 3");
            }
        };
    }
}