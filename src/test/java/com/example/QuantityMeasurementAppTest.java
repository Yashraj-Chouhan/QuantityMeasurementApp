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
	public void testEquality_YardToYard_SameValue() {
		QuantityLength yard1 = new QuantityLength(1.0,LengthUnit.YARDS);
		QuantityLength yard2 = new QuantityLength(1.0,LengthUnit.YARDS);
		assertTrue(yard1.equals(yard2));
	}
	
	@Test
	public void testEquality_YardToYard_DifferentValue() {
		QuantityLength yard1 = new QuantityLength(1.0,LengthUnit.YARDS);
		QuantityLength yard2 = new QuantityLength(2.0,LengthUnit.YARDS);
		assertFalse(yard1.equals(yard2));
	}
	
	@Test
	public void testEquality_YardToFeet_EquivalentValue() {
		QuantityLength yard = new QuantityLength(1.0,LengthUnit.YARDS);
		QuantityLength feet = new QuantityLength(3.0,LengthUnit.FEET);
		assertEquals(yard,feet);
		
	}
	
	@Test
	public void testEquality_FeetToYard_EquivalentValue() {
		QuantityLength yard = new QuantityLength(1.0,LengthUnit.YARDS);
		QuantityLength feet = new QuantityLength(3.0,LengthUnit.FEET);
		assertEquals(feet,yard);
		
	}
	
	@Test 
	public void testEquality_YardToInches_EquivalentValue() {
		QuantityLength yard = new QuantityLength(1.0,LengthUnit.YARDS);
		QuantityLength inches = new QuantityLength(36.0,LengthUnit.INCHES);
		assertEquals(yard,inches);
	
	}
	
	@Test
	public void testEquality_InchesToYard_EquivalentValue(){
		QuantityLength yard = new QuantityLength(1.0,LengthUnit.YARDS);
		QuantityLength inches = new QuantityLength(36.0,LengthUnit.INCHES);
		assertEquals(inches,yard);
	}

	@Test
	public void testEquality_YardToFeet_NonEquivalentValue() {
		QuantityLength yard = new QuantityLength(1.0,LengthUnit.YARDS);
		QuantityLength feet = new QuantityLength(2.0,LengthUnit.FEET);
		assertNotEquals(yard,feet);	
	}
	
	@Test
	public void testEquality_centimetersToInches_EquivalentValue() {
		QuantityLength centimeters = new QuantityLength(1.0,LengthUnit.CENTIMETERS);
		QuantityLength inches = new QuantityLength(0.393701,LengthUnit.INCHES);
		assertEquals(centimeters,inches);	
	}
	
	@Test
	public void testEquality_centimetersToFeet_NonEquivalentValue() {
		QuantityLength centimeters = new QuantityLength(1.0,LengthUnit.CENTIMETERS);
		QuantityLength inches = new QuantityLength(1.0,LengthUnit.FEET);
		assertNotEquals(centimeters,inches);
	}
	
	@Test
	public void testEquality_MultiUnit_TransitiveProperty() {
		QuantityLength yard = new QuantityLength(1.0,LengthUnit.YARDS);
		QuantityLength feet = new QuantityLength(3.0, LengthUnit.FEET);
		QuantityLength inches= new QuantityLength(36.0,LengthUnit.INCHES);
		assertEquals(yard,feet);
		assertEquals(feet,inches);
		assertEquals(yard,inches);
	}
	
	@Test
	public void testEquality_YardWithNullUnit() {
		assertThrows(IllegalArgumentException.class, () -> {
			new QuantityLength(1.0,null);
		});
	}
	
	@Test
	public void testEquality_YardSameReference() {
		QuantityLength yard = new QuantityLength(2.0,LengthUnit.YARDS);
		assertTrue(yard.equals(yard));
	}
	
	@Test
	public void testEquality_YardNullComparison() {
		QuantityLength yard = new QuantityLength(1.0,LengthUnit.YARDS);
		assertFalse(yard.equals(null));
	}
	
	@Test
	public void testEquality_CentimetersWithNullUnit() {
		assertThrows(IllegalArgumentException.class, 
				() -> { new QuantityLength(2.0,null);});
	}
	
	@Test
	public void testEquality_CentimetersSameReference() {
		QuantityLength centimeter = new QuantityLength(2.0,LengthUnit.CENTIMETERS);
		assertTrue(centimeter.equals(centimeter));
	}
	
	@Test
	public void testEquality_CentimeterdNullComparison() {
		QuantityLength centimeter = new QuantityLength(1.0,LengthUnit.CENTIMETERS);
		assertFalse(centimeter.equals(null));
	}
	
	@Test
	public void testEquality_AllUnits_ComplexScenario() {
		QuantityLength yard = new QuantityLength(2.0,LengthUnit.YARDS);
		QuantityLength feet = new QuantityLength(6.0, LengthUnit.FEET);
		QuantityLength inches= new QuantityLength(72.0,LengthUnit.INCHES);
		assertEquals(yard,feet);
		assertEquals(feet,inches);
		assertEquals(yard,inches);
	}
	
	@Test
	public void testEquality_CentimetersToCentimeters_SameValue() {
		QuantityLength cm2a = new QuantityLength(2.0, QuantityLength.LengthUnit.CENTIMETERS);
		QuantityLength cm2b = new QuantityLength(2.0, QuantityLength.LengthUnit.CENTIMETERS);
		assertEquals(cm2a, cm2b);
	}

	@Test
	public void testEquality_CentimetersToCentimeters_DifferentValue() {
		QuantityLength cm2 = new QuantityLength(2.0, QuantityLength.LengthUnit.CENTIMETERS);
		QuantityLength cm3 = new QuantityLength(3.0, QuantityLength.LengthUnit.CENTIMETERS);
		assertNotEquals(cm2, cm3);
	}

	@Test
	public void testEquality_InchesToCentimeters_EquivalentValue() {
		QuantityLength inchValue = new QuantityLength(0.393701, QuantityLength.LengthUnit.INCHES);
		QuantityLength cm1 = new QuantityLength(1.0, QuantityLength.LengthUnit.CENTIMETERS);
		assertEquals(inchValue, cm1);
	}


	@Test
	public void testEquality_DifferentClass() {
		QuantityLength yard = new QuantityLength(2.0, QuantityLength.LengthUnit.YARDS);
		assertFalse(yard.equals("2.0"));
	}
	
	@Test
	public void testEquality_NaN() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength(Double.NaN, QuantityLength.LengthUnit.FEET));
	}

	@Test
	public void testEquality_Infinity() {
		assertThrows(IllegalArgumentException.class,
				() -> new QuantityLength(Double.POSITIVE_INFINITY, QuantityLength.LengthUnit.INCHES));
	}
	
	@Test
	public void testEquality_DemonstrateLengthComparisonMethod() {
		boolean result = QuantityMeasurementApp.demonstrateLengthComparison(1.0, QuantityLength.LengthUnit.YARDS, 36.0, QuantityLength.LengthUnit.INCHES);
		assertEquals(result,true);
	}
	
	// UC5 Test Cases
	@Test
	public void testConversion_FeetToInches() {
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
    
    @Test
    public void testAddition_SameUnit_FeetPlusFeet() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
    			new QuantityLength(1.0,QuantityLength.LengthUnit.FEET),	
    			new QuantityLength(2.0,QuantityLength.LengthUnit.FEET)
    	);	
    	QuantityLength expected = new QuantityLength(3.0, QuantityLength.LengthUnit.FEET);
        assertEquals(expected, result);
    }
    
    @Test
    public void testAddition_SameUnit_InchPlusInch() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
    			new QuantityLength(6.0,QuantityLength.LengthUnit.INCHES),	
    			new QuantityLength(6.0,QuantityLength.LengthUnit.INCHES)
    	);	
    	QuantityLength expected = new QuantityLength(12.0, QuantityLength.LengthUnit.INCHES);
        assertEquals(expected, result);
    }
    
    @Test
    public void testAddition_CrossUnit_FeetPlusInches() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
    			new QuantityLength(1.0,QuantityLength.LengthUnit.FEET),	
    			new QuantityLength(12.0,QuantityLength.LengthUnit.INCHES)
    	);	
    	QuantityLength expected = new QuantityLength(2.0, QuantityLength.LengthUnit.FEET);
        assertEquals(expected, result);
    }
    
    @Test
    public void testAddition_CrossUnit_InchPlusFeet() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
    			new QuantityLength(12.0,QuantityLength.LengthUnit.INCHES),
    			new QuantityLength(1.0,QuantityLength.LengthUnit.FEET)
    	);	
    	QuantityLength expected = new QuantityLength(24.0, QuantityLength.LengthUnit.INCHES);
        assertEquals(expected, result);
    }
    
    @Test
    public void testAddition_CrossUnit_YardPlusFeet() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
    			new QuantityLength(1.0,QuantityLength.LengthUnit.YARDS),
    			new QuantityLength(3.0,QuantityLength.LengthUnit.FEET)
    	);	
    	QuantityLength expected = new QuantityLength(2.0, QuantityLength.LengthUnit.YARDS);
        assertEquals(expected, result);
    }
    
    @Test
    public void testAddition_CrossUnit_CentimeterPlusInch() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
    			new QuantityLength(2.54,QuantityLength.LengthUnit.CENTIMETERS),
    			new QuantityLength(1.0,QuantityLength.LengthUnit.INCHES)
    	);	
    	QuantityLength expected = new QuantityLength(5.08, QuantityLength.LengthUnit.CENTIMETERS);
        assertEquals(expected, result);
    }
    
    @Test
    public void testAddition_Commutativity() {
    	QuantityLength result1 = QuantityMeasurementApp.demonstrateLengthAddition(
    			new QuantityLength(1.0,QuantityLength.LengthUnit.FEET),	
    			new QuantityLength(12.0,QuantityLength.LengthUnit.INCHES)
    	);	
    	QuantityLength result2 = QuantityMeasurementApp.demonstrateLengthAddition(
    			new QuantityLength(12.0,QuantityLength.LengthUnit.INCHES),
    			new QuantityLength(1.0,QuantityLength.LengthUnit.FEET)
    	);
        assertEquals(result1, result2);
    }
    
    @Test
    public void testAddition_WithZero() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
    			new QuantityLength(5.0,QuantityLength.LengthUnit.FEET),	
    			new QuantityLength(0.0,QuantityLength.LengthUnit.INCHES)
    	);	
    	QuantityLength expected = new QuantityLength(5.0, QuantityLength.LengthUnit.FEET);
        assertEquals(expected, result);
    }
    
    @Test
    public void testAddition_NegativeValues() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
    			new QuantityLength(5.0,QuantityLength.LengthUnit.FEET),	
    			new QuantityLength(-2.0,QuantityLength.LengthUnit.FEET)
    	);	
    	QuantityLength expected = new QuantityLength(3.0, QuantityLength.LengthUnit.FEET);
        assertEquals(expected, result);
    }
    
    @Test
    public void testAddition_NullSecondOperand() {
    	assertThrows(
    			IllegalArgumentException.class, 
    			() -> {	QuantityMeasurementApp.demonstrateLengthAddition(new QuantityLength(1.0,QuantityLength.LengthUnit.FEET),null);}
    			);
    }
    
    @Test
    public void testAddition_LargeValues() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
    			new QuantityLength(1e6,QuantityLength.LengthUnit.FEET),	
    			new QuantityLength(1e6,QuantityLength.LengthUnit.FEET)
    	);	
    	QuantityLength expected = new QuantityLength(2e6, QuantityLength.LengthUnit.FEET);
        assertEquals(expected, result);
    }
    
    @Test
    public void testAddition_SmallValues() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
    			new QuantityLength(0.001,QuantityLength.LengthUnit.FEET),	
    			new QuantityLength(0.002,QuantityLength.LengthUnit.FEET)
    	);	
    	QuantityLength expected = new QuantityLength(0.003, QuantityLength.LengthUnit.FEET);
        assertEquals(expected, result);
    }
    
    @Test
    public void testAddition_ExplicitTargetUnit_Feet() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
	        new QuantityLength(1.0, QuantityLength.LengthUnit.FEET),
	        new QuantityLength(12.0, QuantityLength.LengthUnit.INCHES),
	        QuantityLength.LengthUnit.FEET
        );
        assertEquals(new QuantityLength(2.0, QuantityLength.LengthUnit.FEET), result);
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Inches() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
            new QuantityLength(1.0, QuantityLength.LengthUnit.FEET),
            new QuantityLength(12.0, QuantityLength.LengthUnit.INCHES),
            QuantityLength.LengthUnit.INCHES
        );
        assertEquals(new QuantityLength(24.0, QuantityLength.LengthUnit.INCHES), result);
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Yards() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
            new QuantityLength(1.0, QuantityLength.LengthUnit.FEET),
            new QuantityLength(12.0, QuantityLength.LengthUnit.INCHES),
            QuantityLength.LengthUnit.YARDS
        );
        assertTrue(result.equals(new QuantityLength(0.666667, QuantityLength.LengthUnit.YARDS)));
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Centimeters() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
            new QuantityLength(1.0, QuantityLength.LengthUnit.INCHES),
            new QuantityLength(1.0, QuantityLength.LengthUnit.INCHES),
            QuantityLength.LengthUnit.CENTIMETERS
        );
        assertTrue(result.equals(new QuantityLength(5.079998, QuantityLength.LengthUnit.CENTIMETERS)));
    }

    @Test
    public void testAddition_ExplicitTargetUnit_SameAsFirstOperand() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
            new QuantityLength(2.0, QuantityLength.LengthUnit.YARDS),
            new QuantityLength(3.0, QuantityLength.LengthUnit.FEET),
            QuantityLength.LengthUnit.YARDS
        );
        assertEquals(new QuantityLength(3.0, QuantityLength.LengthUnit.YARDS), result);
    }

    @Test
    public void testAddition_ExplicitTargetUnit_SameAsSecondOperand() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
            new QuantityLength(2.0, QuantityLength.LengthUnit.YARDS),
            new QuantityLength(3.0, QuantityLength.LengthUnit.FEET),
            QuantityLength.LengthUnit.FEET
        );
        assertEquals(new QuantityLength(9.0, QuantityLength.LengthUnit.FEET), result);
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Commutativity() {
    	QuantityLength a = new QuantityLength(1.0, QuantityLength.LengthUnit.FEET);
    	QuantityLength b = new QuantityLength(12.0, QuantityLength.LengthUnit.INCHES);
    	QuantityLength result1 = QuantityMeasurementApp.demonstrateLengthAddition(a, b, QuantityLength.LengthUnit.YARDS);
    	QuantityLength result2 = QuantityMeasurementApp.demonstrateLengthAddition(b, a, QuantityLength.LengthUnit.YARDS);
        assertTrue(result1.equals(result2));
    }

    @Test
    public void testAddition_ExplicitTargetUnit_WithZero() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
            new QuantityLength(5.0, QuantityLength.LengthUnit.FEET),
            new QuantityLength(0.0, QuantityLength.LengthUnit.INCHES),
            QuantityLength.LengthUnit.YARDS
        );
        assertTrue(result.equals(new QuantityLength(1.666667, QuantityLength.LengthUnit.YARDS)));
    }

    @Test
    public void testAddition_ExplicitTargetUnit_NegativeValues() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
            new QuantityLength(5.0, QuantityLength.LengthUnit.FEET),
            new QuantityLength(-2.0, QuantityLength.LengthUnit.FEET),
            QuantityLength.LengthUnit.INCHES
        );
        assertEquals(new QuantityLength(36.0, QuantityLength.LengthUnit.INCHES), result);
    }

    @Test
    public void testAddition_ExplicitTargetUnit_NullTargetUnit() {
        assertThrows(
    		IllegalArgumentException.class, 
    		() -> QuantityMeasurementApp.demonstrateLengthAddition(
                new QuantityLength(1.0, QuantityLength.LengthUnit.FEET),
                new QuantityLength(12.0, QuantityLength.LengthUnit.INCHES),
                null
            )
		);
    }

    @Test
    public void testAddition_ExplicitTargetUnit_LargeToSmallScale() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
            new QuantityLength(1000.0, QuantityLength.LengthUnit.FEET),
            new QuantityLength(500.0, QuantityLength.LengthUnit.FEET),
            QuantityLength.LengthUnit.INCHES
        );
        assertEquals(new QuantityLength(18000.0, QuantityLength.LengthUnit.INCHES), result);
    }

    @Test
    public void testAddition_ExplicitTargetUnit_SmallToLargeScale() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
            new QuantityLength(12.0, QuantityLength.LengthUnit.INCHES),
            new QuantityLength(12.0, QuantityLength.LengthUnit.INCHES),
            QuantityLength.LengthUnit.YARDS
        );
        assertTrue(result.equals(new QuantityLength(0.666667, QuantityLength.LengthUnit.YARDS)));
    }

    @Test
    public void testAddition_ExplicitTargetUnit_AllUnitCombinations() {
    	QuantityLength result1 = QuantityMeasurementApp.demonstrateLengthAddition(
            new QuantityLength(1.0, QuantityLength.LengthUnit.FEET),
            new QuantityLength(1.0, QuantityLength.LengthUnit.YARDS),
            QuantityLength.LengthUnit.INCHES
        );
        assertTrue(new QuantityLength(48.0, QuantityLength.LengthUnit.INCHES).equals(result1));

        QuantityLength result2 = QuantityMeasurementApp.demonstrateLengthAddition(
            new QuantityLength(2.54, QuantityLength.LengthUnit.CENTIMETERS),
            new QuantityLength(1.0, QuantityLength.LengthUnit.INCHES),
            QuantityLength.LengthUnit.FEET
        );
        assertTrue(result2.equals(new QuantityLength(0.166667, QuantityLength.LengthUnit.FEET)));
    }

    @Test
    public void testAddition_ExplicitTargetUnit_PrecisionTolerance() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
            new QuantityLength(0.1, QuantityLength.LengthUnit.FEET),
            new QuantityLength(0.2, QuantityLength.LengthUnit.FEET),
            QuantityLength.LengthUnit.INCHES
        );
        assertTrue(result.equals(new QuantityLength(3.6, QuantityLength.LengthUnit.INCHES)));
    }
}

