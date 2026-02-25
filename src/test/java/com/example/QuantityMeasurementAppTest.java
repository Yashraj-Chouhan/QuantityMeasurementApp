package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


import java.util.*;

public class QuantityMeasurementAppTest {
	
	private static final double EPSILON = 1e-6;

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
		QuantityLength cm2a = new QuantityLength(2.0,  LengthUnit.CENTIMETERS);
		QuantityLength cm2b = new QuantityLength(2.0,  LengthUnit.CENTIMETERS);
		assertEquals(cm2a, cm2b);
	}

	@Test
	public void testEquality_CentimetersToCentimeters_DifferentValue() {
		QuantityLength cm2 = new QuantityLength(2.0,  LengthUnit.CENTIMETERS);
		QuantityLength cm3 = new QuantityLength(3.0,  LengthUnit.CENTIMETERS);
		assertNotEquals(cm2, cm3);
	}

	@Test
	public void testEquality_InchesToCentimeters_EquivalentValue() {
		QuantityLength inchValue = new QuantityLength(0.393701,  LengthUnit.INCHES);
		QuantityLength cm1 = new QuantityLength(1.0,  LengthUnit.CENTIMETERS);
		assertEquals(inchValue, cm1);
	}


	@Test
	public void testEquality_DifferentClass() {
		QuantityLength yard = new QuantityLength(2.0,  LengthUnit.YARDS);
		assertFalse(yard.equals("2.0"));
	}
	
	@Test
	public void testEquality_NaN() {
		assertThrows(IllegalArgumentException.class, () -> new QuantityLength(Double.NaN,  LengthUnit.FEET));
	}

	@Test
	public void testEquality_Infinity() {
		assertThrows(IllegalArgumentException.class,
				() -> new QuantityLength(Double.POSITIVE_INFINITY,  LengthUnit.INCHES));
	}
	
	@Test
	public void testEquality_DemonstrateLengthComparisonMethod() {
		boolean result = QuantityMeasurementApp.demonstrateLengthComparison(1.0,  LengthUnit.YARDS, 36.0,  LengthUnit.INCHES);
		assertEquals(result,true);
	}
	
	// UC5 Test Cases
	@Test
	public void testConversion_FeetToInches() {
		QuantityLength result = QuantityMeasurementApp.demonstrateLengthConversion(1.0,  LengthUnit.FEET,  LengthUnit.INCHES);
		QuantityLength expected = new QuantityLength(12.0,  LengthUnit.INCHES);
        assertEquals(expected, result);
	}
	
	@Test
    public void testConversion_InchesToFeet() {
		QuantityLength result = QuantityMeasurementApp.demonstrateLengthConversion(24.0,  LengthUnit.INCHES,  LengthUnit.FEET);
		QuantityLength expected = new QuantityLength(2.0,  LengthUnit.FEET);
        assertEquals(expected, result);
    }

    @Test
    public void testConversion_YardsToInches() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthConversion(1.0,  LengthUnit.YARDS,  LengthUnit.INCHES);
    	QuantityLength expected = new QuantityLength(36.0,  LengthUnit.INCHES);
        assertEquals(expected, result);
    }

    @Test
    public void testConversion_InchesToYards() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthConversion(72.0,  LengthUnit.INCHES,  LengthUnit.YARDS);
    	QuantityLength expected = new QuantityLength(2.0,  LengthUnit.YARDS);
        assertEquals(expected, result);
    }

    @Test
    public void testConversion_CentimetersToInches() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthConversion(2.54,  LengthUnit.CENTIMETERS,  LengthUnit.INCHES);
    	QuantityLength expected = new QuantityLength(1.0,  LengthUnit.INCHES);
        assertTrue(result.equals(expected));
    }

    @Test
    public void testConversion_FeetToYards() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthConversion(6.0,  LengthUnit.FEET,  LengthUnit.YARDS);
    	QuantityLength expected = new QuantityLength(2.0,  LengthUnit.YARDS);
        assertEquals(expected, result);
    }

    @Test
    public void testConversion_RoundTrip_PreservesValue() {
    	QuantityLength original = new QuantityLength(3.0,  LengthUnit.FEET);
        QuantityLength converted = original.convertTo( LengthUnit.INCHES).convertTo( LengthUnit.FEET);
        assertTrue(original.equals(converted));
    }

    @Test
    public void testConversion_ZeroValue() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthConversion(0.0,  LengthUnit.FEET, LengthUnit.INCHES);
    	QuantityLength expected = new QuantityLength(0.0,  LengthUnit.INCHES);
        assertEquals(expected, result);
    }

    @Test
    public void testConversion_NegativeValue() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthConversion(-1.0,  LengthUnit.FEET,  LengthUnit.INCHES);
        QuantityLength expected = new QuantityLength(-12.0,  LengthUnit.INCHES);
        assertEquals(expected, result);
    }

    @Test
    public void testConversion_InvalidUnit_Throws() {
        assertThrows(
    		IllegalArgumentException.class, 
    		() -> QuantityMeasurementApp.demonstrateLengthConversion(1.0, null,  LengthUnit.INCHES)
    	);
    }

    @Test
    public void testConversion_NaNOrInfinite_Throws() {
        assertThrows(
    		IllegalArgumentException.class, 
    		() -> new QuantityLength(Double.NaN,  LengthUnit.FEET)
    	);
        assertThrows(
    		IllegalArgumentException.class, 
    		() -> new QuantityLength(Double.POSITIVE_INFINITY,  LengthUnit.INCHES)
		);
    }
    
    @Test
    public void testConversion_SameUnit() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthConversion(5.0,  LengthUnit.FEET,  LengthUnit.FEET);
    	QuantityLength expected = new QuantityLength(5.0,  LengthUnit.FEET);
        assertEquals(expected, result);
    }
    
    @Test
    public void testAddition_SameUnit_FeetPlusFeet() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
    			new QuantityLength(1.0, LengthUnit.FEET),	
    			new QuantityLength(2.0, LengthUnit.FEET)
    	);	
    	QuantityLength expected = new QuantityLength(3.0,  LengthUnit.FEET);
        assertEquals(expected, result);
    }
    
    @Test
    public void testAddition_SameUnit_InchPlusInch() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
    			new QuantityLength(6.0, LengthUnit.INCHES),	
    			new QuantityLength(6.0, LengthUnit.INCHES)
    	);	
    	QuantityLength expected = new QuantityLength(12.0,  LengthUnit.INCHES);
        assertEquals(expected, result);
    }
    
    @Test
    public void testAddition_CrossUnit_FeetPlusInches() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
    			new QuantityLength(1.0, LengthUnit.FEET),	
    			new QuantityLength(12.0, LengthUnit.INCHES)
    	);	
    	QuantityLength expected = new QuantityLength(2.0,  LengthUnit.FEET);
        assertEquals(expected, result);
    }
    
    @Test
    public void testAddition_CrossUnit_InchPlusFeet() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
    			new QuantityLength(12.0, LengthUnit.INCHES),
    			new QuantityLength(1.0, LengthUnit.FEET)
    	);	
    	QuantityLength expected = new QuantityLength(24.0,  LengthUnit.INCHES);
        assertEquals(expected, result);
    }
    
    @Test
    public void testAddition_CrossUnit_YardPlusFeet() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
    			new QuantityLength(1.0, LengthUnit.YARDS),
    			new QuantityLength(3.0, LengthUnit.FEET)
    	);	
    	QuantityLength expected = new QuantityLength(2.0,  LengthUnit.YARDS);
        assertEquals(expected, result);
    }
    
    @Test
    public void testAddition_CrossUnit_CentimeterPlusInch() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
    			new QuantityLength(2.54, LengthUnit.CENTIMETERS),
    			new QuantityLength(1.0, LengthUnit.INCHES)
    	);	
    	QuantityLength expected = new QuantityLength(5.08,  LengthUnit.CENTIMETERS);
        assertEquals(expected, result);
    }
    
    @Test
    public void testAddition_Commutativity() {
    	QuantityLength result1 = QuantityMeasurementApp.demonstrateLengthAddition(
    			new QuantityLength(1.0, LengthUnit.FEET),	
    			new QuantityLength(12.0, LengthUnit.INCHES)
    	);	
    	QuantityLength result2 = QuantityMeasurementApp.demonstrateLengthAddition(
    			new QuantityLength(12.0, LengthUnit.INCHES),
    			new QuantityLength(1.0, LengthUnit.FEET)
    	);
        assertEquals(result1, result2);
    }
    
    @Test
    public void testAddition_WithZero() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
    			new QuantityLength(5.0, LengthUnit.FEET),	
    			new QuantityLength(0.0, LengthUnit.INCHES)
    	);	
    	QuantityLength expected = new QuantityLength(5.0,  LengthUnit.FEET);
        assertEquals(expected, result);
    }
    
    @Test
    public void testAddition_NegativeValues() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
    			new QuantityLength(5.0, LengthUnit.FEET),	
    			new QuantityLength(-2.0, LengthUnit.FEET)
    	);	
    	QuantityLength expected = new QuantityLength(3.0,  LengthUnit.FEET);
        assertEquals(expected, result);
    }
    
    @Test
    public void testAddition_NullSecondOperand() {
    	assertThrows(
    			IllegalArgumentException.class, 
    			() -> {	QuantityMeasurementApp.demonstrateLengthAddition(new QuantityLength(1.0, LengthUnit.FEET),null);}
    			);
    }
    
    @Test
    public void testAddition_LargeValues() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
    			new QuantityLength(1e6, LengthUnit.FEET),	
    			new QuantityLength(1e6, LengthUnit.FEET)
    	);	
    	QuantityLength expected = new QuantityLength(2e6,  LengthUnit.FEET);
        assertEquals(expected, result);
    }
    
    @Test
    public void testAddition_SmallValues() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
    			new QuantityLength(0.001, LengthUnit.FEET),	
    			new QuantityLength(0.002, LengthUnit.FEET)
    	);	
    	QuantityLength expected = new QuantityLength(0.003,  LengthUnit.FEET);
        assertEquals(expected, result);
    }
    
    @Test
    public void testAddition_ExplicitTargetUnit_Feet() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
	        new QuantityLength(1.0,  LengthUnit.FEET),
	        new QuantityLength(12.0,  LengthUnit.INCHES),
	         LengthUnit.FEET
        );
        assertEquals(new QuantityLength(2.0,  LengthUnit.FEET), result);
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Inches() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
            new QuantityLength(1.0,  LengthUnit.FEET),
            new QuantityLength(12.0,  LengthUnit.INCHES),
             LengthUnit.INCHES
        );
        assertEquals(new QuantityLength(24.0,  LengthUnit.INCHES), result);
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Yards() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
            new QuantityLength(1.0,  LengthUnit.FEET),
            new QuantityLength(12.0,  LengthUnit.INCHES),
             LengthUnit.YARDS
        );
        assertTrue(result.equals(new QuantityLength(0.666667,  LengthUnit.YARDS)));
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Centimeters() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
            new QuantityLength(1.0,  LengthUnit.INCHES),
            new QuantityLength(1.0,  LengthUnit.INCHES),
             LengthUnit.CENTIMETERS
        );
        assertTrue(result.equals(new QuantityLength(5.079998,  LengthUnit.CENTIMETERS)));
    }

    @Test
    public void testAddition_ExplicitTargetUnit_SameAsFirstOperand() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
            new QuantityLength(2.0,  LengthUnit.YARDS),
            new QuantityLength(3.0,  LengthUnit.FEET),
             LengthUnit.YARDS
        );
        assertEquals(new QuantityLength(3.0,  LengthUnit.YARDS), result);
    }

    @Test
    public void testAddition_ExplicitTargetUnit_SameAsSecondOperand() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
            new QuantityLength(2.0,  LengthUnit.YARDS),
            new QuantityLength(3.0,  LengthUnit.FEET),
             LengthUnit.FEET
        );
        assertEquals(new QuantityLength(9.0,  LengthUnit.FEET), result);
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Commutativity() {
    	QuantityLength a = new QuantityLength(1.0,  LengthUnit.FEET);
    	QuantityLength b = new QuantityLength(12.0,  LengthUnit.INCHES);
    	QuantityLength result1 = QuantityMeasurementApp.demonstrateLengthAddition(a, b,  LengthUnit.YARDS);
    	QuantityLength result2 = QuantityMeasurementApp.demonstrateLengthAddition(b, a,  LengthUnit.YARDS);
        assertTrue(result1.equals(result2));
    }

    @Test
    public void testAddition_ExplicitTargetUnit_WithZero() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
            new QuantityLength(5.0,  LengthUnit.FEET),
            new QuantityLength(0.0,  LengthUnit.INCHES),
             LengthUnit.YARDS
        );
        assertTrue(result.equals(new QuantityLength(1.666667,  LengthUnit.YARDS)));
    }

    @Test
    public void testAddition_ExplicitTargetUnit_NegativeValues() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
            new QuantityLength(5.0,  LengthUnit.FEET),
            new QuantityLength(-2.0,  LengthUnit.FEET),
             LengthUnit.INCHES
        );
        assertEquals(new QuantityLength(36.0,  LengthUnit.INCHES), result);
    }

    @Test
    public void testAddition_ExplicitTargetUnit_NullTargetUnit() {
        assertThrows(
    		IllegalArgumentException.class, 
    		() -> QuantityMeasurementApp.demonstrateLengthAddition(
                new QuantityLength(1.0,  LengthUnit.FEET),
                new QuantityLength(12.0,  LengthUnit.INCHES),
                null
            )
		);
    }

    @Test
    public void testAddition_ExplicitTargetUnit_LargeToSmallScale() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
            new QuantityLength(1000.0,  LengthUnit.FEET),
            new QuantityLength(500.0,  LengthUnit.FEET),
             LengthUnit.INCHES
        );
        assertEquals(new QuantityLength(18000.0,  LengthUnit.INCHES), result);
    }

    @Test
    public void testAddition_ExplicitTargetUnit_SmallToLargeScale() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
            new QuantityLength(12.0,  LengthUnit.INCHES),
            new QuantityLength(12.0,  LengthUnit.INCHES),
             LengthUnit.YARDS
        );
        assertTrue(result.equals(new QuantityLength(0.666667,  LengthUnit.YARDS)));
    }

    @Test
    public void testAddition_ExplicitTargetUnit_AllUnitCombinations() {
    	QuantityLength result1 = QuantityMeasurementApp.demonstrateLengthAddition(
            new QuantityLength(1.0,  LengthUnit.FEET),
            new QuantityLength(1.0,  LengthUnit.YARDS),
             LengthUnit.INCHES
        );
        assertTrue(new QuantityLength(48.0,  LengthUnit.INCHES).equals(result1));

        QuantityLength result2 = QuantityMeasurementApp.demonstrateLengthAddition(
            new QuantityLength(2.54,  LengthUnit.CENTIMETERS),
            new QuantityLength(1.0,  LengthUnit.INCHES),
             LengthUnit.FEET
        );
        assertTrue(result2.equals(new QuantityLength(0.166667,  LengthUnit.FEET)));
    }

    @Test
    public void testAddition_ExplicitTargetUnit_PrecisionTolerance() {
    	QuantityLength result = QuantityMeasurementApp.demonstrateLengthAddition(
            new QuantityLength(0.1,  LengthUnit.FEET),
            new QuantityLength(0.2,  LengthUnit.FEET),
             LengthUnit.INCHES
        );
        assertTrue(result.equals(new QuantityLength(3.6,  LengthUnit.INCHES)));
    }
    
 // ---------- LengthUnit ENUM TESTS ----------

 	@Test
 	public void testLengthUnitEnum_FeetConstant() {
 		assertEquals(12.0, LengthUnit.FEET.getConversionFactor(), 0.0001);
 	}

 	@Test
 	public void testLengthUnitEnum_InchesConstant() {
 		assertEquals(1.0, LengthUnit.INCHES.getConversionFactor(), 0.0001);
 	}

 	@Test
 	public void testLengthUnitEnum_YardsConstant() {
 		assertEquals(36.0, LengthUnit.YARDS.getConversionFactor(), 0.0001);
 	}

 	@Test
 	public void testLengthUnitEnum_CentimetersConstant() {
 		assertEquals(1.0/2.54, LengthUnit.CENTIMETERS.getConversionFactor(), 0.0001);
 	}

 	// ---------- TO BASE UNIT ----------

 	@Test
 	public void testConvertToBaseUnit_FeetToFeet() {
 		assertEquals(60.0, LengthUnit.FEET.convertToBaseUnit(5.0), 0.01);
 	}

 	@Test
 	public void testConvertToBaseUnit_InchesToFeet() {
 		assertEquals(12.0, LengthUnit.INCHES.convertToBaseUnit(12.0), 0.01);
 	}

 	@Test
 	public void testConvertToBaseUnit_YardsToFeet() {
 		assertEquals(36.0, LengthUnit.YARDS.convertToBaseUnit(1.0), 0.01);
 	}

 	@Test
 	public void testConvertToBaseUnit_CentimetersToFeet() {
 		assertEquals(12.0, LengthUnit.CENTIMETERS.convertToBaseUnit(30.48), 0.01);
 	}

 	// ---------- FROM BASE UNIT ----------

 	@Test
 	public void testConvertFromBaseUnit_FeetToFeet() {
 		assertEquals(2.0/12.0, LengthUnit.FEET.convertFromBaseUnit(2.0), 0.01);
 	}

 	@Test
 	public void testConvertFromBaseUnit_FeetToInches() {
 		assertEquals(1.0, LengthUnit.INCHES.convertFromBaseUnit(1.0), 0.01);
 	}

 	@Test
 	public void testConvertFromBaseUnit_FeetToYards() {
 		assertEquals(1.0, LengthUnit.YARDS.convertFromBaseUnit(36.0), 0.01);
 	}

 	@Test
 	public void testConvertFromBaseUnit_FeetToCentimeters() {
 		assertEquals(2.54, LengthUnit.CENTIMETERS.convertFromBaseUnit(1.0), 0.01);
 	}

 	// ---------- EQUALITY ----------

 	@Test
 	public void testQuantityLengthRefactored_Equality() {
 		assertTrue(new QuantityLength(1.0, LengthUnit.FEET).equals(new QuantityLength(12.0, LengthUnit.INCHES)));
 	}

 	@Test
 	public void testQuantityLengthRefactored_NotEqual() {
 		assertFalse(new QuantityLength(1.0, LengthUnit.FEET).equals(new QuantityLength(10.0, LengthUnit.INCHES)));
 	}

 	// ---------- CONVERSION ----------

 	@Test
 	public void testQuantityLengthRefactored_ConvertTo() {
 		QuantityLength result = new QuantityLength(1.0, LengthUnit.FEET).convertTo(LengthUnit.INCHES);
 		assertTrue(result.equals(new QuantityLength(12.0, LengthUnit.INCHES)));
 	}

 	// ---------- ADDITION ----------

 	@Test
 	public void testQuantityLengthRefactored_Add() {
 		QuantityLength result = new QuantityLength(1.0, LengthUnit.FEET).add(new QuantityLength(12.0, LengthUnit.INCHES));
 		assertTrue(result.equals(new QuantityLength(2.0, LengthUnit.FEET)));
 	}

 	@Test
 	public void testQuantityLengthRefactored_AddWithTargetUnit() {
 		QuantityLength result = new QuantityLength(1.0, LengthUnit.FEET).add(new QuantityLength(12.0, LengthUnit.INCHES), LengthUnit.YARDS);
 		assertTrue(result.equals(new QuantityLength(0.666667, LengthUnit.YARDS)));
 	}

 	// ---------- NULL & INVALID ----------

 	@Test
 	public void testQuantityLengthRefactored_NullUnit() {
 		assertThrows(IllegalArgumentException.class, () -> new QuantityLength(1.0, null));
 	}

 	@Test
 	public void testQuantityLengthRefactored_InvalidValue() {
 		assertThrows(IllegalArgumentException.class, () -> new QuantityLength(Double.NaN, LengthUnit.FEET));
 	}

 	// ---------- ROUND TRIP ----------

 	@Test
 	public void testRoundTripConversion_RefactoredDesign() {
 		QuantityLength l = new QuantityLength(5.0, LengthUnit.FEET);
 		QuantityLength result = l.convertTo(LengthUnit.INCHES).convertTo(LengthUnit.FEET);
 		assertTrue(l.equals(result));
 	}

 	// ---------- BACKWARD COMPATIBILITY ----------

 	@Test
 	public void testBackwardCompatibility_UC1Equality() {
 		assertTrue(new QuantityLength(3.0, LengthUnit.FEET).equals(new QuantityLength(36.0, LengthUnit.INCHES)));
 	}

 	@Test
 	public void testBackwardCompatibility_UC5Conversion() {
 		QuantityLength result = new QuantityLength(3.0, LengthUnit.FEET).convertTo(LengthUnit.INCHES);
 		assertTrue(result.equals(new QuantityLength(36.0, LengthUnit.INCHES)));
 	}

 	@Test
 	public void testBackwardCompatibility_UC6Addition() {
 		QuantityLength result = new QuantityLength(1.0, LengthUnit.FEET).add(new QuantityLength(12, LengthUnit.INCHES));
 		assertTrue(result.equals(new QuantityLength(2.0, LengthUnit.FEET)));
 	}

 	@Test
 	public void testBackwardCompatibility_UC7AdditionWithTarget() {
 		QuantityLength result = new QuantityLength(1.0, LengthUnit.FEET).add(new QuantityLength(12, LengthUnit.INCHES), LengthUnit.INCHES);
 		assertTrue(result.equals(new QuantityLength(24.0, LengthUnit.INCHES)));
 	}

 	// ---------- IMMUTABILITY ----------

 	@Test
 	public void testUnitImmutability() {
 		assertNotNull(LengthUnit.valueOf("FEET"));
 	}

 	@Test
 	public void testEqualsReturnsFalseForNull() {
 		QuantityLength length = new QuantityLength(1.0, LengthUnit.FEET);
 		assertFalse(length.equals(null));
 	}

 	@Test
 	public void testAddNullThrowsException() {
 		QuantityLength length = new QuantityLength(1.0, LengthUnit.FEET);
 		assertThrows(IllegalArgumentException.class, () -> length.add(null));
 	}

 	@Test
 	public void testAddNullWithTargetUnitThrowsException() {
 		QuantityLength length = new QuantityLength(1.0, LengthUnit.FEET);
 		assertThrows(IllegalArgumentException.class, () -> length.add(null, LengthUnit.FEET));
 	}

 	@Test
 	public void testAddWithNullTargetUnitThrowsException() {
 		QuantityLength l1 = new QuantityLength(1, LengthUnit.FEET);
 		QuantityLength l2 = new QuantityLength(1, LengthUnit.FEET);

 		assertThrows(IllegalArgumentException.class, () -> l1.add(l2, null));
 	}

 	@Test
 	public void testMathCorrectnessAcrossUnits() {
 		QuantityLength result1 = new QuantityLength(1.0, LengthUnit.FEET).add(new QuantityLength(12, LengthUnit.INCHES), LengthUnit.FEET);

 		QuantityLength result2 = new QuantityLength(12.0, LengthUnit.INCHES).add(new QuantityLength(1, LengthUnit.FEET), LengthUnit.INCHES);

 		assertTrue(result1.equals(new QuantityLength(2.0, LengthUnit.FEET)));
 		assertTrue(result2.equals(new QuantityLength(24.0, LengthUnit.INCHES)));
 	}

 	@Test
 	public void testEnumAccessibility() {
 		assertNotNull(LengthUnit.FEET);
 		assertNotNull(LengthUnit.INCHES);
 		assertNotNull(LengthUnit.YARDS);
 		assertNotNull(LengthUnit.CENTIMETERS);
 	}
 	
 	 @Test
     void testEquality_KilogramToKilogram_SameValue() {
         assertEquals(
             new QuantityWeight(1.0, WeightUnit.KILOGRAM),
             new QuantityWeight(1.0, WeightUnit.KILOGRAM)
         );
     }

     @Test
     void testEquality_KilogramToKilogram_DifferentValue() {
         assertNotEquals(
             new QuantityWeight(1.0, WeightUnit.KILOGRAM),
             new QuantityWeight(2.0, WeightUnit.KILOGRAM)
         );
     }

     @Test
     void testEquality_GramToGram_SameValue() {
         assertEquals(
             new QuantityWeight(500.0, WeightUnit.GRAM),
             new QuantityWeight(500.0, WeightUnit.GRAM)
         );
     }

     @Test
     void testEquality_PoundToPound_SameValue() {
         assertEquals(
             new QuantityWeight(2.0, WeightUnit.POUND),
             new QuantityWeight(2.0, WeightUnit.POUND)
         );
     }

     @Test
     void testEquality_KilogramToGram_EquivalentValue() {
         assertEquals(
             new QuantityWeight(1.0, WeightUnit.KILOGRAM),
             new QuantityWeight(1000.0, WeightUnit.GRAM)
         );
     }

     @Test
     void testEquality_GramToKilogram_EquivalentValue() {
         assertEquals(
             new QuantityWeight(1000.0, WeightUnit.GRAM),
             new QuantityWeight(1.0, WeightUnit.KILOGRAM)
         );
     }

     @Test
     void testEquality_KilogramToPound_EquivalentValue() {
         assertTrue(
             new QuantityWeight(1.0, WeightUnit.KILOGRAM)
             .equals(new QuantityWeight(2.204624, WeightUnit.POUND))
         );
     }

     @Test
     void testEquality_GramToPound_EquivalentValue() {
         assertTrue(
             new QuantityWeight(453.592370, WeightUnit.GRAM)
             .equals(new QuantityWeight(1.0, WeightUnit.POUND))
         );
     }

     @Test
     void testEquality_Symmetry() {
    	 QuantityWeight a = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
    	 QuantityWeight b = new QuantityWeight(1000.0, WeightUnit.GRAM);
         assertTrue(a.equals(b));
         assertTrue(b.equals(a));
     }

     @Test
     void testEquality_Transitive() {
    	 QuantityWeight a = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
    	 QuantityWeight b = new QuantityWeight(1000.0, WeightUnit.GRAM);
    	 QuantityWeight c = new QuantityWeight(2.204624, WeightUnit.POUND);
         assertTrue(a.equals(b));
         assertTrue(b.equals(c));
         assertTrue(a.equals(c));
     }

     @Test
     void testEquality_WeightVsLength_Incompatible() {
    	 QuantityWeight w = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
    	 QuantityLength l = new QuantityLength(1.0, LengthUnit.FEET);
         assertFalse(w.equals(l));
     }

     @Test
     void testEquality_NullComparison() {
         assertFalse(new QuantityWeight(1.0, WeightUnit.KILOGRAM).equals(null));
     }

     @Test
     void testEquality_SameReference() {
    	 QuantityWeight w = new QuantityWeight(2.0, WeightUnit.KILOGRAM);
         assertEquals(w, w);
     }

     @Test
     void testEquality_NullUnit_Weight() {
         assertThrows(
             IllegalArgumentException.class, 
             () -> new QuantityWeight(1.0, null)
         );
     }

     @Test
     void testEquality_ZeroValue() {
         assertEquals(
             new QuantityWeight(0.0, WeightUnit.KILOGRAM),
             new QuantityWeight(0.0, WeightUnit.GRAM)
         );
     }

     @Test
     void testEquality_NegativeWeight() {
         assertEquals(
             new QuantityWeight(-1.0, WeightUnit.KILOGRAM),
             new QuantityWeight(-1000.0, WeightUnit.GRAM)
         );
     }

     @Test
     void testEquality_LargeWeightValue() {
         assertEquals(
             new QuantityWeight(1_000_000.0, WeightUnit.GRAM),
             new QuantityWeight(1000.0, WeightUnit.KILOGRAM)
         );
     }

     @Test
     void testEquality_SmallWeightValue() {
         assertEquals(
             new QuantityWeight(0.001, WeightUnit.KILOGRAM),
             new QuantityWeight(1.0, WeightUnit.GRAM)
         );
     }

     @Test
     void testConversion_PoundToKilogram() {
    	 QuantityWeight converted = new QuantityWeight(2.204624, WeightUnit.POUND).convertTo(WeightUnit.KILOGRAM);
         assertEquals(1.0, converted.getValue(), EPSILON);
         assertEquals(WeightUnit.KILOGRAM, converted.getUnit());
     }

     @Test
     void testConversion_KilogramToPound() {
    	 QuantityWeight converted = new QuantityWeight(1.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.POUND);
         assertEquals(2.204624, converted.getValue(), EPSILON);
         assertEquals(WeightUnit.POUND, converted.getUnit());
     }

     @Test
     void testConversion_SameUnit_Weight() {
    	 QuantityWeight converted = new QuantityWeight(5.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.KILOGRAM);
         assertEquals(new QuantityWeight(5.0, WeightUnit.KILOGRAM), converted);
     }

     @Test
     void testConversion_ZeroValue_Weight() {
    	 QuantityWeight converted = new QuantityWeight(0.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM);
         assertEquals(new QuantityWeight(0.0, WeightUnit.GRAM), converted);
     }

     @Test
     void testConversion_NegativeValue_Weight() {
    	 QuantityWeight converted = new QuantityWeight(-1.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM);
         assertEquals(new QuantityWeight(-1000.0, WeightUnit.GRAM), converted);
     }

     @Test
     void testConversion_RoundTrip() {
    	 QuantityWeight original = new QuantityWeight(1.5, WeightUnit.KILOGRAM);
    	 QuantityWeight roundTrip = original
             .convertTo(WeightUnit.GRAM)
             .convertTo(WeightUnit.KILOGRAM);
         assertEquals(original, roundTrip);
     }

     @Test
     void testAddition_SameUnit_KilogramPlusKilogram() {
    	 QuantityWeight sum = new QuantityWeight(1.0, WeightUnit.KILOGRAM)
             .add(new QuantityWeight(2.0, WeightUnit.KILOGRAM));
         assertEquals(new QuantityWeight(3.0, WeightUnit.KILOGRAM), sum);
     }

     @Test
     void testAddition_CrossUnit_KilogramPlusGram() {
    	 QuantityWeight sum = new QuantityWeight(1.0, WeightUnit.KILOGRAM)
             .add(new QuantityWeight(1000.0, WeightUnit.GRAM));
         assertEquals(new QuantityWeight(2.0, WeightUnit.KILOGRAM), sum);
     }

     @Test
     void testAddition_CrossUnit_PoundPlusKilogram() {
    	 QuantityWeight sum = new QuantityWeight(2.204624, WeightUnit.POUND)
             .add(new QuantityWeight(1.0, WeightUnit.KILOGRAM));
         assertEquals(4.409248, sum.getValue(), 1e6);
         assertEquals(WeightUnit.POUND, sum.getUnit());
     }

     @Test
     void testAddition_WithZero_Weight() {
    	 QuantityWeight sum = new QuantityWeight(5.0, WeightUnit.KILOGRAM)
             .add(new QuantityWeight(0.0, WeightUnit.GRAM));
         assertEquals(new QuantityWeight(5.0, WeightUnit.KILOGRAM), sum);
     }

     @Test
     void testAddition_NegativeValues_Weight() {
    	 QuantityWeight sum = new QuantityWeight(5.0, WeightUnit.KILOGRAM)
             .add(new QuantityWeight(-2000.0, WeightUnit.GRAM));
         assertEquals(new QuantityWeight(3.0, WeightUnit.KILOGRAM), sum);
     }

     @Test
     void testAddition_LargeValues_Weight() {
    	 QuantityWeight sum = new QuantityWeight(1e6, WeightUnit.KILOGRAM)
             .add(new QuantityWeight(1e6, WeightUnit.KILOGRAM));
         assertEquals(new QuantityWeight(2e6, WeightUnit.KILOGRAM), sum);
     }

     @Test
     void testAddition_ExplicitTargetUnit_Kilogram() {
    	 QuantityWeight sum = new QuantityWeight(1.0, WeightUnit.KILOGRAM)
             .add(new QuantityWeight(1000.0, WeightUnit.GRAM), WeightUnit.GRAM);
         assertEquals(new QuantityWeight(2000.0, WeightUnit.GRAM), sum);
     }

     @Test
     void testAddition_ExplicitTargetUnit_Pound() {
    	 QuantityWeight sum = new QuantityWeight(1.0, WeightUnit.POUND)
             .add(new QuantityWeight(453.592, WeightUnit.GRAM), WeightUnit.POUND);
         assertEquals(2.0, sum.getValue(), EPSILON);
         assertEquals(WeightUnit.POUND, sum.getUnit());
     }

     @Test
     void testAddition_ExplicitTargetUnit_Kilogram_FromKgAndPound() {
    	 QuantityWeight sum = new QuantityWeight(2.0, WeightUnit.KILOGRAM)
             .add(new QuantityWeight(4.0, WeightUnit.POUND), WeightUnit.KILOGRAM);
         assertEquals(3.814368, sum.getValue(), 1e-5);
         assertEquals(WeightUnit.KILOGRAM, sum.getUnit());
     }

     @Test
     void testAddition_Commutativity_Weight() {
    	 QuantityWeight a = new QuantityWeight(1.0, WeightUnit.KILOGRAM)
             .add(new QuantityWeight(1000.0, WeightUnit.GRAM));
    	 QuantityWeight b = new QuantityWeight(1000.0, WeightUnit.GRAM)
             .add(new QuantityWeight(1.0, WeightUnit.KILOGRAM));
         assertEquals(a.convertTo(WeightUnit.KILOGRAM), b.convertTo(WeightUnit.KILOGRAM));
     }

     @Test
     void testHashCode_ConsistencyWithEquals_InSet() {
         Set<QuantityWeight> set = new HashSet<>();
         set.add(new QuantityWeight(1.0, WeightUnit.KILOGRAM));
         set.add(new QuantityWeight(1000.0, WeightUnit.GRAM));
         assertEquals(1, set.size());
     }

     @Test
     void testHashCode_MapKeyBehavior() {
         Map<QuantityWeight, String> map = new HashMap<>();
         map.put(new QuantityWeight(1.0, WeightUnit.KILOGRAM), "oneKg");
         map.put(new QuantityWeight(1000.0, WeightUnit.GRAM), "oneKgAgain");
         assertEquals(1, map.size());
         assertTrue(map.containsKey(new QuantityWeight(1.0, WeightUnit.KILOGRAM)));
     }

     @Test
     void testInvalidValue_NaN() {
         assertThrows(
             IllegalArgumentException.class, 
             () -> new QuantityWeight(Double.NaN, WeightUnit.KILOGRAM)
         );
     }

     @Test
     void testInvalidValue_Infinite() {
         assertThrows(
             IllegalArgumentException.class, 
             () -> new QuantityWeight(Double.POSITIVE_INFINITY, WeightUnit.KILOGRAM)
         );
     }

     @Test
     void testFromBaseUnit_InequalityDetection() {
         assertNotEquals(
             new QuantityWeight(1.0, WeightUnit.KILOGRAM),
             new QuantityWeight(2.0, WeightUnit.KILOGRAM)
         );
     }
}  
    
   