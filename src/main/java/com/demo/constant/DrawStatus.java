package com.demo.constant;

public enum DrawStatus {

	ACTIVE, COMPLETED;

	public static boolean contains(String status) {
		for (DrawStatus drawStatus : DrawStatus.values()) {
			if (drawStatus.name().equals(status)) {
				return true;
			}
		}
		return false;
	}

}
