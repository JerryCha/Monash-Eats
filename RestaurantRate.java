public class RestaurantRate{
    private int resId;
    private int cusId;
    private int rateVale;
    public  RestaurantRate(){

    }
    public int getResId(){
        return resId;
    }
    public int getCusId(){
        return  cusId;
    }
    public int getRateVale(){
        return rateVale;
    }
    public void setResId(int resId){
        this.resId=resId;
    }
    public  void setCusId(int cusId){
        this.cusId=cusId;
    }
    public  void setRateVale(int rateVale){
        this.rateVale=rateVale;
    }
}