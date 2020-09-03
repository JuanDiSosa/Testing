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
 * 
 * Modifications 
 * 20171114 - Ian De Silva - Updated to comply with JUnit 4 and to adhere to 
 * 							 coding standards.  Added test documentation.
 */

/*
 * Pruebas unitarias sobre el proyecto CoffeMaker, adjunto readme.txt en el git para ver la tarea a realizar.
 */

package edu.ncsu.csc326.coffeemaker;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.After;

public class CoffeeMakerTest
{

	private CoffeeMaker cm;
	private Recipe r1;
	private Recipe r2;
	private Recipe r3;
	//Inicio setUp method
	@Before
	public void setUp() throws Exception
	{
		cm = new CoffeeMaker();

		//preparacion para la receta1 = r1
		r1 = new Recipe();
		r1.setName("Coffee1");
		r1.setAmtChocolate("1");
		r1.setAmtCoffee("2");
		r1.setAmtMilk("3");
		r1.setAmtSugar("4");
		r1.setPrice("5");

		//preparacion para la receta2 = r2
		r2 = new Recipe();
		r2.setName("Coffee2");
		r2.setAmtChocolate("6");
		r2.setAmtCoffee("7");
		r2.setAmtMilk("8");
		r2.setAmtSugar("9");
		r2.setPrice("10");

		cm.addRecipe(r1);
		cm.addRecipe(r2);

	} // final de setUp method


	// -------------------------------------------------------------------
	// Prueba de addInventory y checkInventory methods en CoffeeMaker class
	// -------------------------------------------------------------------
	@Test
	public void addInventoryTest()
	{
		String inv = cm.checkInventory();

		System.out.println("");
		System.out.println("Initial Inventory");
		System.out.println(inv);

		int initialCoffee = getIngredientCount(inv,"Coffee");
		int initialMilk = getIngredientCount(inv,"Milk");
		int initialSugar = getIngredientCount(inv,"Sugar");
		int initialChocolate = getIngredientCount(inv,"Chocolate");

		try
		{
			cm.addInventory("1","2","3","4");
		}
		catch (InventoryException e)
		{
			fail("InventoryException should not be thrown");
    		}

 		inv = cm.checkInventory();
		System.out.println("Final Inventory");
		System.out.println(inv);

		int finalCoffee = getIngredientCount(inv,"Coffee");
		int finalMilk = getIngredientCount(inv,"Milk");
		int finalSugar = getIngredientCount(inv,"Sugar");
		int finalChocolate = getIngredientCount(inv,"Chocolate");

		assertEquals((initialCoffee + 1),finalCoffee );
		assertEquals((initialMilk + 2), finalMilk );
		assertEquals((initialSugar + 3), finalSugar );
		assertEquals((initialChocolate + 4), finalChocolate );

	} 


	// Preparo getIngredientCount
	private int getIngredientCount(String inventory, String searchIngred)
	{
		int rv = -1;
		// Divido el string devuelto por inventario y lo ingreso a un array
		String[] allIngred = inventory.split("\n");
		// Recorro el array dejo el valor int de cada ingrediente
		for (int i = 0; i < allIngred.length; i ++)
		{
			if (allIngred[i].contains(searchIngred) && allIngred[i].contains(":"))
			{
				String[] singleIngred = allIngred[i].split(":");
				rv = Integer.parseInt(singleIngred[1].trim());
			}
		}

		return rv;

	}


	// -------------------------------------------------------------------
	// Prueba addRecipe method en CoffeeMaker class
	// -------------------------------------------------------------------
	@Test
	public void addRecipeTest() throws Exception
	{
		Recipe [] recipeArray = cm.getRecipes();

		assertEquals(recipeArray[0].getName(),"Coffee1" );
		assertEquals(recipeArray[0].getAmtChocolate(),1);
		assertEquals(recipeArray[0].getAmtCoffee(),2);
		assertEquals(recipeArray[0].getAmtMilk(),3);
		assertEquals(recipeArray[0].getAmtSugar(),4);
		assertEquals(recipeArray[0].getPrice(),5);

		assertEquals(recipeArray[1].getName(),"Coffee2" );
		assertEquals(recipeArray[1].getAmtChocolate(),6);
		assertEquals(recipeArray[1].getAmtCoffee(),7);
		assertEquals(recipeArray[1].getAmtMilk(),8);
		assertEquals(recipeArray[1].getAmtSugar(),9);
		assertEquals(recipeArray[1].getPrice(),10);

	} 


	// -------------------------------------------------------------------
	// Prueba deleteRecipe method y getRecipes methods en CoffeeMaker class
	// -------------------------------------------------------------------
	@Test
	public void deleteRecipeTest() throws Exception
	{
		// Ensure that the recipe does exist prior to deleting it
		assertEquals(cm.getRecipes()[0].getName(),"Coffee1");

		String name = cm.getRecipes()[0].getName();
		
		String result = cm.deleteRecipe(0);

		// Aseguro que el recipe no existe despues de borrarlo
		assertEquals(result, name);

	} 


	// -------------------------------------------------------------------
	// Prueba editRecipe method en CoffeeMaker class
	// -------------------------------------------------------------------
	@Test
	public void editRecipeTest() throws Exception
	{
		// Aseguro que el recipe existe antes de editarlo
		assertEquals(cm.getRecipes()[0].getName(),"Coffee1" );

		// Set up para r3
		r3 = new Recipe();
		r3.setName("Coffee3");
		r3.setAmtChocolate("4");
		r3.setAmtCoffee("3");
		r3.setAmtMilk("2");
		r3.setAmtSugar("1");
		r3.setPrice("10");
		
		String name = cm.getRecipes()[0].getName();
				
		String result = cm.editRecipe(0, r3);

		// Aseguro que el recipe nuevo existe
		assertEquals(name, result);

	}

	// -------------------------------------------------------------------
	// Prueba makeCoffee method en CoffeeMaker class
	// -------------------------------------------------------------------
	@Test
	public void makeCoffeeTest() throws Exception
	{
		int result;

		// Recipe no existe, devuelve el amtPaid
		result = cm.makeCoffee(2,20);
		assertEquals(result,20);

		// Suficiente dinero para recipe, hacer cafe y devuelve cambio. Precio es 10
		result = cm.makeCoffee(1,20);
		assertEquals(result,10);

		// No hay suficiente dinero para recipe, devuelve amtPaid. Precio es 10
		result = cm.makeCoffee(1,1);
		assertEquals(result,1);

		// Ingresamos suficiente dinero, pero no tenemos suficiente azucar para realizar la recipe
		result = cm.makeCoffee(1,20);
		assertEquals(result,20);

	}
	
	// -------------------------------------------------------------------
	// Preparo nuevo Set up para la prueba de Inventory class
	// -------------------------------------------------------------------

    private int stockReplenishmentUnitCount = 0;
    private Inventory I1;

    private int currentChocCount;
    private int currentSugarCount;
    private int currentMilkCount;
    private int currentCoffeeCount;

    @Before
    public void setUpInventory() {

        I1 = new Inventory();

        currentChocCount = I1.getChocolate();
        currentSugarCount = I1.getSugar();
        currentMilkCount = I1.getMilk();
        currentCoffeeCount = I1.getCoffee();
        // Inventario inicial
        assertEquals(15, currentChocCount);
        assertEquals(15, currentCoffeeCount);
        assertEquals(15, currentMilkCount);
        assertEquals(15, currentSugarCount);

    }
    // Realizo el teardown sobre Inventory class
    @After
    public void tearDown() throws Exception {
        I1 = null;
    }

	// -------------------------------------------------------------------
	// Prueba sobre el Method para Chocolate Inventory Correction
	// -------------------------------------------------------------------
    @Test
    public void setChocolateTest(){

        System.out.println("Test 1 Started");

        // Veo, inventario para chocolate = 0
        I1.setChocolate(stockReplenishmentUnitCount);
        currentChocCount = I1.getChocolate();
        assertEquals(0,currentChocCount);

        // Veo, inventario para chocolate > 0
        stockReplenishmentUnitCount = 1;
        I1.setChocolate(stockReplenishmentUnitCount);
        currentChocCount = I1.getChocolate();
        assertEquals(1,currentChocCount);
        
        // Veo, inventario para chocolate >= 0
        stockReplenishmentUnitCount = -15;
        I1.setChocolate(stockReplenishmentUnitCount);
        currentChocCount = I1.getChocolate();
        assertEquals(1,currentChocCount);

    }
    
	// -------------------------------------------------------------------
	// Prueba sobre el Method para acumulacion de chocolate en el inventario
	// -------------------------------------------------------------------
    @Test
    public synchronized void addChocolateTest() throws InventoryException{
    	
    	// Ingreso un string . Espero recibir un exception message
        try{
            I1.addChocolate("two");
            currentChocCount = I1.getChocolate();
        }
        catch(InventoryException e){

            assertNotSame("Units of chocolate must be a positive integer",e);
            assertTrue("Units of coffee must be a positive integer",currentChocCount>=0);
        }

        // Pruebo con un valor menor a 0. Espero recibir un exception message
        try{
        I1.addChocolate("-1");
        currentChocCount = I1.getChocolate();
        }
        catch(InventoryException e){
            //assertNotEquals("Units of chocolate must be a positive integer",e);
            assertTrue("Units of coffee must be a positive integer",currentChocCount>0);
        }

        // Pruebo con un valor mayor a 0.
        I1.addChocolate("2");
        currentChocCount = I1.getChocolate();
        assertEquals(17,currentChocCount);

    }

	// -------------------------------------------------------------------
	// Prueba sobre el Method para Cafe Inventory Correction
	// -------------------------------------------------------------------    

    @Test
    public void setCofeeTest(){

        System.out.println("Test 3 Started");

        //stockReplenishmentUnitCount =0
        I1.setCoffee(stockReplenishmentUnitCount);
        currentCoffeeCount = I1.getCoffee();
        assertEquals(0,currentCoffeeCount);

        //stockReplenishmentUnitCount >0
        stockReplenishmentUnitCount = 1;
        I1.setCoffee(stockReplenishmentUnitCount);
        currentCoffeeCount = I1.getCoffee();
        assertEquals(1,currentCoffeeCount);

        //stockReplenishmentUnitCount >=0
        stockReplenishmentUnitCount = -15;
        I1.setChocolate(stockReplenishmentUnitCount);
        currentCoffeeCount = I1.getCoffee();
        assertEquals(1,currentCoffeeCount);

    }

	// -------------------------------------------------------------------
	// Prueba sobre el Method para acumulacion de cofee en el inventario
	// -------------------------------------------------------------------  

    @Test
    public synchronized void addCoffeeTest() throws InventoryException{

        System.out.println("Test 4 Started");

    	// Ingreso un string . Espero recibir un exception message
        try{
            I1.addCoffee("two");
            currentCoffeeCount = I1.getCoffee();
        }
        catch(InventoryException e){

            assertNotSame("Units of chocolate must be a positive integer",e);
            assertTrue("Units of coffee must be a positive integer",currentCoffeeCount>=0);
        }


        // Pruebo con un valor menor a 0. Espero recibir un exception message
        try{
            I1.addCoffee("-1");
            currentCoffeeCount = I1.getCoffee();
        }
        catch(InventoryException e){
            //assertNotEquals("Units of chocolate must be a positive integer",e);
            assertTrue("Units of coffee must be a positive integer",currentCoffeeCount>0);
        }


        // Pruebo con un valor mayor a 0.
        I1.addCoffee("2");
        currentCoffeeCount = I1.getCoffee();
        assertEquals(17,currentCoffeeCount);


    }

	// -------------------------------------------------------------------
	// Prueba sobre el Method para Milk Inventory Correction
	// -------------------------------------------------------------------

    @Test
    public void setMilkTest(){

        //stockReplenishmentUnitCount =0
        I1.setMilk(stockReplenishmentUnitCount);
        currentMilkCount = I1.getMilk();
        assertEquals(0,currentMilkCount);

        //stockReplenishmentUnitCount >0
        stockReplenishmentUnitCount = 1;
        I1.setMilk(stockReplenishmentUnitCount);
        currentMilkCount = I1.getMilk();
        assertEquals(1,currentMilkCount);

        //stockReplenishmentUnitCount >=0
        stockReplenishmentUnitCount = -15;
        I1.setMilk(stockReplenishmentUnitCount);
        currentMilkCount = I1.getMilk();
        assertEquals(1,currentMilkCount);

    }

	// -------------------------------------------------------------------
	// Prueba sobre el Method para acumulacion de Milk en el inventario
	// -------------------------------------------------------------------  
    @Test
    public synchronized void addMilkTest() throws InventoryException{

        System.out.println("Test 6 Started");

    	// Ingreso un string . Espero recibir un exception message
        try{
            I1.addMilk("two");
            currentMilkCount = I1.getMilk();
        }
        catch(InventoryException e){

            assertNotSame("Units of chocolate must be a positive integer",e);
            assertTrue("Units of coffee must be a positive integer",currentMilkCount>=0);
        }


        // Pruebo con un valor menor a 0. Espero recibir un exception message
        try{
            I1.addMilk("-1");
            currentMilkCount = I1.getMilk();
        }
        catch(InventoryException e){
            //assertNotEquals("Units of chocolate must be a positive integer",e);
            assertTrue("Units of coffee must be a positive integer",currentMilkCount>0);
        }


        // Pruebo con un valor mayor a 0.
        I1.addMilk("2");
        currentMilkCount = I1.getMilk();
        assertEquals(17,currentMilkCount);

    }

	// -------------------------------------------------------------------
	// Prueba sobre el Method para Sugar Inventory Correction
	// -------------------------------------------------------------------
    @Test
    public void setSugarTest(){

        //Check for the equality operator inventory count =0
        I1.setSugar(stockReplenishmentUnitCount);
        currentSugarCount = I1.getSugar();
        assertEquals(0,currentSugarCount);

        //Check for the operator inventory count >0
        stockReplenishmentUnitCount = 1;
        I1.setSugar(stockReplenishmentUnitCount);
        currentSugarCount = I1.getSugar();
        assertEquals(1,currentSugarCount);

        //Check for the operator inventory count >=0
        stockReplenishmentUnitCount = -15;
        I1.setSugar(stockReplenishmentUnitCount);
        currentSugarCount = I1.getSugar();
        assertEquals(1,currentSugarCount);

    }

	// -------------------------------------------------------------------
	// Prueba sobre el Method para acumulacion de Sugar en el inventario
	// -------------------------------------------------------------------   
    @Test
    public synchronized void addSugarTest() throws InventoryException{

        System.out.println("Test 8 Started");

    	// Ingreso un string . Espero recibir un exception message
        try{
            I1.addSugar("two");
            currentSugarCount = I1.getSugar();
        }
        catch(InventoryException e){

            assertNotSame("Units of sugar must be a positive integer",e);
          //  assertTrue("Units of coffee must be a positive integer",currentSugarCount>=0);
        }


        // Pruebo con un valor menor a 0. Espero recibir un exception message
        try{
            I1.addSugar("-1");
            currentSugarCount = I1.getSugar();
        }
        catch(InventoryException e){
            //assertNotEquals("Units of chocolate must be a positive integer",e);
            assertTrue("Units of sugar must be a positive integer",currentSugarCount>0);
        }


        // Pruebo con un valor mayor a 0.
        I1.addSugar("2");
        currentSugarCount = I1.getSugar();
        assertEquals(17,currentSugarCount);

        System.out.println("Test 8 Completed");

    }

	// -------------------------------------------------------------------
	// Verifico el inventario contra los requerimientos de recetas
	// -------------------------------------------------------------------
    @Test
    public synchronized void enoughIngredientsTest(){
        Recipe R1 = new Recipe();
        Inventory I2 = new Inventory();
        int reqSugar = 0;
        int reqCoffee = 0;
        int reqChocolate = 0;
        int reqMilk = 0;
        int priceVal = 0;
        String tesName = "";

        try{

            R1.setName("IExperience1");
            R1.setPrice("5");
            R1.setAmtSugar("25");
            R1.setAmtCoffee("20");
            R1.setAmtMilk("30");
            R1.setAmtChocolate("15");

            priceVal = R1.getPrice();
            reqSugar = R1.getAmtSugar();
            reqMilk = R1.getAmtMilk();
            reqChocolate = R1.getAmtChocolate();
            reqCoffee = R1.getAmtCoffee();

            boolean checkValue = I2.enoughIngredients(R1);

            assertFalse(checkValue);  // Receta usa mas ingredientes de los q hay en inventario.
        }
        catch(RecipeException e){
            assertTrue("Check the Input Value for Price. Required to be positive numeric values",priceVal<=0);
            assertTrue("Check the Input Value for Chocolate. Required to be positive numeric values",reqChocolate<=0);
            assertTrue("Check the Input Value for Sugar. Required to be positive numeric values",reqSugar<=0);
            assertTrue("Check the Input Value for Coffee. Required to be positive numeric values",reqCoffee<=0);
            assertTrue("Check the Input Value for Milk. Required to be positive numeric values",reqMilk<=0);
        }


        int prevPriceVal = priceVal;

        try{
        	// prueba con diferentes strings como valor de entrada
            R1.setName("IExperience2");
            tesName = R1.getName();

            R1.setPrice("5.51");
            priceVal = R1.getPrice();

        }
        catch(RecipeException e){
            assertEquals("IExperience2",tesName);
            assertEquals(prevPriceVal,priceVal);
        }

        try{
        	// prueba con diferentes strings como valor de entrada
            R1.setName("IExperience3");
            tesName = R1.getName();

            R1.setPrice("3/4");
            priceVal = R1.getPrice();

            assertEquals(0,priceVal);
            assertEquals("IExperience3",tesName);

        }
        catch(RecipeException e){
            assertEquals("IExperience3",tesName);
            assertEquals(prevPriceVal,priceVal);
        }

        try{
            // Prueba sobre el limite de inventario contra receta
            R1.setName("IExperience4");
            R1.setPrice("10");
            R1.setAmtSugar("2");
            R1.setAmtCoffee("5");
            R1.setAmtMilk("3");
            R1.setAmtChocolate("1");

            tesName = R1.getName();
            priceVal = R1.getPrice();
            reqSugar = R1.getAmtSugar();
            reqMilk = R1.getAmtMilk();
            reqChocolate = R1.getAmtChocolate();
            reqCoffee = R1.getAmtCoffee();

            boolean check2 = I2.enoughIngredients(R1);
            assertTrue(check2);  // Receta se puede realizar con este inventory

        }
        catch(RecipeException e){
            assertEquals("IExperience4",tesName);
            assertTrue("Check the Input Value for Price. Required to be positive numeric values",priceVal<=0);
            assertTrue("Check the Input Value for Chocolate. Required to be positive numeric values",reqChocolate<=0);
            assertTrue("Check the Input Value for Sugar. Required to be positive numeric values",reqSugar<=0);
            assertTrue("Check the Input Value for Coffee. Required to be positive numeric values",reqCoffee<=0);
            assertTrue("Check the Input Value for Milk. Required to be positive numeric values",reqMilk<=0);
        }

        System.out.println("Test 9 Completed");

    }

	// -------------------------------------------------------------------
	// Verifico el inventario contra los requerimientos de recetas basadas en su uso
	// -------------------------------------------------------------------
    @Test
    public synchronized void useIngredientsTest(){

        System.out.println("Test 10 Started");

        Recipe R2 = new Recipe();

        R2.setName("UExperience01");
        String sN = R2.getName();
        int sP = 0;
        int sM = 0;
        int sCh = 0;
        int sCof = 0;
        int sSug = 0;

        try{
            R2.setPrice("9");
            sP = R2.getPrice();

            R2.setAmtMilk("5");
            sM = R2.getAmtMilk();

            R2.setAmtChocolate("10");
            sCh = R2.getAmtChocolate();

            R2.setAmtCoffee("5");
            sCof = R2.getAmtCoffee();

            R2.setAmtSugar("4");
            sSug = R2.getAmtSugar();

            boolean check01 = I1.enoughIngredients(R2);
            assertTrue(check01);

            boolean check02 = I1.useIngredients(R2);
            assertTrue(check02);

            // Espero una reduccion en el inventario, deacuerdo a lo usado en las recetas
            assertEquals(9,sP);
            assertEquals(15-5,I1.getMilk());
            assertEquals(15-10,I1.getChocolate());
            assertEquals(15-4,I1.getSugar());
            assertEquals(15-5,I1.getCoffee());
        }
        catch(RecipeException e){
            assertEquals("UExperience01",sN);
            assertTrue("Check the Input Value for Price. Required to be positive numeric values",sP<=0);
            assertTrue("Check the Input Value for Chocolate. Required to be positive numeric values",sCh<=0);
            assertTrue("Check the Input Value for Sugar. Required to be positive numeric values",sSug<=0);
            assertTrue("Check the Input Value for Coffee. Required to be positive numeric values",sCof<=0);
            assertTrue("Check the Input Value for Milk. Required to be positive numeric values",sM<=0);
        }

        // Defino de vuelta
        Recipe R3 = new Recipe();
        Inventory I10 = new Inventory();

        R3.setName("UExperience02");
        String sN1 = R2.getName();
        int sP1 = 0;
        int sM1 = 0;
        int sCh1 = 0;
        int sCof1 = 0;
        int sSug1 = 0;

        try{
            R3.setPrice("19");
            sP1 = R3.getPrice();

            R3.setAmtMilk("500");
            sM1 = R3.getAmtMilk();

            R3.setAmtChocolate("100");
            sCh1 = R3.getAmtChocolate();

            R3.setAmtCoffee("500");
            sCof1 = R3.getAmtCoffee();

            R3.setAmtSugar("400");
            sSug1 = R3.getAmtSugar();

            boolean check01 = I10.enoughIngredients(R3);
            assertFalse(check01);

            boolean check02 = I10.useIngredients(R3);
            assertFalse(check02);
            
            // Espero 0 reduccion de inventario. dado que la receta pide niveles exagerados de ingredientes mas alla del disponible
            assertEquals(9,sP);
            assertEquals(15,I10.getMilk());
            assertEquals(15,I10.getChocolate());
            assertEquals(15,I10.getSugar());
            assertEquals(15,I10.getCoffee());
        }
        catch(RecipeException e){
            assertEquals("UExperience02",sN1);
            assertTrue("Check the Input Value for Price. Required to be positive numeric values",sP1<=0);
            assertTrue("Check the Input Value for Chocolate. Required to be positive numeric values",sCh1<=0);
            assertTrue("Check the Input Value for Sugar. Required to be positive numeric values",sSug1<=0);
            assertTrue("Check the Input Value for Coffee. Required to be positive numeric values",sCof1<=0);
            assertTrue("Check the Input Value for Milk. Required to be positive numeric values",sM1<=0);
        }

    }

	// -------------------------------------------------------------------
	// Prueba sobre el comportamiento del overridden toString method para Inventory
	// -------------------------------------------------------------------

    @Test
    public void toStringTest(){
        Inventory I9 = new Inventory();
        String tS = I9.toString();
        assertNotNull(tS);
    }

    // Set up para la prueba de recipeBook
	private Recipe r4;
	private Recipe r5;
	private Recipe r1_2; // igual q r1 pero instanciada diferente
	RecipeBook recipeBook;

	@Before
	public void setUp3() throws Exception
	{
		recipeBook = new RecipeBook();

		r1 = new Recipe();
		r1.setName("Coffee");
		r1.setAmtChocolate("0");
		r1.setAmtCoffee("3");
		r1.setAmtMilk("1");
		r1.setAmtSugar("1");
		r1.setPrice("50");
		
		r2 = new Recipe();
		r2.setName("Mocha");
		r2.setAmtChocolate("20");
		r2.setAmtCoffee("3");
		r2.setAmtMilk("1");
		r2.setAmtSugar("1");
		r2.setPrice("75");
		
		r3 = new Recipe();
		r3.setName("Latte");
		r3.setAmtChocolate("0");
		r3.setAmtCoffee("3");
		r3.setAmtMilk("3");
		r3.setAmtSugar("1");
		r3.setPrice("100");
		
		r4 = new Recipe();
		r4.setName("Hot Chocolate");
		r4.setAmtChocolate("4");
		r4.setAmtCoffee("0");
		r4.setAmtMilk("1");
		r4.setAmtSugar("1");
		r4.setPrice("65");
		
		r5 = new Recipe();
		r5.setName("Double Chocolate Hot Chocolate");
		r5.setAmtChocolate("8");
		r5.setAmtCoffee("0");
		r5.setAmtMilk("1");
		r5.setAmtSugar("1");
		r5.setPrice("65");
		r1_2 = new Recipe();
		r1_2.setName("OtherCoffee");
		r1_2.setAmtChocolate("0");
		r1_2.setAmtCoffee("3");
		r1_2.setAmtMilk("1");
		r1_2.setAmtSugar("1");
		r1_2.setPrice("50");
				
	}

	@After
	public void tearDown1() throws Exception
	{
		recipeBook = null;
	}
	// -------------------------------------------------------------------
	// Prueba que el return es un array de recetas
	// -------------------------------------------------------------------
	@Test 
	public void testGetRecipeSucceed()
	{
		assertTrue(recipeBook.getRecipes() instanceof Recipe []);
	}
	// -------------------------------------------------------------------
	// Prueba si la receta se agrega de forma correcta
	// -------------------------------------------------------------------
	@Test
	public void testAddRecipeSucceeds()
	{
		//recipeBook = new RecipeBook();
		assertTrue(recipeBook.addRecipe(r2));
		assertEquals("Mocha", recipeBook.getRecipes()[0].getName());
		assertEquals(20,recipeBook.getRecipes()[0].getAmtChocolate());
		assertEquals(3,recipeBook.getRecipes()[0].getAmtCoffee());
		assertEquals(1,recipeBook.getRecipes()[0].getAmtMilk());
		assertEquals(1,recipeBook.getRecipes()[0].getAmtSugar());
		assertEquals(75,recipeBook.getRecipes()[0].getPrice());
	}
	// -------------------------------------------------------------------
	// Prueba sobre el Method para acumulacion de chocolate en el inventario
	// -------------------------------------------------------------------
	@Test
	public void testAddRecipeFailsSameInstance()
	{
		//recipeBook = new RecipeBook();
		recipeBook.addRecipe(r1);
		//try to add the recipe again
		assertFalse(recipeBook.addRecipe(r1));
	}
	// -------------------------------------------------------------------
	// Prueba sobre recetas iguales pero con diferente nombre e instancia
	// -------------------------------------------------------------------
	@Test
	public void testAddRecipeFailsDifferentInstance()
	{
		recipeBook.addRecipe(r1);
		//Same ingredients as r1 but different instance and name. Should not be allowed
		recipeBook.addRecipe(r1_2);
	}
	// -------------------------------------------------------------------
	// Prueba si la receta es añadida en el primer indice disponible
	// -------------------------------------------------------------------
	@Test
	public void testAddRecipeFailsAfterDelete()
	{
		recipeBook.addRecipe(r1);
		recipeBook.addRecipe(r2);
		recipeBook.addRecipe(r3);
		recipeBook.deleteRecipe(0);
		recipeBook.addRecipe(r4);
		assertTrue(r4.equals(recipeBook.getRecipes()[0]));
	}
	// -------------------------------------------------------------------
	// Prueba sobre si el recipe agregado tiene el mismo nombre q el recipe a agregar
	// -------------------------------------------------------------------
	@Test
	public void testAddRecipeFailsNameChange ()
	{
		recipeBook.addRecipe(r1);
		recipeBook.addRecipe(r2);
		String A = r3.getName();
		recipeBook.editRecipe(0, r3);
		String B = recipeBook.getRecipes()[0].getName();
		assertNotEquals(A, B);
	}
	// -------------------------------------------------------------------
	// Prueba la posicion luego de borrar una receta es null 
	// -------------------------------------------------------------------
	@Test
	public void testDeleteRecipeSetToNull()
	{
		recipeBook.addRecipe(r1);
		recipeBook.deleteRecipe(0);
		assertEquals(null, recipeBook.getRecipes()[0]);
	}
	// -------------------------------------------------------------------
	// Prueba borrar una receta
	// -------------------------------------------------------------------
	@Test
	public void testDeleteRecipeSuccess()
	{
		recipeBook.addRecipe(r1);
		String deleted = recipeBook.deleteRecipe(0);
		assertEquals("Coffee",deleted);
	}
	// -------------------------------------------------------------------
	// Prueba añadir una receta
	// -------------------------------------------------------------------
	@Test
	public void testEditRecipeSuccess()
	{
		recipeBook.addRecipe(r1);
		recipeBook.editRecipe(0, r1);
		assertEquals("Coffee", recipeBook.getRecipes()[0].getName());
		assertEquals(0,recipeBook.getRecipes()[0].getAmtChocolate());
		assertEquals(3,recipeBook.getRecipes()[0].getAmtCoffee());
		assertEquals(1,recipeBook.getRecipes()[0].getAmtMilk());
		assertEquals(1,recipeBook.getRecipes()[0].getAmtSugar());
		assertEquals(50,recipeBook.getRecipes()[0].getPrice());
	}
	// -------------------------------------------------------------------
	// Prueba borrar una receta q no existe en el libro
	// -------------------------------------------------------------------
	@Test
	public void testDeleteRecipeDNE()
	{
		recipeBook.addRecipe(r1);
		assertEquals(null, recipeBook.deleteRecipe(1));
	}
	// -------------------------------------------------------------------
	// Prueba editar una receta q no existe en el libro
	// -------------------------------------------------------------------

	@Test 
	public void testEditRecipeDNE ()
	{
		recipeBook.addRecipe(r1);
		assertEquals(null,recipeBook.editRecipe(1, r2) );
	}
	

    private Recipe R1;

    // Set uo para probar sobre Recipe class
    @Before
    public void setUp4(){
        R1 = new Recipe();
    }

    @After
    public void tearDown3(){
        R1 = null;
    }
	// -------------------------------------------------------------------
	// Prueba comportamiento de la cantidad de chocolate para una receta
	// -------------------------------------------------------------------   
    @Test
    public void setAmtChocolateTest(){
        System.out.println("Test 1 Started");
        try{  //Test for null input values
            R1.setName("R1");
            R1.setAmtChocolate("");

            assertNotNull(R1.getAmtChocolate());
            assertEquals(0,R1.getAmtChocolate());
        }
        catch(RecipeException e){
            assertTrue("Units of chocolate must be a positive integer",R1.getAmtChocolate()<=0);
        }

        try{
        	// Prueba por sting como entrada
            R1.setAmtChocolate("ten");

            assertNull(R1.getAmtChocolate());

        }
        catch(RecipeException e){
            assertTrue("Units of chocolate must be a positive integer",R1.getAmtChocolate()<=0);
        }

        try{
            //Prueba para un negativo como input
            R1.setAmtChocolate("-1");

            assertNotNull(R1.getAmtChocolate());
            assertEquals(0,R1.getAmtChocolate());
        }
        catch(RecipeException e){
            assertTrue("Units of chocolate must be a positive integer",R1.getAmtChocolate()<=0);
        }

        try{
            //Prueba para un positivo no entero como input
            R1.setAmtChocolate("3/4");

            assertNotNull(R1.getAmtChocolate());
            assertEquals(0,R1.getAmtChocolate());
        }
        catch(RecipeException e){
            assertTrue("Units of chocolate must be a positive integer",R1.getAmtChocolate()<=0);
        }

        try{
            //Prueba para un positivo entero como input
            R1.setAmtChocolate("1");

            assertEquals(1,R1.getAmtChocolate());

            assertTrue(R1.getAmtChocolate()>0);

        }
        catch(RecipeException e){
            assertTrue("Units of chocolate must be a positive integer",R1.getAmtChocolate()<=0);
        }
        System.out.println("Test 1 Completed");
    }
	// -------------------------------------------------------------------
	// Prueba comportamiento de la cantidad de cafe para una receta
	// -------------------------------------------------------------------   
    @Test
    public void setAmtCoffeeTest(){
        System.out.println("Test 2 Started");
        try{  //Test for null input values
            R1.setName("R1");
            R1.setAmtCoffee("");

            assertNotNull(R1.getAmtCoffee());
            assertEquals(0,R1.getAmtCoffee());
        }
        catch(RecipeException e){
            assertTrue("Units of chocolate must be a positive integer",R1.getAmtCoffee()<=0);
        }

        try{
            R1.setAmtCoffee("ten");

            assertNull(R1.getAmtCoffee());

        }
        catch(RecipeException e){
            assertTrue("Units of chocolate must be a positive integer",R1.getAmtCoffee()<=0);
        }

        try{
            R1.setAmtCoffee("-1");

            assertNotNull(R1.getAmtCoffee());
            assertEquals(0,R1.getAmtCoffee());
        }
        catch(RecipeException e){
            assertTrue("Units of chocolate must be a positive integer",R1.getAmtCoffee()<=0);
        }

        try{
            R1.setAmtCoffee("3/4");

            assertNotNull(R1.getAmtCoffee());
            assertEquals(0,R1.getAmtCoffee());
        }
        catch(RecipeException e){
            assertTrue("Units of chocolate must be a positive integer",R1.getAmtCoffee()<=0);
        }

        try{
            R1.setAmtCoffee("1");

            assertEquals(1,R1.getAmtCoffee());

            assertTrue(R1.getAmtCoffee()>0);

        }
        catch(RecipeException e){
            assertTrue("Units of chocolate must be a positive integer",R1.getAmtCoffee()<=0);
        }
        System.out.println("Test 2 Completed");
    }
    
	// -------------------------------------------------------------------
	// Prueba comportamiento de la cantidad de Sugar para una receta
	// -------------------------------------------------------------------   

    @Test
    public void setAmtSugarTest(){
        System.out.println("Test 3 Started");
        try{  //prueba para valores nulos de entrada o input
            R1.setName("R1");
            R1.setAmtSugar("");

            assertNotNull(R1.getAmtSugar());
            assertEquals(0,R1.getAmtSugar());
        }
        catch(RecipeException e){
            assertTrue("Units of chocolate must be a positive integer",R1.getAmtSugar()<=0);
        }

        try{
            R1.setAmtSugar("ten");

            assertNull(R1.getAmtSugar());

        }
        catch(RecipeException e){
            assertTrue("Units of chocolate must be a positive integer",R1.getAmtSugar()<=0);
        }

        try{
            R1.setAmtSugar("-1");

            assertNotNull(R1.getAmtSugar());
            assertEquals(0,R1.getAmtSugar());
        }
        catch(RecipeException e){
            assertTrue("Units of chocolate must be a positive integer",R1.getAmtSugar()<=0);
        }

        try{
            R1.setAmtSugar("3/4");

            assertNotNull(R1.getAmtSugar());
            assertEquals(0,R1.getAmtSugar());
        }
        catch(RecipeException e){
            assertTrue("Units of chocolate must be a positive integer",R1.getAmtSugar()<=0);
        }

        try{
            R1.setAmtSugar("1");

            assertEquals(1,R1.getAmtSugar());

            assertTrue(R1.getAmtSugar()>0);

        }
        catch(RecipeException e){
            assertTrue("Units of chocolate must be a positive integer",R1.getAmtSugar()<=0);
        }
        System.out.println("Test 3 Completed");
    }
	// -------------------------------------------------------------------
	// Prueba comportamiento de la cantidad de Milk para una receta
	// -------------------------------------------------------------------   
    @Test
    public void setAmtMilkTest(){
        System.out.println("Test 4 Started");
        try{  
            R1.setName("R1");
            R1.setAmtMilk("");

            assertNotNull(R1.getAmtMilk());
            assertEquals(0,R1.getAmtMilk());
        }
        catch(RecipeException e){
            assertTrue("Units of chocolate must be a positive integer",R1.getAmtMilk()<=0);
        }

        try{
            R1.setAmtMilk("ten");

            assertNull(R1.getAmtMilk());

        }
        catch(RecipeException e){
            assertTrue("Units of chocolate must be a positive integer",R1.getAmtMilk()<=0);
        }

        try{
            R1.setAmtMilk("-1");

            assertNotNull(R1.getAmtMilk());
            assertEquals(0,R1.getAmtMilk());
        }
        catch(RecipeException e){
            assertTrue("Units of chocolate must be a positive integer",R1.getAmtMilk()<=0);
        }

        try{
            R1.setAmtMilk("3/4");

            assertNotNull(R1.getAmtMilk());
            assertEquals(0,R1.getAmtMilk());
        }
        catch(RecipeException e){
            assertTrue("Units of chocolate must be a positive integer",R1.getAmtMilk()<=0);
        }

        try{
            R1.setAmtMilk("1");

            assertEquals(1,R1.getAmtMilk());

            assertTrue(R1.getAmtMilk()>0);

        }
        catch(RecipeException e){
            assertTrue("Units of chocolate must be a positive integer",R1.getAmtMilk()<=0);
        }
        System.out.println("Test 4 Completed");
    }
	// -------------------------------------------------------------------
	// Prueba comportamiento de Price para una receta
	// ------------------------------------------------------------------- 

    @Test
    public void setPriceTest() throws RecipeException{
        System.out.println("Test 5 Started");
        try{  
            R1.setName("R1");
            R1.setPrice("");

            assertNotNull(R1.getPrice());
            assertEquals(0,R1.getPrice());
        }
        catch(RecipeException e){
            assertTrue("Units of chocolate must be a positive integer",R1.getPrice()<=0);
        }

        try{
            R1.setPrice("ten");

            assertNull(R1.getPrice());

        }
        catch(RecipeException e){
            assertTrue("Units of chocolate must be a positive integer",R1.getPrice()<=0);
        }

        try{
            R1.setPrice("-1");

            assertNotNull(R1.getPrice());
            assertEquals(0,R1.getPrice());
        }
        catch(RecipeException e){
            assertTrue("Units of chocolate must be a positive integer",R1.getPrice()<=0);
        }

        try{
            R1.setPrice("3/4");

            assertNotNull(R1.getPrice());
            assertEquals(0,R1.getPrice());
        }
        catch(RecipeException e){
            assertTrue("Units of chocolate must be a positive integer",R1.getPrice()<=0);
        }

        try{
            R1.setPrice("1.5");

            assertEquals(1,R1.getPrice());

            assertTrue(R1.getPrice()>0);

        }
        catch(RecipeException e){
            assertTrue("Units of chocolate must be a positive integer",R1.getPrice()<=0);
        }
    }
	// -------------------------------------------------------------------
	// Prueba comportamiento para Name para una receta
	// ------------------------------------------------------------------- 
    @Test
    public void setNameTest(){
        R1.setName("");
        assertNotNull(R1.getName());
        assertEquals("",R1.getName());

        R1.setName(null);
        assertNotNull(R1.getName());
        assertEquals("",R1.getName());

        R1.setName("Regular@1*");
        assertNotNull(R1.getName());
        assertEquals("Regular@1*",R1.getName());
    }
	// -------------------------------------------------------------------
	// Prueba comportamiento de overridden toString method
	// ------------------------------------------------------------------- 
    @Test
    public void toStringTest1(){
        // Prueba para un string valido
        R1.setName("Regular000@1*");
        assertEquals("Regular000@1*",R1.toString());
        
        // Prueba para un string vacio
        R1.setName("");
        assertEquals("",R1.toString());

        // Prueba para uninput null
        R1.setName(null);
        assertEquals("",R1.getName());
    }
	// -------------------------------------------------------------------
	// Prueba comportamiento de overridden hashCode method
	// ------------------------------------------------------------------- 
    @Test
    public void hashCodeTest(){
        System.out.println("Test 9 Started");
        // Prueba para un string valido
        R1.setName("");
        long val1 = R1.hashCode();
        long val2 = 31 + R1.getName().hashCode();
        assertEquals(val2,val1);

        // Prueba para un string vacio
        R1.setName(null);
        long val3 = R1.hashCode();
        long val4 = 31;
        assertEquals(val4,val3);

        // Prueba para uninput null
        R1.setName("Rand0m@1");
        long val5 = R1.hashCode();
        long val6 = 31 + R1.getName().hashCode();
        assertEquals(val6,val5);
        System.out.println("Test 9 Completed");
    }
    
	// -------------------------------------------------------------------
	// Prueba comportamiento de overridden equals method.
	// ------------------------------------------------------------------- 
    @Test
    public void equalsTest(){
        System.out.println("Test 10 Started");
        // Compara diferente objectos del mismo tipo
        Recipe R1 = new Recipe();
        Recipe R2 = new Recipe();
        assertTrue(R1.equals(R2)); 

        R1.setName("B1");
        assertFalse(R1.equals(R2)); 

        try{
            R2.setName(null); 
            R2.setPrice("3");
            assertFalse(R1.equals(R2));
        }
        catch(RecipeException e){
            assertTrue("Input for price needs to be a positive integer",R2.getPrice()<0);
            assertNull(R2.getName());
        }

        System.out.println("Test 10 Completed");
    }


}