package name.abhijitsarkar.moviemanager.service.rip

import org.junit.BeforeClass
import org.junit.Test

import java.util.regex.Matcher

import static name.abhijitsarkar.moviemanager.service.rip.MovieNameMatcherCategory.MOVIE_NAME_WITH_RELEASE_YEAR_REGEX
/**
 * @author Abhijit Sarkar
 */
class MovieNameMatcherCategoryTest {
    @BeforeClass
    static void oneTimeSetUp() {
        Matcher.mixin MovieNameMatcherCategory
    }

    @Test
    void testMatch() {
        Matcher m = 'Casino Royal (2006).mkv' =~ MOVIE_NAME_WITH_RELEASE_YEAR_REGEX
        assert m.matches()

        assert m.title == 'Casino Royal'
        assert m.year == 2006

        m.reset()
        m = '2 Fast 2 Furious - part 1 (2001).mkv' =~ MOVIE_NAME_WITH_RELEASE_YEAR_REGEX
        assert m.matches()

        assert m.title == '2 Fast 2 Furious - part 1'
        assert m.year == 2001
    }

    @Test
    void testNoMatch() {
        Matcher m = 'He-Man - A Friend In Need.avi' =~ MOVIE_NAME_WITH_RELEASE_YEAR_REGEX
        assert !m.matches()
    }
}
