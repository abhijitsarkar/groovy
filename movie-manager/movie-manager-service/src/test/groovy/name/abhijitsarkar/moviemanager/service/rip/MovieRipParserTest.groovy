package name.abhijitsarkar.moviemanager.service.rip

import name.abhijitsarkar.moviemanager.domain.MovieRip
import org.junit.Test

import static name.abhijitsarkar.moviemanager.service.rip.MovieRipParser.fileExtension
import static name.abhijitsarkar.moviemanager.service.rip.MovieRipParser.parseMovieRip
import static name.abhijitsarkar.moviemanager.service.rip.MovieRipParser.releaseDate

/**
 * @author Abhijit Sarkar
 */
class MovieRipParserTest {
    @Test
    void testFileExtension() {
        assert fileExtension('a.mkv') == '.mkv'
        assert fileExtension('.mkv') == '.mkv'
        assert fileExtension('a') == ''
    }

    @Test
    void testMinusFileExtension() {
        assert 'a.mkv'.minus('.mkv') == 'a'
        assert 'a.mkv'.minus('a.mkv') == ''
        assert '.mkv'.minus('.mkv') == ''
        assert 'a'.minus('') == 'a'
    }

    @Test
    void testReleaseDate() {
        Calendar c = new GregorianCalendar(2010, Calendar.JANUARY, 1)

        assert releaseDate(2010) == c.time
    }

    @Test
    void testParseMovieRip() {
        MovieRip mr = parseMovieRip('Casino Royal (2006).mkv')
        assert mr.title == 'Casino Royal'
        assert mr.releaseDate[Calendar.YEAR] == 2006
        assert mr.fileExtension == '.mkv'

        mr = parseMovieRip('2 Fast 2 Furious - part 1 (2001).mkv')

        assert mr.title == '2 Fast 2 Furious - part 1'
        assert mr.releaseDate[Calendar.YEAR] == 2001
        assert mr.fileExtension == '.mkv'

        mr = parseMovieRip('He-Man - A Friend In Need.avi')

        assert mr.title == 'He-Man - A Friend In Need'
        assert mr.releaseDate[Calendar.YEAR] == 1
        assert mr.fileExtension == '.avi'
    }
}
