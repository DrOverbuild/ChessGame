package edu.uca.csci4490.chessgame.server.gamelogic;

public enum Direction {
	NORTH,
	SOUTH,
	EAST,
	WEST,
	NORTHEAST,
	SOUTHEAST,
	NORTHWEST,
	SOUTHWEST,
	L_N1_E2,
	L_N2_E1,
	L_S1_E2,
	L_S2_E1,
	L_N1_W2,
	L_N2_W1,
	L_S1_W2,
	L_S2_W1;

	public boolean isLShaped() {
		return this == L_N1_E2 ||
				this == L_N2_E1 ||
				this == L_S1_E2 ||
				this == L_S2_E1 ||
				this == L_N1_W2 ||
				this == L_N2_W1 ||
				this == L_S1_W2 ||
				this == L_S2_W1;

	}

	public boolean isDiagonal() {
		return this == NORTHEAST ||
				this == SOUTHEAST ||
				this == NORTHWEST ||
				this == SOUTHWEST;
	}

	public boolean isStraight() {
		return this == NORTH ||
				this == SOUTH ||
				this == EAST ||
				this == WEST;
	}
}
