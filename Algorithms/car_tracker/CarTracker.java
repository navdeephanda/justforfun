import java.util.Scanner;

public class CarTracker
{
    public static void main(String [] args)
    {
        CarOrganizer cars = new CarOrganizer();
        cars.importDatabase("cars.txt");        
        
        Scanner sc = new Scanner(System.in);
        promptUser();
        int userChoice = sc.nextInt();
        sc.nextLine();

        while(userChoice != 0)
        {
            switch(userChoice)
            {
                case 0:
                    System.out.println("Bye!");
                    System.exit(0);
                case 1:
                    addCar(cars, sc);
                    break;
                case 2:
                    updateCar(cars,sc);
                    break;
                case 3:
                    deleteCar(cars,sc);
                    break;
                case 4:
                    lowestPricedCar(cars);
                    break;
                case 5:
                    lowestMileageCar(cars);
                    break;
                case 6:
                    lowestPricedCarbyMakeandModel(cars, sc);
                    break;
                case 7:
                    lowestMileageCarbyMakeandModel(cars, sc);
                    break;
                default:
                    System.out.println("Invalid input! Try again.");
                    break;
            }
            promptUser();
            userChoice = sc.nextInt();
            sc.nextLine();
        }
        
        sc.close();
    }

    private static void promptUser()
    {
        System.out.println
        ("Type the corresponding number to select an action.\n"+
         "1. Add a car!\n"+
         "2. Update a car's price, mileage, or color!\n"+
         "3. Remove a car from consideration!!\n"+
         "4. Find the lowest-priced car!\n"+
         "5. Find the lowest mileage car!\n"+
         "6. Find the lowest-priced car for a make and model!\n"+
         "7. Find the lowest mileage car for a make and model!\n"+
         "Type 0 to quit.\n"
        );
    }

    private static void addCar(CarOrganizer cars, Scanner userInputSrc)
    {
        System.out.println("Enter the VIN of the car: ");
        String VIN = userInputSrc.nextLine();
        System.out.println("Enter the make of the car: ");
        String Make = userInputSrc.nextLine();
        System.out.println("Enter the model of the car: ");
        String Model = userInputSrc.nextLine();
        System.out.println("Enter the price of the car: ");
        int Price = Integer.parseInt(userInputSrc.nextLine());
        System.out.println("Enter the mileage of the car: ");
        int Mileage = Integer.parseInt(userInputSrc.nextLine());
        System.out.println("Enter the color of the car: ");
        String Color = userInputSrc.nextLine();

        Car c = cars.addCar(VIN, Make, Model, Price, Mileage, Color);
        if(c == null)
            System.out.println("A car with that VIN already exists in the database!");
        else
            System.out.println("Added car: "+c);
    }

    private static void updateCar(CarOrganizer cars, Scanner userInputSrc)
    {
        System.out.println("Enter the VIN of the car: ");
        String VIN = userInputSrc.nextLine();
        System.out.println("Would you like to update the price, mileage, or color of this car?");
        System.out.println("Type p for price, m for mileage, or c for color.");
        String whichToUpdate = userInputSrc.nextLine();

        if(whichToUpdate.equals("p"))
        {
            System.out.println("Enter the price you wish to update it to: ");
            int Price = Integer.parseInt(userInputSrc.nextLine()); 
            Car c = cars.updateCarPrice(VIN, Price);
            if(c == null)
                System.out.println("The VIN you provided was not found in the database!");
            else
                System.out.println("Updated car: "+c);       
        }
        else if(whichToUpdate.equals("m"))
        {
            System.out.println("Enter the mileage you wish to update it to: ");
            int Mileage = Integer.parseInt(userInputSrc.nextLine());
            Car c = cars.updateCarMileage(VIN, Mileage);
            if(c == null)
                System.out.println("The VIN you provided was not found in the database!");
            else
                System.out.println("Updated car: "+c);    
   
        }
        else if(whichToUpdate.equals("c"))
        {
            System.out.println("Enter the color you wish to update it to");
            String Color = userInputSrc.nextLine();    
            Car c = cars.updateCarColor(VIN, Color);
            if(c == null)
                System.out.println("The VIN you provided was not found in the database!");
            else
                System.out.println("Updated car: "+c);    
        }
    }

    private static void deleteCar(CarOrganizer cars, Scanner userInputSrc)
    {
        System.out.println("Enter the VIN of the car: ");
        String VIN = userInputSrc.nextLine();
        boolean wasDeleted = cars.deleteCar(VIN);
        if(wasDeleted)
            System.out.println("Deleted successfully");
        else
            System.out.println("The VIN you provided was not found in the database!");            
    }

    private static void lowestPricedCar(CarOrganizer cars)
    {
        System.out.println("The car with the lowest price is: \n" + cars.getMinCarByPrice()+ "\n");
    }
    private static void lowestMileageCar(CarOrganizer cars)
    {
        System.out.println("The car with the lowest mileage is: \n" + cars.getMinCarByMileage()+ "\n");
    }
    private static void lowestPricedCarbyMakeandModel(CarOrganizer cars, Scanner userInputSrc)
    {
        System.out.println("Enter the make of the car: ");
        String Make = userInputSrc.nextLine();
        System.out.println("Enter the model of the car: ");
        String Model = userInputSrc.nextLine();
        Car c = cars.getMinPriceCarByMakeAndModel(Make, Model);
        if(c == null)
            System.out.println("That make and model doesn't exist in the database!");
        else
            System.out.println("The cheapest "+Make+" "+Model+" is :"+c);
    }
    private static void lowestMileageCarbyMakeandModel(CarOrganizer cars, Scanner userInputSrc)
    {
        System.out.println("Enter the make of the car: ");
        String Make = userInputSrc.nextLine();
        System.out.println("Enter the model of the car: ");
        String Model = userInputSrc.nextLine();
        Car c = cars.getMinMileageCarByMakeAndModel(Make, Model);
        if(c == null)
            System.out.println("That make and model doesn't exist in the database!");
        else
            System.out.println("The "+Make+" "+Model+" with the lowest mileage is:"+c);
    }
}