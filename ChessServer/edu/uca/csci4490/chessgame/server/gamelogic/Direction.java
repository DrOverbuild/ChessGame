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
	L_N2_E3,
	L_N3_E2,
	L_S2_E3,
	L_S3_E2,
	L_N2_W3,
	L_N3_W2,
	L_S2_W3,
	L_S3_W2;

	public boolean isLShaped() {
		return this == L_N2_E3 ||
				this == L_N3_E2 ||
				this == L_S2_E3 ||
				this == L_S3_E2 ||
				this == L_N2_W3 ||
				this == L_N3_W2 ||
				this == L_S2_W3 ||
				this == L_S3_W2;

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
