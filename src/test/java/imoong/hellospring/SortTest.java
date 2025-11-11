package imoong.hellospring;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SortTest {

    Sort sort;

    @BeforeEach
    void init() {
        sort = new Sort();
    }

    @Test
    void sort() {
        List<String> list = sort.sortByLength(Arrays.asList("abcrrr", "aaaee", "bbbc"));

        assertThat(list).isEqualTo(List.of("bbbc", "aaaee", "abcrrr"));
    }
}