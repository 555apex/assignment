public class onewayTicket extends fareSystem{
    public double getfare(double distance){
        double fare = 0;
        if ((distance <= 4)&&(distance>0)) {
        fare = 2.0;
        }
        else if((4<distance)&&(distance<=8))
        {
            fare = 3.0;
        }
        else if((8<distance)&&(distance<=12))
        {
            fare =4.0;
        }
        else if((12<distance)&&(distance<=18))
        {
            fare =5.0;}
        else if((18<distance)&&(distance<=24))
        {
            fare =6.0;}
        else if((24<distance)&&(distance<=32))
        {
            fare =7.0;}
        else if((32<distance)&&(distance<=40))
        {
            fare =8.0;}
        else if((40<distance)&&(distance<=50))
        {
            fare =9.0;}
        else
        {
            fare =10.0+(distance-50-((distance-50)%20))/20;
        }
        return fare;
    }//单程票价格计算
}
