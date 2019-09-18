public class Car implements Comparable<Car>
{
    String VIN;
    String make;
    String model;
    int price;
    int mileage;
    String color;

    boolean isMileageComparator; // true if sorting by mileage, false if soring by price

    public Car(String v, String ma, String mo, int p, int mi, String c, boolean trueIfMileageComparator)
    {
        VIN = v;
        make = ma;
        model = mo;
        price = p;
        mileage = mi;
        color = c;
        isMileageComparator = trueIfMileageComparator;
    }
    
    public void setColor(String c)
    {
        color = c;
    }

    public void setMileage(int mil)
    {
        mileage = mil;
    }

    public void setPrice(int p)
    {
        price = p;
    }

    public String getVIN()
    {
        return VIN;
    }

    public String getMake()
    {
        return make;
    }

    public String getModel()
    {
        return model;
    }

    public String getColor()
    {
        return color;
    }

    public int getMileage()
    {
        return mileage;
    }

    public int getPrice()
    {
        return price;
    }

    public String toString()
    {
        String output = "\n\tVIN: " + VIN + "\n\tMake: " + make + "\n\tModel: " + model + "\n\tColor: " + color + "\n\tMileage: " + mileage + "\n\tPrice: $" + price;
        return output;
    }

    public int compareTo(Car anotherCar)
    {
        if(isMileageComparator)
        {
            if(this.mileage == anotherCar.mileage)
                return 0;
            else
                return this.mileage > anotherCar.mileage ? 1 : -1;
        }
        else
        {
            if(this.price == anotherCar.price)
                return 0;
            else
                return this.price > anotherCar.price ? 1 : -1;
        }
    }
}