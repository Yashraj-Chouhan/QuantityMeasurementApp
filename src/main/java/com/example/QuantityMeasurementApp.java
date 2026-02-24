package com.example;

public class QuantityMeasurementApp {
	
	public static class Feet {
		private final double value;

		public Feet(double value) {
			this.value = value;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}

			if (obj == null || this.getClass() != obj.getClass()) {
				return false;
			}

			Feet other = (Feet) obj;
			return Double.compare(this.value, other.value) == 0;
		}
	}

	public static void main(String[] args) {
		Feet feet1 = new Feet(1.0);
		Feet feet2 = new Feet(1.0);

		System.out.println("Are they equal? " + feet1.equals(feet2));
	}
}