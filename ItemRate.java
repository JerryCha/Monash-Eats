public class ItemRate{
  private int resId;
  private int cusId;
  private int itemId;
  private int rateVaule;

public ItemRate(){

}
public int getResId(){
    return resId;
}
public int getItemId(){
    return itemId;
}
public int getRateVaule(){
    return rateVaule;
}
public int getCusId(){
    return cusId;
}
public void setResId(int resId){
       this.resId = resId;
}
public void setCusId(int cusId){
    this.cusId=cusId;
}
public void setItemId(int itemId){
    this.itemId=itemId;
}
public void setRateVaule(int rateVaule){
    this.rateVaule=rateVaule;
}
}
