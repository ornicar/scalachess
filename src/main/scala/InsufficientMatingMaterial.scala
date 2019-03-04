package chess

/**
 * Utility methods for helping to determine whether a situation is a draw or a draw
 * on a player flagging.
 *
 * See http://www.e4ec.org/immr.html
 */
object InsufficientMatingMaterial {

  def nonKingPieces(board: Board) = board.pieces filter (_._2.role != King)

  def bishopsOnOppositeColors(board: Board) =
    (board.pieces collect { case (pos, Piece(_, Bishop)) => pos.color } toList).distinct.size == 2

  /*
   * Returns true if a pawn cannot progress forward because it is blocked by a pawn
   */
  def pawnBlockedByPawn(pawn: Actor, board: Board) = pawn.moves.isEmpty && {
    val blockingPosition = Actor.posAheadOfPawn(pawn.pos, pawn.piece.color)
    blockingPosition.flatMap(board.actorAt(_)).exists(_.piece.is(Pawn))
  }

  /*
   * Determines whether a color (of a game in progress) has mating material
   */
  def apply(game: Game, color: Color): Boolean = {
    if (game.situation.color != color) game.situation.insufficientWinningMaterial
    else {
      // Crazyhouse drop moves are not accounted for in game.situation.moves
      val moves = game.situation.moves;
      (!(moves isEmpty)) && (moves.values forall { _ forall { move => game.apply(move).situation.insufficientWinningMaterial } })
    }
  }
}
