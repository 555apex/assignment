public class onewayTicket extends fareSystem{
    public double getfare(double distance){
        double price= 0;
        if ((distance <= 4)&&(distance>0)) {
        price= 2.0;
        }
        else if((4<distance)&&(distance<=8))
        {price= 3.0;
        }
        else if((8<distance)&&(distance<=12))
        {price=4.0;
        }
        else if((12<distance)&&(distance<=18))
        {price=5.0;}
        else if((18<distance)&&(distance<=24))
        {price=6.0;}
        else if((24<distance)&&(distance<=32))
        {price=7.0;}
        else if((32<distance)&&(distance<=40))
        {price=8.0;}
        else if((40<distance)&&(distance<=50))
        {price=9.0;}
        else
        {price=10.0+(distance-50-((distance-50)%20))/20;
        }
        return price;
    }
}
