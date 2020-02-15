import chess.{ ChessTest, Mode }
import chess.Mode._

class ModeTest extends ChessTest {

  private val invalidId = 42

  "Mode" should {
    "either be Casual or Rated" in {
      Casual.casual must_== true
      Casual.rated must_== false

      Rated.casual must_== false
      Rated.rated must_== true
    }

    "fold based on type" in {
      Casual.fold(true, false) must_== true
      Rated.fold(true, false) must_== false
    }

    "find a Mode by ID" in {
      Mode(0).get must_== Casual
      Mode(1).get must_== Rated
    }

    "return None if the Mode cannot be found" in {
      Mode(invalidId) must beNone
    }

    "find a Mode based on whether it is rated or not" in {
      Mode(true) must_== Rated
      Mode(false) must_== Casual
    }

    "have a default Mode of Casual" in {
      Mode orDefault invalidId must_== Casual
    }
  }
}
