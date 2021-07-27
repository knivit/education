package com.tsoft.demo.map;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class MapTest {

    @Test
    public void noEquals() {
        @Setter
        @Accessors(chain = true)
        class X {
            int val;
        }

        X x1 = new X().setVal(1);
        X x2 = new X().setVal(1);

        Map<X, String> map = new HashMap<>();
        map.put(x1, "one");
        map.put(x2, "one");
        assertThat(map.size()).isEqualTo(2);
    }

    @Test
    public void withEquals() {
        @Setter
        @EqualsAndHashCode
        @Accessors(chain = true)
        class X {
            int val;
        }

        X x1 = new X().setVal(1);
        X x2 = new X().setVal(1);

        Map<X, String> map = new HashMap<>();
        map.put(x1, "one");
        map.put(x2, "one");
        assertThat(map.size()).isEqualTo(1);
    }
}
