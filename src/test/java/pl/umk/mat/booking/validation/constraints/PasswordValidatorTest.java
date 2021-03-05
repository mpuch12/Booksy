package pl.umk.mat.booking.validation.constraints;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class PasswordValidatorTest {

    @ParameterizedTest
    @CsvSource({"a","aaa","a1","a1A", "Aaaaaaaa", "Aa1", "aaaaaaaa"})
    void checkIfPasswordDontMatchRegex(String password) {
        //given
        PasswordValidator passwordValidator = new PasswordValidator();
        assertThat(passwordValidator.validate(password), is(false));
    }

    @ParameterizedTest
    @CsvSource({"aksjdfhlkajsdA1!","adsfa123sdf!A","asdASDdsa1$2","aaaaEEE3"})
    void checkIfPasswordMatchRegex(String password) {
        //given
        PasswordValidator passwordValidator = new PasswordValidator();
        assertThat(passwordValidator.validate(password), is(true));
    }

}
