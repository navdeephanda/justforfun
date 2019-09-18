import java.io.*;
import java.util.*;

public class CarOrganizer
{ 
    IndexMinPQ<Car> pricePQ;
    IndexMinPQ<Car> mileagePQ;

    HashMap<String, IndexMinPQ<Car>> pricePQsforEachMakeAndModel;
    HashMap<String, IndexMinPQ<Car>> mileagePQsforEachMakeAndModel;

    HashMap<String, Integer> VINmapping;

    int N;

    ArrayDeque<Integer> availableIndices;

    public CarOrganizer()
    {
        pricePQ = new IndexMinPQ<>(10000);
        mileagePQ = new IndexMinPQ<>(10000);
        pricePQsforEachMakeAndModel = new HashMap<>();
        mileagePQsforEachMakeAndModel = new HashMap<>();
        VINmapping = new HashMap<>();
        availableIndices = new ArrayDeque<>();
        N = 0;
    }

    public void importDatabase(String filename)
    {
        File file = new File(filename); 
        BufferedReader fileReader;

        try
        {
            fileReader = new BufferedReader(new FileReader(file));
            String currLine; 
            while ((currLine = fileReader.readLine()) != null)
            {
                if(currLine.charAt(0) == '#')
                    continue;
                if(currLine.equals(""))
                    break;
                
                String [] currCarInfo = currLine.split(":");
                String VIN = currCarInfo[0];
                String Make = currCarInfo[1];
                String Model = currCarInfo[2];
                int Price = Integer.parseInt(currCarInfo[3]);
                int Mileage = Integer.parseInt(currCarInfo[4]);
                String Color = currCarInfo[5];

                addCar(VIN, Make, Model, Price, Mileage, Color);
            }
            fileReader.close();
        }
        catch(FileNotFoundException f)
        {
            System.out.println(filename + " is not present. Aborting program.");
            System.exit(1);
        }
        catch(IOException i)
        {
            System.out.println("I/O Error while reading in "+ filename +". Aborting program.");
            System.exit(1);
        }
    }

    public Car getMinCarByPrice()
    {
        return pricePQ.minKey();
    }

    public Car getMinCarByMileage()
    {
        return mileagePQ.minKey();
    }

    public Car getMinPriceCarByMakeAndModel(String make, String model)
    {
        String makeAndModel = make + ":" + model;
        IndexMinPQ<Car> p = pricePQsforEachMakeAndModel.get(makeAndModel);
        if(p == null)
            return null;
        if(p.isEmpty())
            return null;
        return p.minKey();
    }

    public Car getMinMileageCarByMakeAndModel(String make, String model)
    {
        String makeAndModel = make + ":" + model;
        IndexMinPQ<Car> m = mileagePQsforEachMakeAndModel.get(makeAndModel);
        if(m == null)
            return null;
        if(m.isEmpty())
            return null;
        return m.minKey();
    }

    // retval:
    //  valid car reference for successful add
    //  null if the VIN passed in is already in the organizer
    public Car addCar(String VIN, String Make, String Model, int Price, int Mileage, String Color)
    {
        // CREATE A CAR
        Car currCarMileage = new Car(VIN, Make, Model, Price, Mileage, Color, true);
        Car currCarPrice = new Car(VIN, Make, Model, Price, Mileage, Color, false);

        // CHOOSE A VALID ID FOR IT
        int insertID = 0;
        if(availableIndices.isEmpty())
            insertID = N;
        else
            insertID = availableIndices.pop();

        // MAP ITS VIN TO ITS IDS IN THE PQS
        Integer val = VINmapping.putIfAbsent(VIN, insertID);
        if(val != null)
            return null;

        // SAVE TO PQs
        mileagePQ.insert(insertID, currCarMileage);
        pricePQ.insert(insertID, currCarPrice);

        // CREATE/UPDATE HASHMAPS OF PQS
        String MakeModelString = Make + ":" + Model;
        
        IndexMinPQ<Car> m = mileagePQsforEachMakeAndModel.get(MakeModelString);
        if (m == null) 
        {
            m = new IndexMinPQ<>(10000);
        }
        m.insert(insertID, currCarMileage);
        mileagePQsforEachMakeAndModel.put(MakeModelString, m);

        IndexMinPQ<Car> p = pricePQsforEachMakeAndModel.get(MakeModelString);
        if (p == null) 
        {
            p = new IndexMinPQ<>(10000);
        }
        p.insert(insertID, currCarPrice);
        pricePQsforEachMakeAndModel.put(MakeModelString, p);

        N++;

        return currCarMileage;
    }

    public Car updateCarColor(String VIN, String newColor)
    {
        // MAP ITS VIN TO ITS IDS IN THE PQS
        Integer i = VINmapping.get(VIN);
        if(i == null)
            return null;

        Car retCarPrice = pricePQ.keyOf(i);
        Car retCarMileage = mileagePQ.keyOf(i);
        
        retCarPrice.setColor(newColor);
        retCarMileage.setColor(newColor);

        return retCarMileage;
    }

    public Car updateCarMileage(String VIN, int mileage)
    {
        Integer i = VINmapping.get(VIN);
        if(i == null)
            return null;

        Car oldCarPrice = pricePQ.keyOf(i);
        Car oldCarMileage = mileagePQ.keyOf(i);

        Car newCarPrice = new Car(oldCarPrice.getVIN(), oldCarPrice.getMake(), oldCarPrice.getModel(), oldCarPrice.getPrice(), mileage, oldCarPrice.getColor(), false);
        Car newCarMileage = new Car(oldCarMileage.getVIN(), oldCarMileage.getMake(), oldCarMileage.getModel(), oldCarMileage.getPrice(), mileage, oldCarMileage.getColor(), true);
    
        pricePQ.changeKey(i, newCarPrice);
        mileagePQ.changeKey(i, newCarMileage);

        String makeModelString = newCarPrice.getMake() + ":" + newCarPrice.getModel();

        IndexMinPQ<Car> p = pricePQsforEachMakeAndModel.get(makeModelString);
        if(p == null)
            return null;
        if(p.isEmpty())
            return null;
        IndexMinPQ<Car> m = mileagePQsforEachMakeAndModel.get(makeModelString);
        if(m == null)
            return null;
        if(m.isEmpty())
            return null;

        p.changeKey(i, newCarPrice);
        m.changeKey(i, newCarMileage);
        
        return newCarPrice;
    }

    public Car updateCarPrice(String VIN, int price)
    {
        Integer i = VINmapping.get(VIN);
        if(i == null)
            return null;

        Car oldCarPrice = pricePQ.keyOf(i);
        Car oldCarMileage = mileagePQ.keyOf(i);

        Car newCarPrice = new Car(oldCarPrice.getVIN(), oldCarPrice.getMake(), oldCarPrice.getModel(), price, oldCarPrice.getMileage(), oldCarPrice.getColor(), false);
        Car newCarMileage = new Car(oldCarMileage.getVIN(), oldCarMileage.getMake(), oldCarMileage.getModel(), price, oldCarMileage.getMileage() , oldCarMileage.getColor(), true);
    
        pricePQ.changeKey(i, newCarPrice);
        mileagePQ.changeKey(i, newCarMileage);

        String makeModelString = newCarPrice.getMake() + ":" + newCarPrice.getModel();

        IndexMinPQ<Car> p = pricePQsforEachMakeAndModel.get(makeModelString);
        if(p == null)
            return null;
        if(p.isEmpty())
            return null;
        IndexMinPQ<Car> m = mileagePQsforEachMakeAndModel.get(makeModelString);
        if(m == null)
            return null;
        if(m.isEmpty())
            return null;

        p.changeKey(i, newCarPrice);
        m.changeKey(i, newCarMileage);
        
        return newCarPrice;
    }

    public boolean deleteCar(String VIN)
    {
        Integer i = VINmapping.get(VIN);
        if(i == null)
            return false;

        Car carToDelete = pricePQ.keyOf(i);
        
        String makeModelString = carToDelete.getMake() + ":" + carToDelete.getModel();

        pricePQ.delete(i);
        mileagePQ.delete(i);

        IndexMinPQ<Car> p = pricePQsforEachMakeAndModel.get(makeModelString);
        if(p == null)
            return false;
        if(p.isEmpty())
            return false;

        IndexMinPQ<Car> m = mileagePQsforEachMakeAndModel.get(makeModelString);
        if(m == null)
            return false;
        if(m.isEmpty())
            return false;

        p.delete(i);
        m.delete(i);

        if(p.isEmpty())
        {
            pricePQsforEachMakeAndModel.remove(VIN);
        }
        if(m.isEmpty())
        {
            mileagePQsforEachMakeAndModel.remove(VIN);
        }

        VINmapping.remove(VIN);
        N--;

        if(i < N)
            availableIndices.push(i);
        return true;
    }
    // test CarOrganizer
    public static void main(String [] args)
    {
        // PROPER INITIALIZATION

        CarOrganizer cars = new CarOrganizer();
        cars.importDatabase("cars.txt");
        System.out.println("num of cars in pq: "+ cars.pricePQ.n);

        // MIN PRICE/MILEAGE APIS AFTER PROPER INITIALIZATION

        System.out.println("the car with the lowest mileage is: \n"+ cars.getMinCarByMileage());
        System.out.println("the car with the lowest price is: \n"+ cars.getMinCarByPrice());

        // MINIMUM PRICE/MILEAGE CAR BY MAKE AND MODEL FOR VALID USER INPUT

        System.out.println("The Ford Fiesta with the lowest price is: \n"+cars.getMinPriceCarByMakeAndModel("Ford", "Fiesta"));
        System.out.println("The Ford Fiesta with the lowest mileage is: \n"+cars.getMinMileageCarByMakeAndModel("Ford", "Fiesta"));

        // MINIMUM PRICE/MILEAGE CAR BY MAKE AND MODEL FOR INVALID USER INPUT (CAR DOES NOT EXIST OR TYPO IN INPUT)

        Car sadcar = cars.getMinPriceCarByMakeAndModel("Mazda", "5");
        System.out.println("This should be null. I'm too lazy to use JUnit to properly test this lol " + sadcar);
        Car sadcar2 = cars.getMinMileageCarByMakeAndModel("Mazda", "5");
        System.out.println("This should be null. I'm too lazy to use JUnit to properly test this lol " + sadcar2);

        int i = 0;
        Iterator j = cars.pricePQ.iterator();
        while(j.hasNext())
        {
            System.out.println("element "+i+" of the price minpq");
            System.out.println(cars.pricePQ.keyOf(i));
            i++;
            j.next();
        }


        // ADD CAR API

        System.out.println("There are "+ cars.N + " cars in the organizer");
        System.out.println("num of cars in pq: "+ cars.pricePQ.n);
        Car lexusLS = cars.addCar("4T1BD1FK8EU130549", "Lexus", "LS", 50, 50, "Red");
        System.out.println("Added Lexus LS:" + lexusLS);
        System.out.println("There are "+ cars.N + " cars in the organizer");
        System.out.println("num of cars in pq: "+ cars.pricePQ.n);
        System.out.println("the car with the lowest mileage is:"+ cars.getMinCarByMileage());
        System.out.println("the car with the lowest price is:"+ cars.getMinCarByPrice());
        System.out.println("The Lexus LS with the lowest price is:"+cars.getMinPriceCarByMakeAndModel("Lexus", "LS"));
        System.out.println("The Lexus LS with the lowest mileage is:"+cars.getMinMileageCarByMakeAndModel("Lexus", "LS"));

        // ADDING A DUPLICATE SHOULD FAIL

        System.out.println("There are "+ cars.N + " cars in the organizer");
        System.out.println("num of cars in pq: "+ cars.pricePQ.n);
        System.out.println("Attempt to add another car with the same VIN");
        Car lexusLS2 = cars.addCar("4T1BD1FK8EU130549", "Lexus", "LS", 50, 50, "Red");
        System.out.println("Car 2 is "+ lexusLS2);
        System.out.println("There are "+ cars.N + " cars in the organizer");
        System.out.println("num of cars in pq: "+ cars.pricePQ.n);

        // UPDATE CAR COLOR API FOR VALID VIN
        Car fuschiaLexusLS = cars.updateCarColor("4T1BD1FK8EU130549", "Fuschia");
        System.out.println("Lexus is now: "+fuschiaLexusLS);
        System.out.println("the car with the lowest mileage is:"+ cars.getMinCarByMileage());
        System.out.println("the car with the lowest price is:"+ cars.getMinCarByPrice());
        System.out.println("The Lexus LS with the lowest price is:"+cars.getMinPriceCarByMakeAndModel("Lexus", "LS"));
        System.out.println("The Lexus LS with the lowest mileage is:"+cars.getMinMileageCarByMakeAndModel("Lexus", "LS"));

        // UPDATE CAR COLOR API FOR INVALID VIN
        Car blerg = cars.updateCarColor("blerg", "Fuschia");
        System.out.println("blerg is : "+blerg);

        // UPDATE MILEAGE API
        Car lexusNot50Anymore = cars.updateCarMileage("4T1BD1FK8EU130549", 1001);
        System.out.println("Lexus is now: "+lexusNot50Anymore);
        System.out.println("the car with the lowest mileage is:"+ cars.getMinCarByMileage());
        System.out.println("the car with the lowest price is:"+ cars.getMinCarByPrice());

        Car FordFiesta = cars.updateCarMileage("5DZ623ZRW0C4N8OYZ", 100);
        System.out.println("Ford is now: "+FordFiesta);
        System.out.println("The Ford Fiesta with the lowest price is:"+cars.getMinPriceCarByMakeAndModel("Ford", "Fiesta"));
        System.out.println("The Ford Fiesta with the lowest mileage is:"+cars.getMinMileageCarByMakeAndModel("Ford", "Fiesta"));

        // UPDATE CAR PRICE API
        lexusNot50Anymore = cars.updateCarPrice("4T1BD1FK8EU130549", 1001);
        System.out.println("Lexus is now: "+lexusNot50Anymore);
        System.out.println("the car with the lowest mileage is:"+ cars.getMinCarByMileage());
        System.out.println("the car with the lowest price is:"+ cars.getMinCarByPrice());

        FordFiesta = cars.updateCarPrice("5DZ623ZRW0C4N8OYZ", 100);
        System.out.println("Ford is now: "+FordFiesta);
        System.out.println("The Ford Fiesta with the lowest price is:"+cars.getMinPriceCarByMakeAndModel("Ford", "Fiesta"));
        System.out.println("The Ford Fiesta with the lowest mileage is:"+cars.getMinMileageCarByMakeAndModel("Ford", "Fiesta"));

        // UPDATE A CAR THAT ISN'T THERE
        cars.updateCarPrice("blerg", 27);
        cars.updateCarMileage("blerg", 27);
        // (shouldn't crash)

        // DELETE A VIN THAT ISN'T THERE
        cars.deleteCar("blerg");
        cars.deleteCar("blerg");
        // (shouldn't crash)

        // DELETE FROM END
        System.out.println("There are "+ cars.N + " cars in the organizer");
        System.out.println("num of cars in pq: "+ cars.pricePQ.n);
        boolean didItDie = cars.deleteCar("4T1BD1FK8EU130549");
        if(didItDie)
            System.out.println("lexus go bye");

        // CHECK THAT AFTER YOU DELETE ALL THE CARS OF A CERTAIN MAKE AND MODEL, YOU DON'T CRASH AFTER TRYING TO GET MIN
        // FOR THAT MAKE AND MODEL
        System.out.println("Accessing Lexus should yield null: "+ cars.getMinMileageCarByMakeAndModel("Lexus", "LS"));
        System.out.println("Accessing Lexus should yield null: "+ cars.getMinPriceCarByMakeAndModel("Lexus", "LS"));

        // DELETE FROM MIDDLE
        System.out.println("There are "+ cars.N + " cars in the organizer");
        System.out.println("num of cars in pq: "+ cars.pricePQ.n);
        didItDie = cars.deleteCar("16Z2DPEHSUK5KCMEH");
        if(didItDie)
            System.out.println("bye elantra!!!!!");

        // CHECK THAT ADDING A CAR KEEPS THE SIZE OF THE PQ IN SYNC WITH THE ORGANIZER SIZE
        System.out.println("There are "+ cars.N + " cars in the organizer");
        System.out.println("num of cars in pq: "+ cars.pricePQ.n);
        cars.addCar("blerg", "blergy", "BLE", 6969, 6969, "blerg");
        System.out.println(cars.VINmapping.get("blerg"));
        System.out.println("There are "+ cars.N + " cars in the organizer");
        System.out.println("num of cars in pq: "+ cars.pricePQ.n);

    }
}