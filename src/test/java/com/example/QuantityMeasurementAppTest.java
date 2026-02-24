package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.example.QuantityLength;
import com.example.QuantityLength.LengthUnit;

public class QuantityMeasurementAppTest {

	@Test
	void testEquality_YardToYard_SameValue() {
		QuantityLength yard1 = new QuantityLength(1.0,LengthUnit.YARDS);
		QuantityLength yard2 = new QuantityLength(1.0,LengthUnit.YARDS);
		assertTrue(yard1.equals(yard2));
	}
	
	@Test
	void testEquality_YardToYard_DifferentValue() {
		QuantityLength yard1 = new QuantityLength(1.0,LengthUnit.YARDS);
		QuantityLength yard2 = new QuantityLength(2.0,LengthUnit.YARDS);
		assertFalse(yard1.equals(yard2));
	}
	
	@Test
	void testEquality_YardToFeet_EquivalentValue() {
		QuantityLength yard = new QuantityLength(1.0,LengthUnit.YARDS);
		QuantityLength feet = new QuantityLength(3.0,LengthUnit.FEET);
		assertEquals(yard,feet);
		
	}
	
	@Test
	void testEquality_FeetToYard_EquivalentValue() {
		QuantityLength yard = new QuantityLength(1.0,LengthUnit.YARDS);
		QuantityLength feet = new QuantityLength(3.0,LengthUnit.FEET);
		assertEquals(feet,yard);
		
	}
	
	@Test 
	void testEquality_YardToInches_EquivalentValue() {
		QuantityLength yard = new QuantityLength(1.0,LengthUnit.YARDS);
		QuantityLength inches = new QuantityLength(36.0,LengthUnit.INCHES);
		assertEquals(yard,inches);
	
	}
	
	@Test
	void testEquality_InchesToYard_EquivalentValue(){
		QuantityLength yard = new QuantityLength(1.0,LengthUnit.YARDS);
		QuantityLength inches = new QuantityLength(36.0,LengthUnit.INCHES);
		assertEquals(inches,yard);
	}

	@Test
	void testEquality_YardToFeet_NonEquivalentValue() {
		QuantityLength yard = new QuantityLength(1.0,LengthUnit.YARDS);
		QuantityLength feet = new QuantityLength(2.0,LengthUnit.FEET);
		assertNotEquals(yard,feet);	
	}
	
	@Test
	void testEquality_centimetersToInches_EquivalentValue() {
		QuantityLength centimeters = new QuantityLength(1.0,LengthUnit.CENTIMETERS);
		QuantityLength inches = new QuantityLength(0.393701,LengthUnit.INCHES);
		assertEquals(centimeters,inches);	
	}
	
	@Test
	void testEquality_centimetersToFeet_NonEquivalentValue() {
		QuantityLength centimeters = new QuantityLength(1.0,LengthUnit.CENTIMETERS);
		QuantityLength inches = new QuantityLength(1.0,LengthUnit.FEET);
		assertNotEquals(centimeters,inches);
	}
	
	@Test
	void testEquality_MultiUnit_TransitiveProperty() {
		QuantityLength yard = new QuantityLength(1.0,LengthUnit.YARDS);
		QuantityLength feet = new QuantityLength(3.0, LengthUnit.FEET);
		QuantityLength inches= new QuantityLength(36.0,LengthUnit.INCHES);
		assertEquals(yard,feet);
		assertEquals(feet,inches);
		assertEquals(yard,inches);
	}
	
	@Test
	void testEquality_YardWithNullUnit() {
		assertThrows(IllegalArgumentException.class, () -> {
			new QuantityLength(1.0,null);
		});
	}
	
	@Test
	void testEquality_YardSameReference() {
		QuantityLength yard = new QuantityLength(2.0,LengthUnit.YARDS);
		assertTrue(yard.equals(yard));
	}
	
	@Test
	void testEquality_YardNullComparison() {
		QuantityLength yard = new QuantityLength(1.0,LengthUnit.YARDS);
		assertFalse(yard.equals(null));
	}
	
	@Test
	void testEquality_CentimetersWithNullUnit() {
		assertThrows(IllegalArgumentException.class, 
				() -> { new QuantityLength(2.0,null);});
	}
	
	@Test
	void testEquality_CentimetersSameReference() {
		QuantityLength centimeter = new QuantityLength(2.0,LengthUnit.CENTIMETERS);
		assertTrue(centimeter.equals(centimeter));
	}
	
	@Test
	void testEquality_CentimeterdNullComparison() {
		QuantityLength centimeter = new QuantityLength(1.0,LengthUnit.CENTIMETERS);
		assertFalse(centimeter.equals(null));
	}
	
	@Test
	void testEquality_AllUnits_ComplexScenario() {
		QuantityLength yard = new QuantityLength(2.0,LengthUnit.YARDS);
		QuantityLength feet = new QuantityLength(6.0, LengthUnit.FEET);
		QuantityLength inches= new QuantityLength(72.0,LengthUnit.INCHES);
		assertEquals(yard,feet);
		assertEquals(feet,inches);
		assertEquals(yard,inches);
	}
	
	@Test
	void testEquality_CentimetersToCentimeters_SameValue() {
		QuantityLength cm2a = new QuantityLength(2.0, QuantityLength.LengthUnit.CENTIMETERS);
		QuantityLength cm2b = new QuantityLength(2.0, QuantityLength.LengthUnit.CENTIMETERS);
		assertEquals(cm2a, cm2b);
	}

	@Test
	 void testEquality_CentimetersToCentimeters_DifferentValue() {
		QuantityLength cm2 = new QuantityLength(2.0, QuantityLength.LengthUnit.CENTIMETERS);
		QuantityLength cm3 = new QuantityLength(3.0, QuantityLength.LengthUnit.CENTIMETERS);
		assertNotEquals(cm2, cm3);
	}

	@Test
	void testEquality_InchesToCentimeters_EquivalentValue() {
		QuantityLength inchValue = new QuantityLength(0.393701, QuantityLength.LengthUnit.INCHES);
		QuantityLength cm1 = new QuantityLength(1.0, QuantityLength.LengthUnit.CENTIMETERS);
		assertEquals(inchValue, cm1);
	}


	@Test
	void testEquality_DifferentClass() {
		QuantityLength yard = new QuantityLength(2.0, QuantityLength.LengthUnit.YARDS);
		assertFalse(yard.equals("2.0"));
	}
	
	@Test
	void testEquality_NaN() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength(Double.NaN, QuantityLength.LengthUnit.FEET));
	}

	@Test
	void testEquality_Infinity() {
		assertThrows(IllegalArgumentException.class,
				() -> new QuantityLength(Double.POSITIVE_INFINITY, QuantityLength.LengthUnit.INCHES));
	}
	
	@Test
	void testEquality_DemonstrateLengthComparisonMethod() {
		boolean result = QuantityMeasurementApp.demonstrateLengthComparison(1.0, QuantityLength.LengthUnit.YARDS, 36.0, QuantityLength.LengthUnit.INCHES);
		assertEquals(result,true);
	}
	
	// UC5 Test Cases
	@Test
	void testConversion_FeetToInches() {
		QuantityLength result = QuantityMeasurementApp.demonstrateLengthConversion(1.0, QuantityLength.LengthUnit.FEET, QuantityLength.LengthUnit.INCHES);
		QuantityLength expected = new QuantityLength(12.0, QuantityLength.LengthUnit.INCHES);
        assertEquals(expected, result);
	}
	
	@Test
    public void testConversion_InchesToFeet() {
		QuantityLength result = QuantityMeasurementApp.demonstrateLengthConversion(24.0, QuantityLength.LengthUnit.INCHES, QuantityLength.LengthUnit.FEET);
		QuantityLength expected = new QuantityLength(2.0, QuantityLength.LengthUnit.FEET);
        assertEquals(expected, result);
    }

    @Test
    public void testConversion_YardsToInches() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthConversion(1.0, QuantityLength.LengthUnit.YARDS, QuantityLength.LengthUnit.INCHES);
    	QuantityLength expected = new QuantityLength(36.0, QuantityLength.LengthUnit.INCHES);
        assertEquals(expected, result);
    }

    @Test
    public void testConversion_InchesToYards() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthConversion(72.0, QuantityLength.LengthUnit.INCHES, QuantityLength.LengthUnit.YARDS);
    	QuantityLength expected = new QuantityLength(2.0, QuantityLength.LengthUnit.YARDS);
        assertEquals(expected, result);
    }

    @Test
    public void testConversion_CentimetersToInches() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthConversion(2.54, QuantityLength.LengthUnit.CENTIMETERS, QuantityLength.LengthUnit.INCHES);
    	QuantityLength expected = new QuantityLength(1.0, QuantityLength.LengthUnit.INCHES);
        assertTrue(result.equals(expected));
    }

    @Test
    public void testConversion_FeetToYards() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthConversion(6.0, QuantityLength.LengthUnit.FEET, QuantityLength.LengthUnit.YARDS);
    	QuantityLength expected = new QuantityLength(2.0, QuantityLength.LengthUnit.YARDS);
        assertEquals(expected, result);
    }

    @Test
    public void testConversion_RoundTrip_PreservesValue() {
    	QuantityLength original = new QuantityLength(3.0, QuantityLength.LengthUnit.FEET);
        QuantityLength converted = original.convertTo(QuantityLength.LengthUnit.INCHES).convertTo(QuantityLength.LengthUnit.FEET);
        assertTrue(original.equals(converted));
    }

    @Test
    public void testConversion_ZeroValue() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthConversion(0.0, QuantityLength.LengthUnit.FEET,QuantityLength.LengthUnit.INCHES);
    	QuantityLength expected = new QuantityLength(0.0, QuantityLength.LengthUnit.INCHES);
        assertEquals(expected, result);
    }

    @Test
    public void testConversion_NegativeValue() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthConversion(-1.0, QuantityLength.LengthUnit.FEET, QuantityLength.LengthUnit.INCHES);
        QuantityLength expected = new QuantityLength(-12.0, QuantityLength.LengthUnit.INCHES);
        assertEquals(expected, result);
    }

    @Test
    public void testConversion_InvalidUnit_Throws() {
        assertThrows(
    		IllegalArgumentException.class, 
    		() -> QuantityMeasurementApp.demonstrateLengthConversion(1.0, null, QuantityLength.LengthUnit.INCHES)
    	);
    }

    @Test
    public void testConversion_NaNOrInfinite_Throws() {
        assertThrows(
    		IllegalArgumentException.class, 
    		() -> new QuantityLength(Double.NaN, QuantityLength.LengthUnit.FEET)
    	);
        assertThrows(
    		IllegalArgumentException.class, 
    		() -> new QuantityLength(Double.POSITIVE_INFINITY, QuantityLength.LengthUnit.INCHES)
		);
    }

    @Test
    public void testConversion_PrecisionTolerance() {
        double result = QuantityMeasurementApp.convert(30.48, QuantityLength.LengthUnit.CENTIMETERS, QuantityLength.LengthUnit.FEET);
        double expected = 1.0;
        assertTrue(Math.abs(result - expected) < 1e-6, "Conversion should be within precision tolerance");
    }

    
    @Test
    public void testConversion_SameUnit() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthConversion(5.0, QuantityLength.LengthUnit.FEET, QuantityLength.LengthUnit.FEET);
    	QuantityLength expected = new QuantityLength(5.0, QuantityLength.LengthUnit.FEET);
        assertEquals(expected, result);
    }
}
