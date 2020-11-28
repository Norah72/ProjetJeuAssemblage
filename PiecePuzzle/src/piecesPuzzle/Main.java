package piecesPuzzle;

import piecesPuzzle.pieces.*;
import java.util.*;

public class Main {

	public static void main(String[] args){
            PieceT pt = new PieceT(3,3);
			PieceT pt1 = new PieceT(3,3,1);
			PieceT pt2 = new PieceT(3,3,2);
			PieceT pt3 = new PieceT(3,3,3);
			System.out.println(pt);
			System.out.println(pt1);
			System.out.println(pt2);
			System.out.println(pt3);
			System.out.println("\n-------------------\n");
			PieceT pte = new PieceT(4,3);
			PieceT pte1 = new PieceT(4,3,1);
			PieceT pte2 = new PieceT(4,3,2);
			PieceT pte3 = new PieceT(4,3,3);
			System.out.println(pte);
			System.out.println(pte1);
			System.out.println(pte2);
			System.out.println(pte3);
			System.out.println("\n-------------------\n");
			PieceT ptz = new PieceT(3,4);
			PieceT ptz1 = new PieceT(3,4,1);
			PieceT ptz2 = new PieceT(3,4,2);
			PieceT ptz3 = new PieceT(3,4,3);
			System.out.println(ptz);
			System.out.println(ptz1);
			System.out.println(ptz2);
			System.out.println(ptz3);
	}
}

