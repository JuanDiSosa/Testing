/*
 * Copyright (c) 2009,  Sarah Heckman, Laurie Williams, Dright Ho
 * All Rights Reserved.
 * 
 * Permission has been explicitly granted to the University of Minnesota 
 * Software Engineering Center to use and distribute this source for 
 * educational purposes, including delivering online education through
 * Coursera or other entities.  
 * 
 * No warranty is given regarding this software, including warranties as
 * to the correctness or completeness of this software, including 
 * fitness for purpose.
 * 
 * Modifications
 * 20171113 - Michael W. Whalen - Extended with additional recipe.
 * 20171114 - Ian J. De Silva   - Updated to JUnit 4; fixed variable names.
 */
package edu.ncsu.csc326.coffeemaker;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;



/**
 * Unit tests for CoffeeMaker class.
 * 
 * @author Sarah Heckman
 *
 * Extended by Mike Whalen
 */

public class CoffeeMakerTest {
	
	//-----------------------------------------------------------------------
	//	DATA MEMBERS
	//-----------------------------------------------------------------------
	private Recipe recipe1;
	private Recipe recipe2;
	private Recipe recipe3;
	private Recipe recipe4;
	private Recipe recipe5;
	
	private Recipe [] stubRecipies; 
	
	/**
	 * The coffee maker -- our object under test.
	 */
	private CoffeeMaker coffeeMaker;
	
	/**
	 * The stubbed recipe book.
	 */
	private RecipeBook recipeBookStub;
	
	
	//-----------------------------------------------------------------------
	//	Set-up / Tear-down
	//-----------------------------------------------------------------------
	/**
	 * Initializes some recipes to test with, creates the {@link CoffeeMaker} 
	 * object we wish to test, and stubs the {@link RecipeBook}. 
	 * 
	 * @throws RecipeException  if there was an error parsing the ingredient 
	 * 		amount when setting up the recipe.
	 */
	@Before
	public void setUp() throws RecipeException {
		
		recipeBookStub = mock(RecipeBook.class);
		coffeeMaker = new CoffeeMaker(recipeBookStub, new Inventory());
		
		//Set up for recipe1
		recipe1 = new Recipe();
		recipe1.setName("Coffee");
		recipe1.setAmtChocolate("0");
		recipe1.setAmtCoffee("3");
		recipe1.setAmtMilk("1");
		recipe1.setAmtSugar("1");
		recipe1.setPrice("50");
		
		//Set up for recipe2
		recipe2 = new Recipe();
		recipe2.setName("Mocha");
		recipe2.setAmtChocolate("20");
		recipe2.setAmtCoffee("3");
		recipe2.setAmtMilk("1");
		recipe2.setAmtSugar("1");
		recipe2.setPrice("75");
		
		//Set up for recipe3
		recipe3 = new Recipe();
		recipe3.setName("Latte");
		recipe3.setAmtChocolate("0");
		recipe3.setAmtCoffee("3");
		recipe3.setAmtMilk("3");
		recipe3.setAmtSugar("1");
		recipe3.setPrice("100");
		
		//Set up for recipe4
		recipe4 = new Recipe();
		recipe4.setName("Hot Chocolate");
		recipe4.setAmtChocolate("4");
		recipe4.setAmtCoffee("0");
		recipe4.setAmtMilk("1");
		recipe4.setAmtSugar("1");
		recipe4.setPrice("65");
		
		//Set up for recipe5 (added by MWW)
		recipe5 = new Recipe();
		recipe5.setName("Super Hot Chocolate");
		recipe5.setAmtChocolate("6");
		recipe5.setAmtCoffee("0");
		recipe5.setAmtMilk("1");
		recipe5.setAmtSugar("1");
		recipe5.setPrice("100");

		stubRecipies = new Recipe [] {recipe1, recipe2, recipe3};
	}
	
	
	//-----------------------------------------------------------------------
	//	Test Methods
	//-----------------------------------------------------------------------
	
	// put your tests here!
	
	@Test
	public void testMakeCoffee() {
		coffeeMaker = new CoffeeMaker(recipeBookStub, new Inventory());
		assertTrue(true);
	}
	
	@Test
	public void testMakeCoffee2() {
		when(recipeBookStub.getRecipes()).thenReturn(stubRecipies);
		coffeeMaker.addRecipe(stubRecipies[0]);
		assertEquals(25, coffeeMaker.makeCoffee(0, 75));
	}
	
	@Test
	public void testMakeCoffee8() {
		when(coffeeMaker.getRecipes()).thenReturn(stubRecipies);
		assertEquals(25, coffeeMaker.makeCoffee(0, 25));
	}
	
	@Test
	public void testMakeCoffee9() throws RecipeException {
		when(coffeeMaker.getRecipes()).thenReturn(stubRecipies);
		assertEquals(100, coffeeMaker.makeCoffee(50, 100));
	}
	
	@Test
	public void testMakeCoffee3() {
		when(recipeBookStub.addRecipe(stubRecipies[0])).thenReturn(true);
		when(recipeBookStub.addRecipe(stubRecipies[1])).thenReturn(true);
		when(recipeBookStub.addRecipe(stubRecipies[2])).thenReturn(true);
		when(recipeBookStub.addRecipe(stubRecipies[2])).thenReturn(false);
	}
	
	@Test
	public void testMakeCoffee4() {
		when(recipeBookStub.addRecipe(stubRecipies[0])).thenReturn(true);
		when(recipeBookStub.addRecipe(stubRecipies[1])).thenReturn(true);
		when(recipeBookStub.addRecipe(stubRecipies[1])).thenReturn(false);
		when(recipeBookStub.addRecipe(recipe4)).thenReturn(false);
		when(recipeBookStub.addRecipe(stubRecipies[2])).thenReturn(true);
	}
	
	@Test
	public void testMakeCoffee5() {
		Inventory inventServ = new Inventory();
		coffeeMaker = new CoffeeMaker(recipeBookStub, inventServ);

		when(coffeeMaker.getRecipes()).thenReturn(stubRecipies);

		assertEquals(15, coffeeMaker.makeCoffee(0, 65));

		//after purchase coffee the inventory should!! decrease.
		//Although the SUT has an error when trying to decrease (It's Increasing instead of) the amount of coffee

		assertEquals(12, inventServ.getCoffee());
		assertEquals(14, inventServ.getMilk());
		assertEquals(14, inventServ.getSugar());
		assertEquals(15, inventServ.getChocolate());
	}
	
	@Test
	public void testMakeCoffee6() throws InventoryException {
		Inventory inventServ = new Inventory();
		coffeeMaker = new CoffeeMaker(recipeBookStub, inventServ);
		
		when(coffeeMaker.getRecipes()).thenReturn(stubRecipies);

		coffeeMaker.addInventory("1", "2", "3", "4");
		assertEquals(16, inventServ.getCoffee());
		assertEquals(17, inventServ.getMilk());
		assertEquals(18, inventServ.getSugar());
		assertEquals(19, inventServ.getChocolate());
	}
	
	@Test
	public void testMakeCoffee7() {
		when(coffeeMaker.getRecipes()).thenReturn(stubRecipies);
		when(coffeeMaker.addRecipe(stubRecipies[0])).thenReturn(true);
		when(coffeeMaker.editRecipe(0,recipe5)).thenReturn("Coffee");
		when(coffeeMaker.deleteRecipe(0)).thenReturn(null);
	}
	
	/*----------------------------------------------------------*/
	
	@Test
	public void testDummy() throws RecipeException {
		when(recipeBookStub.getRecipes()).thenReturn(stubRecipies);
		coffeeMaker.addRecipe(stubRecipies[2]);
		assertEquals(stubRecipies[2].getName(), "Latte");
		assertEquals(stubRecipies[2].getAmtChocolate(), 0);
	}
	
	@Test
	public void testDummy2() throws Exception {
		when(recipeBookStub.getRecipes()).thenReturn(stubRecipies);
		coffeeMaker.addRecipe(stubRecipies[2]);
		assertEquals(stubRecipies[2].getName(), "Latte");
		assertEquals(stubRecipies[2].getAmtChocolate(), 0);
		assertEquals(stubRecipies[2].getAmtCoffee(), 3);
		assertEquals(stubRecipies[2].getAmtMilk(), 3);
		assertEquals(stubRecipies[2].getAmtSugar(), 1);
		assertEquals(stubRecipies[2].getPrice(), 100);
	}
	
	@Test
	public void addInventoryTest() throws InventoryException {

		String inv = coffeeMaker.checkInventory();
		
		System.out.println("");
		System.out.println("Initial Inventory");
		System.out.println(inv);
		
		int initialCoffee = getIngredientCount(inv,"Coffee");
		int initialMilk = getIngredientCount(inv,"Milk");
		int initialSugar = getIngredientCount(inv,"Sugar");
		int initialChocolate = getIngredientCount(inv,"Chocolate");
		
		try {
			
			coffeeMaker.addInventory("1", "2", "3", "4");
			
		} catch (InventoryException e) {
			
			fail("InventoryException should not be thrown");
			
		}
		
		inv = coffeeMaker.checkInventory();
		System.out.println("Final Inventory");
		System.out.println(inv);
		
		int finalCoffee = getIngredientCount(inv,"Coffee");
		int finalMilk = getIngredientCount(inv,"Milk");
		int finalSugar = getIngredientCount(inv,"Sugar");
		int finalChocolate = getIngredientCount(inv,"Chocolate");
		
		assertEquals((initialCoffee + 1),finalCoffee);
		assertEquals((initialMilk + 2),finalMilk);
		assertEquals((initialSugar + 3),finalSugar);
		assertEquals((initialChocolate + 4),finalChocolate);
		
	}
	
	private int getIngredientCount(String inventory, String searchIngred) {
		
		int rv = -1;
		
		String[] allIngred = inventory.split("\n");
		
		for (int i = 0; i < allIngred.length; i ++) {
			
			if (allIngred[i].contains(searchIngred) && allIngred[i].contains(":")) {
				
				String[] singleIngred = allIngred[i].split(":");
				rv = Integer.parseInt(singleIngred[1].trim());
				
			}
			
		}
		
		return rv;
		
	}
	
}
