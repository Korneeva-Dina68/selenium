package helpers;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class DataProvider {
    public static Stream<Arguments> providerCheckingSearchResult(){
        return Stream.of(
                Arguments.of("Яндекс", "Ноутбуки и компьютеры", "Ноутбуки", "10000", "30000", "Lenovo", "HP")
        );
    }
}
