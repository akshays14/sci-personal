package com.sciencescape.ds.db.util;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;

public class RandomText {
	private int _minLength;
	private int _maxLength;
	private Random _random;

	public RandomText(int _minLength, int _maxLength) {
		super();
		this._minLength = _minLength;
		this._maxLength = _maxLength;
		this._random = new Random();
	}

	public RandomText() {
		this(1, 12);
	}

	private int getLength() {
		return (_random.nextInt((_maxLength - _minLength) + 1) + _minLength);
	}

	public String generateText(int length) {
		StringBuilder text = new StringBuilder();
		for (int i = 0; i < length - 1; ++i) {
			text.append(RandomStringUtils.randomAlphabetic(getLength()));
			text.append(" ");
		}
		// last word at the end
		text.append(RandomStringUtils.randomAlphabetic(getLength()));
		return (text.toString());
	}
}